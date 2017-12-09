package com.mygdx.game;

import java.lang.System;
import java.lang.System.*;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Entidades.Arqueiro;
import com.mygdx.game.Entidades.Mago;
import com.mygdx.game.Entidades.Mapa;
import com.mygdx.game.Entidades.Personagem;
import com.mygdx.game.Entidades.Guerreiro;
import com.mygdx.game.Entidades.Habilidade;

import static java.lang.System.*;

public class TelaJogo implements Screen {
    public static final int WIDTH = Gdx.app.getGraphics().getWidth();
    public static final int HEIGHT = Gdx.app.getGraphics().getHeight();

    private Personagem personagens[];
    private Texture fundo;
    private Mapa mapa;
    Jogo jogo;
    float stateTime = 0;

    public TelaJogo(Jogo jogo) {
        this.jogo = jogo;
        int escolhas[] = jogo.escolhas;
        int melhorias[] = jogo.melhorias;

        personagens = new Personagem[6];
        mapa = new Mapa(20, 20);

        boolean resVeneno;

        fundo = new Texture("badlogic.jpg");

        for(int i = 0; i < 6; i++) {
            resVeneno = false;

            int modificadores[] = new int[6];

            for(int j = 0; j < 6; j++) {
                if(melhorias[i] == j+2)
                    modificadores[j] = 1;
                else
                    modificadores[j] = 0;
            }
            if(melhorias[i] == 1)
                resVeneno = true;
            if(escolhas[i] == 1) {
                personagens[i] = new Guerreiro(resVeneno, modificadores);
            }
            else if(escolhas[i] == 2) {
                personagens[i] = new Mago(resVeneno, modificadores);
            }
            else {
                personagens[i] = new Arqueiro(resVeneno, modificadores);
            }

            personagens[i].setX(i * 128);
            personagens[i].setY(i * 128);
            personagens[i].setRoll(i);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        jogo.batch.begin();

        //jogo.batch.draw(fundo, 0, 0, WIDTH, HEIGHT);
        for(int i = 0; i < 6; i++) {
            jogo.batch.draw((TextureRegion)personagens[i].rolls[personagens[i].roll].getKeyFrame(stateTime, true), personagens[i].getX(), personagens[i].getY(), 128, 128);
        }
        stateTime += delta;
        jogo.batch.end();
        //jogo.batch.draw(personagens[i].rolls);
    }
    @Override
    public void dispose() {
        //fundo.dispose();
    }

    @Override
    public void show () {

    }

    @Override
    public void resize (int width, int height) {

    }

    @Override
    public void pause () {

    }

    @Override
    public void resume () {

    }

    @Override
    public void hide () {

    }
}
