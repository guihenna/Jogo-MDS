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

    public int POSX = 0;
    public int POSY = 0;

    public static final int WIDTH = Gdx.app.getGraphics().getWidth();
    public static final int HEIGHT = Gdx.app.getGraphics().getHeight();

    public static final int TILT_WIDTH = 11*WIDTH / 1060;
    public static final int TILT_HEIGHT = 10*HEIGHT / 650;

    public static final int ESPACO_WIDTH = 64*WIDTH/1060;
    public static final int ESPACO_HEIGHT = (HEIGHT - TILT_HEIGHT)/10;

    private Personagem personagens[];
    private Texture fundo;
    private Texture campo;
    private Texture botaoOpcoes;
    private Texture botaoSair;
    private Texture espacoMover;
    private Texture espacoAtacar;

    private float dragX;
    private float dragY;
    private boolean movendo;
    private int acao;

    private Mapa mapa;
    Jogo jogo;
    float stateTime = 0;
    Habilidade habilidadeAtual;

    public TelaJogo(Jogo jogo) {
        dragX = dragY = 0;
        this.jogo = jogo;
        int escolhas[] = jogo.escolhas;
        int melhorias[] = jogo.melhorias;

        personagens = new Personagem[6];
        mapa = new Mapa(10, 15);

        /*for(int i = 0; i < mapa.getLin(); i++) {
            for(int j = 0; j < mapa.getCol(); j++) {
                if(i % 2 != j%2)
                    mapa.setTipo(i, j, 1);
                else
                    mapa.setTipo(i, j, 2);
            }
        }*/

        boolean resVeneno;

        fundo = new Texture("fundo.png");
        campo = new Texture("campo.png");
        botaoOpcoes = new Texture("botaoOpcoes.png");
        botaoSair = new Texture("botaoSair.png");
        espacoMover = new Texture("espacoMover.png");
        espacoAtacar = new Texture("espacoAtacar.png");

        acao = 0;

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
        }
        personagens[0].setX(5);
        personagens[1].setX(7);
        personagens[2].setX(9);

        personagens[0].setY(2);
        personagens[1].setY(2);
        personagens[2].setY(2);

        personagens[3].setX(5);
        personagens[4].setX(7);
        personagens[5].setX(9);

        personagens[3].setY(7);
        personagens[4].setY(7);
        personagens[5].setY(7);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.12f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        jogo.batch.begin();

        jogo.batch.draw(fundo, 0, 0, WIDTH, HEIGHT);
        jogo.batch.draw(campo, POSX, POSY, WIDTH, HEIGHT);
        for(int j = 0; j < mapa.getLin(); j++) {
            for(int i = 0; i < mapa.getCol(); i++) {
                if(mapa.getGrid()[j][i].getTipo() == 1)
                    jogo.batch.draw(espacoMover, POSX + i*ESPACO_WIDTH + j*TILT_WIDTH, POSY + TILT_HEIGHT + j * ESPACO_HEIGHT, ESPACO_WIDTH, ESPACO_HEIGHT);
                else if(mapa.getGrid()[j][i].getTipo() == 2)
                    jogo.batch.draw(espacoAtacar, POSX + i*ESPACO_WIDTH + j*TILT_WIDTH, POSY + TILT_HEIGHT + j * ESPACO_HEIGHT, ESPACO_WIDTH, ESPACO_HEIGHT);
            }
        }
        for(int i = 0; i < 6; i++) {
            // Desenhar a vida e a mana
            jogo.batch.draw(personagens[i].getInfo(), i * (WIDTH/6), HEIGHT - ESPACO_HEIGHT, WIDTH/6, ESPACO_HEIGHT);
            if(personagens[i].getAcao() != 0) {
                jogo.batch.draw((TextureRegion) personagens[i].rolls[personagens[i].roll].getKeyFrame(stateTime, false), POSX + personagens[i].getX() * ESPACO_WIDTH + personagens[i].getY() * TILT_WIDTH, POSY + TILT_HEIGHT + personagens[i].getY() * ESPACO_HEIGHT, ESPACO_WIDTH, ESPACO_HEIGHT);
            }
            else {
                jogo.batch.draw(personagens[i].getTexture(), POSX + personagens[i].getX() * ESPACO_WIDTH + personagens[i].getY() * TILT_WIDTH, POSY + TILT_HEIGHT + personagens[i].getY() * ESPACO_HEIGHT, ESPACO_WIDTH, ESPACO_HEIGHT);
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

        jogo.batch.draw(botaoSair, WIDTH - (WIDTH/10), 0, WIDTH / 10, WIDTH / 10);

        jogo.batch.draw(botaoOpcoes, 0, 0, WIDTH / 4, HEIGHT / 2);

        if(Gdx.input.isTouched()) {
            if(Gdx.input.getX() <= WIDTH/3 && Gdx.input.getY() <= HEIGHT/2) {// Menu
                if(Gdx.input.getY() <= HEIGHT/6) { // Mover

                }
                else if(Gdx.input.getY() <= HEIGHT/3) { // Atacar

                }
                else { // Aguardar

                }
            }

            else if(Gdx.input.getX() >= WIDTH - (WIDTH/10) && Gdx.input.getY() <= WIDTH/10) { // Sair

            }

            else if(acao == 0) {
                movendo = true;
            }
        }
        else {
            movendo = false;
        }

        if(movendo == true) {
            float x = Gdx.input.getDeltaX();
            float y = Gdx.input.getDeltaY();

            POSX += x;
            if(POSX > WIDTH - WIDTH/4)
                POSX = WIDTH - WIDTH/4;
            if(POSX < -WIDTH/4)
                POSX = -WIDTH/4;
            POSY -= y;
            if(POSY > HEIGHT - HEIGHT/4)
                POSY = HEIGHT - HEIGHT/4;
            if(POSY < -HEIGHT/4)
                POSY = -HEIGHT/4;


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
