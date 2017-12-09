package com.mygdx.game.Entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

public abstract class Personagem {
    final float SPEED = 0.5f;
    final int SIZE = 64;
    protected int vida;
    protected int ataque;
    protected int armadura;
    protected int poder;
    protected int resistencia;
    protected int velocidade;
    protected int mana;
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

    public Animation rolls[];

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

    public void setRoll(int roll) {
        this.roll = roll;
    }

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
}
