package com.mygdx.game.Multiplayer;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.Jogador;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MPClient extends Listener {

	/*
	 *  Classe MPClient (MultiPlayer Client) estende de
	 *  Listener para utilizar os metodos connect, receive e disconnected
	 *  e poder programar o que acontece caso um desses metodos seja
	 *  chamado. A biblioteca Kryonet ja cuida, em suas entranhas,
	 *  de chamar automaticamente um desses metodos no caso de algum evento
	 *  durante uma conexao com um Cliente, por isso sao chamados de Listener.
	 */
	
	Client client;	// Classe da Kryonet.
	String ip = "localhost"; 
	int port = 54555; 	// Mesma porta do server
	
	// Pacotes para serem retornados com informa��es
	PacoteAddJogador add;
	PacoteAtacar ataque;
	PacoteAguardar aguardo;
	PacoteMover mover;
	
	// Jogador do proprio cliente e lista de jogadores
	Jogador eu;
	public Map<Integer, Jogador> adversarios;
	
	// Cliente recebe
	public MPClient(Jogador eu) {
		
		// Inicializacao de ids dos pacotes, 
		// para garantir que ainda nao foi recebido
		// pacote do seu respectivo tipo
		add = new PacoteAddJogador();
		add.id = -1;
		ataque = new PacoteAtacar();
		ataque.id = -1;
		aguardo = new PacoteAguardar();
		aguardo.id = -1;
		mover = new PacoteMover();
		mover.id = -1;
		
		this.eu = eu;
		adversarios = new HashMap<Integer, Jogador>();
	}
	
	// Metodo chamado para conectar com servidor
	public void connect() {
		
		client = new Client();
		
		// Registro dos tipos de pacote que serao utilizados.
		// Kryonet requer que voc� registre os pacotes utilizados,
		// ja que sera ela quem cuidara da serializacao deles.
		client.getKryo().register(PacoteAddJogador.class);
		client.getKryo().register(PacoteMover.class);
		client.getKryo().register(PacoteAguardar.class);
		client.getKryo().register(PacoteAtacar.class);
		client.getKryo().register(PacoteDesconectado.class);
		client.getKryo().register(PacoteOK.class);
		
		client.start();
		client.addListener(this);
		
		// Tenta conectar o cliente, esperando 5000ms antes de dar erro
		try {
			client.connect(5000, ip, port, port);
			System.out.println("Conexao bem sucedida.");
		} catch (IOException e) {
			System.out.println("Conexao mal sucedida.");
			e.printStackTrace();
		}
	}
	
	// Metodo chamado quando cliente recebe pacote
	public void received(Connection c, Object o){
	
		// Eh checado o tipo do pacote
		// e feita as acoes de acordo
		
		// Se o pacote for do tipo OK
		if(o instanceof PacoteOK) {
				
				// Quando jogador recebe pacote OK, o servidor
				// espera que ele retorne um pacote com o time dele
				// para que tanto o servidor, quanto os adversarios
				// saibam quais classes ele tem
				PacoteAddJogador meuTime = new PacoteAddJogador();
				meuTime.id = client.getID();
				meuTime.p1 = eu.p1;
				meuTime.p2 = eu.p2;
				meuTime.p3 = eu.p3;
				meuTime.x = eu.x;
				meuTime.y = eu.y;
				
				client.sendTCP(meuTime);
				
				System.out.println("Enviado ao servidor meu time.");			
			
		// Se o pacote for de adicionar adversario
		} else if(o instanceof PacoteAddJogador) {
		
			// Cria um pacote de adicionar jogador, preenchendo
			// seus respectivos campos, e depois atribuindo
			// cada classe das 3 personagens do novo adversario.
			PacoteAddJogador novoJogador = (PacoteAddJogador) o;
			Jogador adversario = new Jogador();
			adversario.id = novoJogador.id;
			adversario.p1 = novoJogador.p1;
			adversario.p2 = novoJogador.p2;
			adversario.p3 = novoJogador.p3;
			adversario.x = novoJogador.x;
			adversario.y = novoJogador.y;
			
			// Coloca na lista de adversarios o novo adversario.
			adversarios.put(novoJogador.id, adversario);
			
			System.out.println("Atualizada lista de adversarios:");
			printAdversarios();
			
		// Se o pacote for de atacar
		} else if(o instanceof PacoteAtacar) {
			
			// Recebe pacote de ataque
			ataque = (PacoteAtacar) o;
			
			System.out.println("Ataque de " + c.getID() + " (" + ataque.x + ", " + ataque.y + ") em (" + 
                    			ataque.w + ", " + ataque.z + ")");
			
		// Se o pacote for de mover
		} else if(o instanceof PacoteMover) {
			
			// Recebe pacote de movimentacao
			PacoteMover movimentacao = (PacoteMover) o;
			
			mover.x = movimentacao.x;
			mover.y = movimentacao.y;
			mover.id = movimentacao.id;
		
			System.out.println("Jogador " + movimentacao.id + " se moveu para (" + movimentacao.x + ", " + movimentacao.y + ")");
			
		// Se o pacote for de aguardar
		} else if(o instanceof PacoteAguardar) {
			
			// Recebe pacote de aguardo
			aguardo = (PacoteAguardar) o;
			
			System.out.println("Jogador " + aguardo.id + " passou a vez.");
		
		// Se o pacote for adversario desconectado
		} else if(o instanceof PacoteDesconectado) {
			
			// Recebe pacote de desconectado e remove da lista de adversarios
			PacoteDesconectado desconectado = (PacoteDesconectado) o;
			
			adversarios.remove(desconectado.id);
			
			System.out.println("Jogador " + desconectado.id + " desconectado.");
			
		}
		
	}
	
	public void sendAtacar(int tipo, int x, int y, int w, int z) {
		PacoteAtacar at = new PacoteAtacar();
		at.id = client.getID();
		at.tipo = tipo;
		at.x = x;
		at.y = y;
		at.w = w;
		at.z = z;
		
		client.sendTCP(at);
	}
	
	public void sendMover(int x, int y) {
		PacoteMover m = new PacoteMover();
		m.id = client.getID();
		m.x = x;
		m.y = y;
		
		client.sendTCP(m);
	}
	
	public void sendAguardar() {
		PacoteAguardar ag = new PacoteAguardar();
		ag.id = client.getID();
		
		client.sendTCP(ag);
	}
	
	public void disconnect() {
		client.stop();
	}

	public PacoteAddJogador getAdd() {
		if(add.id != -1)
			return add;
		else
			return null;
	}

	public PacoteMover getMover() {
	    if(mover.id != -1)
	        return mover;
	    return null;
    }
	
	// Para que sejam feitas as devidas acoes
	// quando se recebe um ataque, deve ser 
	// retornado um objeto do pacote atacar
	// para se ter acesso as informacoes deste ataque.
	// OBS: Quando terminar as devidas acoes, 
	// chame a funcao resetAtaque() para estar apto
	// a receber um novo ataque.
	public PacoteAtacar getAtaque() {
		if(ataque.id != -1)
			return ataque;
		else
			return null;
	}
	
	// Funcao a ser chamada sempre que terminar 
	// as devidas acoes quando se recebe um ataque, 
	// para estar apto a receber um novo ataque.
	public void resetAtaque() {
		ataque.id = -1;
	}
	
	// Semelhante a getAtaque
	public PacoteAguardar getAguardo() {
		if(aguardo.id != -1)
			return aguardo;
		else
			return null;
	}
	
	// Semelhante a resetAtaque()
	public void resetAguardo() {
		aguardo.id = -1;
	}
		
	// Funcao para facilitar o reset 
	// de todos os pacotes.
	public void resetPacotes() {
		ataque.id = -1;
		aguardo.id = -1;
		add.id = -1;
		mover.id = -1;
	}
	
	public Map<Integer, Jogador> getAdversarios() {
		return adversarios;
	}
	
	// Funcao utilizada para DEBUG,
	// printa a lista adversarios
	public void printAdversarios() {
		for(Jogador adv : adversarios.values()) {
			System.out.println("Adversario [" + adv.id + "] :");
			System.out.println("P1: " + adv.p1);
			System.out.println("P2: " + adv.p2);
			System.out.println("P3: " + adv.p3);
		}
	}

	public int getID() {
		return client.getID();
	}
	
} // MPClient