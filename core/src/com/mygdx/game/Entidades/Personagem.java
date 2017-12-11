package com.mygdx.game.Entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

public abstract class Personagem {
    final float SPEED = 0.5f;
    final int SIZE = 64;
    protected int vida;
    protected int maxVida;
    protected int ataque;
    protected int armadura;
    protected int poder;
    protected int resistencia;
    protected int velocidade;
    protected int mana;
    protected int maxMana;
    protected Habilidade[] skills;
    protected int veneno;
    protected Boolean resVeneno;
    protected int escudo;
    protected Boolean stun;
    protected Boolean congelado;
    protected float x;
    protected float y;
    protected int move;
    public int roll;
    protected int dir; //0=UP,1=LEFT,2=DOWN,3=RIGHT
    protected int acao; //1=ANDAR,2=CASTAR,3=MORRER,4=NADA
    protected Texture info;
    protected int time;

    public Animation rolls[];

    public Personagem(Boolean resVeneno, int[] modificadores) {
        skills = new Habilidade[4];
        this.resVeneno = resVeneno;
        vida = modificadores[0];
        ataque = modificadores[1];
        armadura = modificadores[2];
        poder = modificadores[3];
        resistencia = modificadores[4];
        velocidade = modificadores[5];
        veneno = 0;
        escudo = 0;
        mana = 0;
        move = 3;
        rolls = new Animation[13];
        roll = 0;
        acao = 0;
        //texture = new Texture("badlogic.jpg");
    }

    public void minhaVez() {
        if(veneno > 0) {
            veneno--;
            this.levarDano(5, 2);
        }
    }



    public void levarDano(int dano, int tipo) {
        if(tipo == 1) { // Fisico
            escudo -= (dano - armadura);
            if(escudo < 0) {
                vida += escudo;
                escudo = 0;
            }
        }
        else { // Magico
            escudo -= (dano - resistencia);
            if(escudo < 0) {
                vida += escudo;
                escudo = 0;
            }
        }
    }

    public void andar(int valor) {
        //permitir andar $valor casas
    }

    // Setters and Getters

    public Texture getInfo() {
        return info;
    }

    public void setInfo(Texture info) {
        this.info = info;
    }

    public int getMaxVida() {
        return maxVida;
    }

    public void setMaxVida(int maxVida) {
        this.maxVida = maxVida;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    protected Texture texture;

    public Animation[] getRolls() {
        return rolls;
    }

    public void setRolls(Animation[] rolls) {
        this.rolls = rolls;
    }

    public int getRoll() {
        return roll;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }
    public int getArmadura() {
        return armadura;
    }
    public int getAtaque() {
        return ataque;
    }
    public int getPoder() {
        return poder;
    }
    public int getResistencia() {
        return resistencia;
    }
    public int getVelocidade() {
        return velocidade;
    }
    public int getVida() {
        return vida;
    }
    public int getMana() {
        return mana;
    }
    public Habilidade[] getSkills() {
        return skills;
    }
    public int getVeneno() {
        return veneno;
    }
    public Boolean getResVeneno() {
        return resVeneno;
    }
    public int getEscudo() {
        return escudo;
    }
    public Boolean getStun() {
        return stun;
    }
    public Boolean getCongelado() {
        return congelado;
    }
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public int getMove() {
        return move;
    }
    public int getDir() {
        return dir;
    }
    public int getAcao() {
        return acao;
    }

    public void setArmadura(int armadura) {
        this.armadura = armadura;
    }
    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }
    public void setPoder(int poder) {
        this.poder = poder;
    }
    public void setResistencia(int resistencia) {
        this.resistencia = resistencia;
    }
    public void setVelocidade(int velocidade) {
        this.velocidade = velocidade;
    }
    public void setVida(int vida) {
        this.vida = vida;
    }
    public void setMana(int mana) {
        this.mana = mana;
    }
    public void setSkills(Habilidade[] skills) {
        this.skills = skills;
    }
    public void setVeneno(int veneno) {
        this.veneno = veneno;
    }
    public void setResVeneno(Boolean resVeneno) {
        this.resVeneno = resVeneno;
    }
    public void setEscudo(int escudo) {
        this.escudo = escudo;
    }
    public void setStun(Boolean stun) {
        this.stun = stun;
    }
    public void setCongelado(Boolean congelado) {
        this.congelado = congelado;
    }
    public void setX(float x) {
        this.x = x;
    }
    public void setY(float y) {
        this.y = y;
    }
    public void setMove(int move) {
        this.move = move;
    }
    public void setDir(int dir) {
        this.dir = dir;
    }
    public void setAcao(int acao) {
        this.acao = acao;
    }
}
