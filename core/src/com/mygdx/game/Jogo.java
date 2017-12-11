package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Jogo extends Game {
	SpriteBatch batch;
	int escolhas[];
	int melhorias[];
	private GameCamera cam;

	@Override
	public void create () {

		batch = new SpriteBatch();
		escolhas = new int[6];
		melhorias = new int[7];

		for(int i = 0; i < 6; i++) {
			melhorias[i] = i%6 + 1;
			escolhas[i] = i%3 + 1;
		}
		setScreen(new TelaJogo(this));
	}

	@Override
	public void render () {
	    super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
