package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

import static java.lang.Math.abs;

public class TelaJogo implements Screen, InputProcessor {

    public int POSX = 0;
    public int POSY = 0;

    public static final int WIDTH = Gdx.app.getGraphics().getWidth();
    public static final int HEIGHT = Gdx.app.getGraphics().getHeight();

    public static final int TILT_WIDTH = 11*WIDTH / 1060;
    public static final int TILT_HEIGHT = 10*HEIGHT / 650;

    public static final int ESPACO_WIDTH = 64*WIDTH/1060;
    public static final int ESPACO_HEIGHT = (HEIGHT - TILT_HEIGHT)/10;

    public static final float barraX = 94f*(WIDTH/6f)/256f;
    public static final float barraY = 19f*ESPACO_HEIGHT/64f;
    public static final float barraTam = 12f*ESPACO_HEIGHT/64f;
    public static final float barraEspaco = 3f*ESPACO_HEIGHT/64f;
    public static final float barraEspessura = 92*(WIDTH/6f)/256f;

    private float destX;
    private float destY;
    private int personagemAtual;

    private Personagem personagens[];
    private Texture fundo;
    private Texture campo;
    private Texture botaoOpcoes;
    private Texture botaoSair;
    private Texture espacoMover;
    private Texture espacoAtacar;
    private Texture barraHP;
    private Texture barraSP;
    private Texture selecionaLocal;
    private boolean gyroscopeAvail = Gdx.input.isPeripheralAvailable(Input.Peripheral.Gyroscope);

    private float dragX;
    private float dragY;
    private boolean movendo;

    /* Tipos de acao
    1: Mover tela
    2: Escolher local para mover
    3: Aguardar o programa mover o personagem
    4: Escolher um ataque
    5: Aguardar animacao do ataque
    6: delay para selecionar ataque
    7: delay para mover
    8: delay para atacar
    */
    private int acao;

    private int jogador;
    private boolean aguardandoRcv;
    private boolean andou;

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
        barraHP = new Texture("barraHP.png");
        barraSP = new Texture("barraSP.png");
        selecionaLocal = new Texture("selecioneLocal.png");

        acao = 0;

        for(int i = 0; i < 6; i++) {
            resVeneno = false;

            int modificadores[] = new int[6];
            modificadores[0] = 0;
            for(int j = 1; j < 6; j++) {
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

            personagens[i].setTime(i/3);
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

        /*
        personagens[4].setMana(10);
        personagens[5].setMana(personagens[5].getMaxMana());
        personagens[0].setMana(40);
        */

        atualizarOrdem();
        personagens[0].setMana(10);
    }

    public void atualizarOrdem() {
        for(int i = 0; i < 5; i++) {
            for(int j = i+1; j < 6; j++) {
                if(personagens[i].getVelocidade() < personagens[j].getVelocidade()) {
                    Personagem p = personagens[i];
                    personagens[i] = personagens[j];
                    personagens[j] = p;
                }
            }
        }
        personagemAtual = 0;
    }

    public void atualizarRodada() {
        personagens[personagemAtual].setMove(3);
        personagemAtual++;

        if(personagemAtual == 6) {
            personagemAtual = 0;
        }
        andou = false;
        acao = 12;
        personagens[personagemAtual].setMana(personagens[personagemAtual].getMana() + 10);

        if(personagens[personagemAtual].getMana() > personagens[personagemAtual].getMaxMana())
            personagens[personagemAtual].setMana(personagens[personagemAtual].getMaxMana());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.12f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        jogo.batch.begin();

        // Desenhar o fundo e o campo - O fundo nao pode ser arrastado
        jogo.batch.draw(fundo, 0, 0, WIDTH, HEIGHT);
        jogo.batch.draw(campo, POSX, POSY, WIDTH, HEIGHT);

        // Desenhar os espacos, quando ha movimentacao ou ataque (bordas coloridas)
        for(int j = 0; j < mapa.getLin(); j++) {
            for(int i = 0; i < mapa.getCol(); i++) {
                if(mapa.getGrid()[i][j].getTipo() == 1)
                    jogo.batch.draw(espacoMover, POSX + i*ESPACO_WIDTH + j*TILT_WIDTH, POSY + TILT_HEIGHT + j * ESPACO_HEIGHT, ESPACO_WIDTH, ESPACO_HEIGHT);
                else if(mapa.getGrid()[i][j].getTipo() == 2)
                    jogo.batch.draw(espacoAtacar, POSX + i*ESPACO_WIDTH + j*TILT_WIDTH, POSY + TILT_HEIGHT + j * ESPACO_HEIGHT, ESPACO_WIDTH, ESPACO_HEIGHT);
            }
        }

        // Desenhar os personagens
        for(int i = 0; i < 6; i++) {
            // Desenhar a vida e a mana
            jogo.batch.draw(personagens[i].getInfo(), i * (WIDTH/6), HEIGHT - ESPACO_HEIGHT, WIDTH/6, ESPACO_HEIGHT);
            jogo.batch.draw(barraHP, barraX + i * (WIDTH/6), barraTam + barraEspaco + barraY + HEIGHT - ESPACO_HEIGHT, barraEspessura * ((float)personagens[i].getVida() / (float)personagens[i].getMaxVida()), barraTam);
            jogo.batch.draw(barraSP, barraX + i * (WIDTH/6), barraY + HEIGHT - ESPACO_HEIGHT, barraEspessura * ((float)personagens[i].getMana() / (float)personagens[i].getMaxMana()), barraTam);
            boolean loop;
            if(acao == 3)
                loop = true;
            else
                loop = false;
            if(personagens[i].getAcao() != 0) {
                jogo.batch.draw((TextureRegion) personagens[i].rolls[personagens[i].roll].getKeyFrame(stateTime, loop), POSX + personagens[i].getX() * ESPACO_WIDTH + personagens[i].getY() * TILT_WIDTH, POSY + TILT_HEIGHT + personagens[i].getY() * ESPACO_HEIGHT, ESPACO_WIDTH, ESPACO_HEIGHT);
            }
            else {
                jogo.batch.draw(personagens[i].getTexture(), POSX + personagens[i].getX() * ESPACO_WIDTH + personagens[i].getY() * TILT_WIDTH, POSY + TILT_HEIGHT + personagens[i].getY() * ESPACO_HEIGHT, ESPACO_WIDTH, ESPACO_HEIGHT);
            }

            if(personagens[i].getAcao() == 2) { // Acabou o ataque
                if(personagens[i].rolls[personagens[i].roll].isAnimationFinished(stateTime)) {
                    //Animacao Parado
                    personagens[i].setAcao(0);
                }
            }
        }
        stateTime += delta;

        // Botao de sair ou voltar
        jogo.batch.draw(botaoSair, WIDTH - (WIDTH/10), 0, WIDTH / 10, WIDTH / 10);

        if(personagens[personagemAtual].getTime() != jogador) {
            //Esperar resposta do inimigo
        }
        
        if(acao <= 1)
            jogo.batch.draw(botaoOpcoes, 0, 0, WIDTH / 4, HEIGHT / 2);

        if(acao == 7) { // Impedir toque continuo entre duas funcoes
            if(!Gdx.input.isTouched())
                acao = 2;
        }
        else if(acao == 2) {
            for(int i = 0; i < mapa.getCol(); i++) {
                for (int j = 0; j < mapa.getLin(); j++) {
                    if (abs(i - personagens[personagemAtual].getX()) + abs(j - personagens[personagemAtual].getY()) <= personagens[personagemAtual].getMove()) {
                        mapa.setTipo(i, j, 1);
                    }
                }
            }
            jogo.batch.draw(selecionaLocal, 0, 0, WIDTH/3, HEIGHT / 6);
            if(Gdx.input.isTouched()) {
                //System.out.println(Gdx.input.getX() + "," + Gdx.input.getY() + " - " + (WIDTH-(WIDTH/10)) + "," + WIDTH/10);
                if (Gdx.input.getX() >= WIDTH - (WIDTH / 10) && HEIGHT - Gdx.input.getY() <= WIDTH / 10) { // Voltar
                    acao = 12;
                    for(int i = 0; i < mapa.getCol(); i++)
                        for(int j = 0; j < mapa. getLin(); j++)
                            mapa.setTipo(i, j, 0);
                }
                else if(clicouParaMover(Gdx.input.getX(), HEIGHT-Gdx.input.getY())) {
                    for(int i = 0; i < mapa.getCol(); i++) {
                        for(int j = 0; j < mapa.getLin(); j++) {
                            if(mapa.getGrid()[i][j].getTipo() == 1)
                                mapa.setTipo(i, j, 0);
                            else if(mapa.getGrid()[i][j].getTipo() == 5) {
                                destX = i;
                                destY = j;
                                personagens[personagemAtual].setMove(personagens[personagemAtual].getMove() - (abs((int)personagens[personagemAtual].getX() - i) + abs((int)personagens[personagemAtual].getY() - j)));
                                mapa.setTipo(i, j, 0);
                                acao = 3;
                                //System.out.println(personagens[personagemAtual].getMove());
                                personagens[personagemAtual].setAcao(1);
                            }
                        }
                    }
                }
            }
        }
        else if(acao == 3) {
            if(personagens[personagemAtual].getX() < destX) {
                personagens[personagemAtual].setX(personagens[personagemAtual].getX() + 0.02f);
                personagens[personagemAtual].setDir(3);
                personagens[personagemAtual].setRoll(3);
                if(personagens[personagemAtual].getX() >= destX)
                    personagens[personagemAtual].setX(destX);
            }
            else if(personagens[personagemAtual].getX() > destX) {
                personagens[personagemAtual].setX(personagens[personagemAtual].getX() - 0.02f);
                personagens[personagemAtual].setDir(1);
                personagens[personagemAtual].setRoll(1);
                if(personagens[personagemAtual].getX() <= destX)
                    personagens[personagemAtual].setX(destX);
            }
            else if(personagens[personagemAtual].getY() < destY) {
                personagens[personagemAtual].setY(personagens[personagemAtual].getY() + 0.02f);
                personagens[personagemAtual].setDir(0);
                personagens[personagemAtual].setRoll(0);
                if(personagens[personagemAtual].getY() >= destY)
                    personagens[personagemAtual].setY(destY);
            }
            else if(personagens[personagemAtual].getY() > destY) {
                personagens[personagemAtual].setY(personagens[personagemAtual].getY() - 0.02f);
                personagens[personagemAtual].setDir(2);
                personagens[personagemAtual].setRoll(2);
                if(personagens[personagemAtual].getY() <= destY)
                    personagens[personagemAtual].setY(destY);
            }
            else {
                acao = 12;
                personagens[personagemAtual].setAcao(0);
                if(personagens[personagemAtual].getMove() <= 0)
                    andou = true;
            }
        }
        else if(acao == 13) {
            if(!Gdx.input.isTouched())
                acao = 4;
        }
        else if(acao == 4) {
            jogo.batch.draw(personagens[personagemAtual].getAtaques(), 0, 0, WIDTH / 4, HEIGHT / 2);
            if(Gdx.input.isTouched()) {
                System.out.println(Gdx.input.getX() + "," + Gdx.input.getY() + " - " + (WIDTH-(WIDTH/10)) + "," + WIDTH/10);

                if (Gdx.input.getX() >= WIDTH - (WIDTH / 10) && HEIGHT - Gdx.input.getY() <= WIDTH / 10) { // Voltar
                    acao = 12;
                }
                else if(Gdx.input.getX() <= WIDTH/4 && HEIGHT-Gdx.input.getY() <= HEIGHT/2) { // Opcoes de ataque

                }
            }
        }
        else if(acao == 12) {
            if(!Gdx.input.isTouched())
                acao = 0;
        }
        else if(acao == 0) {
            if(Gdx.input.isTouched()) {
                //System.out.println(Gdx.input.getX() + "," + Gdx.input.getY() + " - " + (WIDTH/4) + "," + HEIGHT/2);
                if (Gdx.input.getX() <= WIDTH / 4 && Gdx.input.getY() >= HEIGHT - (HEIGHT / 2)) {// Menu
                    if (Gdx.input.getY() <= HEIGHT - (HEIGHT / 3)) { // Atacar
                        acao = 13;
                        //System.out.println("Atacar");
                    } else if (Gdx.input.getY() <= HEIGHT - (HEIGHT / 6)) { // Mover
                        if (andou == false) {
                            acao = 7;
                        }
                    } else { // Aguardar
                        atualizarRodada();
                        acao = 12;
                    }
                }

                else if (Gdx.input.getX() >= WIDTH - (WIDTH / 10) && Gdx.input.getY() <= WIDTH / 10) { // Sair
                    //Desconecta tudo
                    //jogo.setScreen(new TelaMenu);
                }

                else {
                    movendo = true;
                }
            }
            else
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
        fundo.dispose();
        campo.dispose();
        botaoOpcoes.dispose();
        botaoSair.dispose();
        espacoMover.dispose();
        espacoAtacar.dispose();
        barraHP.dispose();
        barraSP.dispose();
        selecionaLocal.dispose();
        for(int i = 0; i < 6; i++) {
            personagens[i].getTexture().dispose();
            personagens[i].getInfo().dispose();
            personagens[i].getAtaques().dispose();
        }
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

    public boolean clicouParaMover(int x, int y) {
        //Descobrir i e j no mapa
        //jogo.batch.draw(espacoMover, POSX + i*ESPACO_WIDTH + j*TILT_WIDTH, POSY + TILT_HEIGHT + j * ESPACO_HEIGHT, ESPACO_WIDTH, ESPACO_HEIGHT);

        for(int i = 0; i < mapa.getCol(); i++) {
            for(int j = 0; j < mapa.getLin(); j++) {
                if(mapa.getGrid()[i][j].getTipo() != 1)
                    continue;

                int inix = POSX + i*ESPACO_WIDTH + j*TILT_WIDTH;
                int iniy = POSY + TILT_HEIGHT + j * ESPACO_HEIGHT;
                int fimx = inix + ESPACO_WIDTH;
                int fimy = iniy + ESPACO_HEIGHT;

                if(x >= inix && x <= fimx && y >= iniy && y <= fimy) {
                    mapa.setTipo(i, j, 5);
                    return true;
                }
            }
        }
        return false;
    }
}
