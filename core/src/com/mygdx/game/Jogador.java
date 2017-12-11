package com.mygdx.game;

public class Jogador {

		// Essa classe eh utilizada pelo cliente.
		// Ela eh semelhante da classe JogadorServer,
		// sendo que ela NAO tem um objeto connection
		// para a comunica��o com o server.
		
		// Classe jogador contem o tipo da 
		// classe de cada personagem do seu time.
		// Sendo 1 - guerreiro, 2 - arqueiro, 3 - mago.
		// Alem disso, possui posicoes x e y no mapa.
		public int id, p1, p2, p3, x, y;
		
		// Pode inicializar a teste zerada sem personagens
		public Jogador() {
			p1 = 0;
			p2 = 0; 
			p3 = 0;
			x = 0;
			y = 0;
		}
		
		// Ou inicializar com os personagens
		public Jogador(int p1, int p2, int p3, int x, int y) {
			this.p1 = p1;
			this.p2 = p2;
			this.p3 = p3;
			this.x = x;
			this.y = y;
		}
		
	
} // Jogador