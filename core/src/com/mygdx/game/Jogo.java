package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Jogo extends Game {
	SpriteBatch batch;
	Texture img;
	int escolhas[];
	int melhorias[];

	@Override
	public void create () {
		batch = new SpriteBatch();
		escolhas = new int[6];
		melhorias = new int[7];

		for(int i = 0; i < 6; i++) {
			melhorias[i] = i%6 + 1;
			escolhas[i] = 2;//i % 3 + 1;
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
		img.dispose();
	}
}
