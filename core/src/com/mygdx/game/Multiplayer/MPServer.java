package com.mygdx.game.Multiplayer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class MPServer extends Listener {
	
	/*
	 *  Classe MPServer (MultiPlayer Server) estende de
	 *  Listener para utilizar os metodos connect, receive e disconnected
	 *  e poder programar o que acontece caso um desses metodos seja
	 *  chamado. A biblioteca Kryonet ja cuida, em suas entranhas,
	 *  de chamar automaticamente um desses metodos no caso de algum evento
	 *  durante uma conexao com um Cliente, por isso sao chamados de Listener.
	 */
	
	static Server server;	// Classe da Kryonet.
	static final int port = 54555; // Porta com numero aleatorio.
	
	// Map <ID, Jogador> - lista de jogadores conectados
	static Map<Integer, JogadorServer> jogadores = new HashMap<Integer, JogadorServer>();
	
	static int conectados;
	
	public static void main(String[] args) throws IOException{
		
		conectados = 0;
		
		server = new Server();
		
		// Registro dos tipos de pacote que serao utilizados.
		// Kryonet requer que voc� registre os pacotes utilizados,
		// ja que sera ela quem cuidara da serializacao deles.
		server.getKryo().register(PacoteAddJogador.class);
		server.getKryo().register(PacoteMover.class);
		server.getKryo().register(PacoteAguardar.class);
		server.getKryo().register(PacoteAtacar.class);
		server.getKryo().register(PacoteDesconectado.class);
		server.getKryo().register(PacoteOK.class);
		
		// O metodo bind recebe como argumento (int tcpPort, int udpPort).
		server.bind(port, port);
		server.start();
		server.addListener(new MPServer());
		
		System.out.println("Server rodando.");
	}
	
	// Metodo chamado quando conexao eh estabelecida com cliente
	public void connected(Connection c) {
		
		if(conectados < 2) {
			// Cria jogador novo, atribuindo ao campo
			// Connection dele a conexao c do parametro
			JogadorServer jogador = new JogadorServer();
			jogador.c = c;
			
			// Porem, ainda nao se sabe as classes
			// das personagens do seu time. Entao,
			// eh enviado um pacote do tipo OK, 
			// para que o cliente responda com seu time.
			PacoteOK ok = new PacoteOK();
			c.sendTCP(ok);
			
			// O jogador eh adicionado na lista de jogadores conectados
			jogadores.put(c.getID(), jogador);
			
			System.out.println("Jogador " + c.getID() + " entrou na partida.");
			
			conectados++;
			
		} else {
			
			c.close();
			
			System.out.println("Partida cheia ");
		}
	}
	
	// Metodo chamado quando servidor recebe pacote
	public void received(Connection c, Object o) {
		
		// Eh checado o tipo do pacote
		// e feita as acoes de acordo
		
		// Se o pacote for de adicionar jogador
		if(o instanceof PacoteAddJogador) {
			
			// Cria um pacote de adicionar jogador, preenchendo
			// seus respectivos campos, e depois enviando esse 
			// pacote para todos os jogadores conectados MENOS
			// o jogador de quem o servidor recebeu esse pacote
			// (o jogador novo). Afinal, se mandar a ele tambem, 
			// ele adicionara a si proprio.
			PacoteAddJogador novoJogador = (PacoteAddJogador) o;
			jogadores.get(c.getID()).p1 = novoJogador.p1;
			jogadores.get(c.getID()).p2 = novoJogador.p2;
			jogadores.get(c.getID()).p3 = novoJogador.p3;
			jogadores.get(c.getID()).x = novoJogador.x;
			jogadores.get(c.getID()).y = novoJogador.y;
			
			server.sendToAllExceptTCP(c.getID(), novoJogador);

			System.out.println("Time do jogador " + c.getID() + " atualizado.");
			System.out.println("Atualizando jogador " + c.getID() + " sobre outros jogadors");
			
			// Percorre lista de jogadores, criando pacotes com seus times
			// para enviar para o novo jogador, checando se o jogador atual da 
			// iteracao nao eh o mesmo que enviou esse pacote. Isso porque quando
			// a conexao eh estabelecida, o jogador novo eh adicionado apenas com seu id.
			for(JogadorServer j : jogadores.values()) {
				if(j.c.getID() != c.getID()) {
					PacoteAddJogador antigoJogador = new PacoteAddJogador();
					antigoJogador.id = j.c.getID();
					antigoJogador.p1 = j.p1;
					antigoJogador.p2 = j.p2;
					antigoJogador.p3 = j.p3;
					antigoJogador.x = j.x;
					antigoJogador.y = j.y;
					
					c.sendTCP(antigoJogador);
				}
			}
			
			System.out.println("Jogador atualizado.");
			
		// Se o pacote for de atacar
		} else if(o instanceof PacoteAtacar) {
			
			// Envia pacote de ataque para o id2 - o jogador sendo atacado.
			PacoteAtacar ataque = (PacoteAtacar) o;
			server.sendToAllExceptTCP(c.getID(), ataque);
			
			System.out.println("Ataque de " + c.getID() + " (" + ataque.x + ", " + ataque.y + ") em (" + 
	                            ataque.w + ", " + ataque.z + ")");
			
		// Se o pacote for de mover
		} else if(o instanceof PacoteMover) {
			
			// Envia pacote de movimentacao para todos os outros jogadores conectados
			PacoteMover movimento = (PacoteMover) o;
			server.sendToAllExceptTCP(c.getID(), movimento);
		
			System.out.println("Jogador " + c.getID() + " se moveu para (" + movimento.x + ", " + movimento.y + ")");
			
		// Se o pacote for de aguardar
		} else if(o instanceof PacoteAguardar) {
			
			// Envia pacote de aguardo para todos os outros jogadores conectados
			PacoteAguardar aguardo = (PacoteAguardar) o;
			server.sendToAllExceptTCP(c.getID(), aguardo);
			
			System.out.println("Jogador " + c.getID() + " passou a vez.");
		}
	
	}
	
	// Metodo chamado quando jogador desconecta
	public void disconnected(Connection c) {
		
		// Envia pacote de desconexao para todos os outros jogadores ainda em partida
		jogadores.remove(c.getID());
		PacoteDesconectado desc = new PacoteDesconectado();
		desc.id = c.getID();
		server.sendToAllExceptTCP(c.getID(), desc);
		
		System.out.println("Jogador " + c.getID() + " desconectado.");
		
		conectados--;
	}
	
} // MPServer
