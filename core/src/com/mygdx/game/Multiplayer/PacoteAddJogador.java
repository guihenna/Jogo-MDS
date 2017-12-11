package com.mygdx.game.Multiplayer;

public class PacoteAddJogador {
	
	// As classes de pacote sao serializadas pelo
	// kryo, bastando que voce declare os campos contidos
	// no seu pacote.
	
	// O pacote adicionar jogador comunica:
	// o id do jogador que se conectou em uma partida,
	// o tipo de cada personagem do seu time.
	// Sendo 1 - guerreiro, 2 - arqueiro, 3 - mago,
	// a posicao x e y no mapa
	public int id, p1, p2, p3, x, y;
	
} // PacoteAddJogador