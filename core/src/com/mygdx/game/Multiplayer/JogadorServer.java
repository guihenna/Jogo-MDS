package com.mygdx.game.Multiplayer;

import com.esotericsoftware.kryonet.Connection;

public class JogadorServer {

	// Essa classe eh utilizada pelo server.
	// Ela eh semelhante da classe Jogador,
	// sendo que ela tem um objeto connection
	// para a comunica��o com o server.
	
	// Classe jogador contem um objeto de conexao,
	// que serve para a comunicacao multiplayer,
	// identificadores da classe de cada membro do seu time,
	// sendo 1 - guerreiro, 2 - arqueiro, 3 - mago,
	// e posicao x e y no mapa
	public Connection c;
	public int p1, p2, p3, x, y;
	
	public JogadorServer() {
		p1 = 0;
		p2 = 0; 
		p3 = 0;
	}
	
} // Jogador
