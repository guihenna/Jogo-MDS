package com.mygdx.game.Multiplayer;

public class PacoteMover {

	// As classes de pacote sao serializadas pelo
	// kryo, bastando que voce declare os campos contidos
	// no seu pacote.
	
	// O pacote mover comunica:
	// o id do jogador que moveu,
	// a posicao x e y no mapa para onde moveu
	public int id, x, y;
	
} // PacoteMover