package com.mygdx.game.Multiplayer;

public class PacoteAtacar {

	// As classes de pacote sao serializadas pelo
	// kryo, bastando que voce declare os campos contidos
	// no seu pacote.
	
	// O pacote atacar comunica:
	// o id do jogador que atacou,
	// o tipo de ataque (veja abaixo),
	// a posicao x e y no mapa de quem esta atacando
	// a posicao w e z no mapa de quem esta sendo atacado
	public int id, tipo, x, y, w, z;
	
	// Sobre tipos de ataque:
	// considerando que existem tres classes 
	// (guerreiro, arqueiro e mago),
	// e que cada classe tem 4 tipos de ataque,
	// tipo pode ser 1, 2, 3 ou 4.
	// 
	// Guerreiro:
	// Golpe de Espada      - 0
	// Escudo Protetor      - 1
	// Investida Selvagem   - 2 
	// Tremor Violento      - 3
	//
    // Arqueiro:
	// Flechada             - 0
	// Atirar em Movimento  - 1
	// Armadilha de Cacador - 2
	// Flecha Venenosa      - 4
	// 
	// Mago:
	// Raio            - 1
    // Bola de Fogo    - 2
    // Congelar        - 3   
	// Circulo de Fogo - 4
    
	
} // PacoteAtacar