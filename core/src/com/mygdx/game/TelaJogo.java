package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Entidades.Arqueiro;
import com.mygdx.game.Entidades.Guerreiro;
import com.mygdx.game.Entidades.Habilidade;
import com.mygdx.game.Entidades.Mago;
import com.mygdx.game.Entidades.Mapa;
import com.mygdx.game.Entidades.Personagem;

public class TelaJogo implements Screen, InputProcessor {
    public static final int WIDTH = Gdx.app.getGraphics().getWidth();
    public static final int HEIGHT = Gdx.app.getGraphics().getHeight();

    public static final int ESPACO_WIDTH = 64*WIDTH/1060;
    public static final int ESPACO_HEIGHT = HEIGHT/10;

    public static final int TILT_WIDTH = 11*WIDTH / 1060;
    public static final int TILT_HEIGHT = 10*HEIGHT / 650;

    private Personagem personagens[];
    private Texture fundo;
    private Texture campo;
    private Texture botaoOpcoes;
    private Texture botaoSair;
    private float opcoesX;
    private float opcoesY;
    private boolean movendo;

    private Mapa mapa;
    Jogo jogo;
    float stateTime = 0;
    Habilidade habilidadeAtual;

    public TelaJogo(Jogo jogo) {
        opcoesX = opcoesY = 0;
        this.jogo = jogo;
        int escolhas[] = jogo.escolhas;
        int melhorias[] = jogo.melhorias;

        personagens = new Personagem[6];
        mapa = new Mapa(10, 15);

        boolean resVeneno;

        fundo = new Texture("fundo.png");
        campo = new Texture("campo.png");
        botaoOpcoes = new Texture("botaoOpcoes.png");
        botaoSair = new Texture("botaoSair.png");

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

            personagens[i].setX(0);
            personagens[i].setY(i);
            personagens[i].setRoll(i);
            personagens[i].setAcao(2);
        }
        personagens[5].setX(14);
        personagens[5].setY(9);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.12f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        jogo.batch.begin();

        jogo.batch.draw(fundo, 0, 0, WIDTH, HEIGHT);
        jogo.batch.draw(campo, 0, 0, WIDTH, HEIGHT);
        for(int i = 0; i < 6; i++) {

            if(personagens[i].getAcao() != 0) {
                jogo.batch.draw((TextureRegion) personagens[i].rolls[personagens[i].roll].getKeyFrame(stateTime, false), personagens[i].getX() * ESPACO_WIDTH + personagens[i].getY() * TILT_WIDTH, TILT_HEIGHT + personagens[i].getY() * ESPACO_HEIGHT, ESPACO_WIDTH, ESPACO_HEIGHT);
            }
            else {
                jogo.batch.draw(personagens[i].getTexture(), personagens[i].getX() * ESPACO_WIDTH + personagens[i].getY() * TILT_WIDTH, TILT_HEIGHT + personagens[i].getY() * ESPACO_HEIGHT, ESPACO_WIDTH, ESPACO_HEIGHT);
            }

            if(personagens[i].rolls[personagens[i].roll].isAnimationFinished(stateTime)) {
                if(personagens[i].getAcao() == 1) { // Acabou o ataque
                    //Animacao Parado
                    personagens[i].setAcao(0);
                }
                if(personagens[i].getAcao() == 2) { // Parou de andar
                    //Animacao Parado
                    personagens[i].setAcao(0);
                }
            }
        }
        stateTime += delta;

        jogo.batch.draw(botaoSair, opcoesX + WIDTH/3, opcoesY, WIDTH / 10, WIDTH / 10);

        jogo.batch.draw(botaoOpcoes, opcoesX, opcoesY, WIDTH / 4, HEIGHT / 2);

        if(Gdx.input.isTouched()) {
            if(Gdx.input.getX() >= opcoesX + WIDTH/4 && Gdx.input.getX() <= opcoesX + WIDTH/3 &&
                    Gdx.input.getY() >= opcoesY && Gdx.input.getY() <= opcoesY + HEIGHT/2)
                movendo = true;
        }
        else {
            movendo = false;
        }

        if(movendo == true) {
            float x = Gdx.input.getDeltaX();
            float y = Gdx.input.getDeltaY();

            opcoesX += x;
            opcoesY -= y;
            if(opcoesX < 0)
                opcoesX = 0;
            if(opcoesY < 0)
                opcoesY = 0;

            if(opcoesX > (WIDTH - WIDTH/3 - WIDTH/10))
                opcoesX = (WIDTH - WIDTH/3 - WIDTH/10);
            if(opcoesY > (HEIGHT - HEIGHT/2))
                opcoesY = (HEIGHT - HEIGHT/2);
        }

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

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        movendo = false;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
