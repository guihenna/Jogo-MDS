package com.mygdx.game.Entidades;

public class Espaco {
    private int x;
    private int y;
    private boolean personagem;
    private int escudo;
    private int tipo; //0=NORMAL,1=MOVER,2=ATACAR

    public Espaco(int x, int y) {
        this.x = x;
        this.y = y;
        this.escudo = 0;
        this.personagem = false;
        this.tipo = 0;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPersonagem(boolean personagem) {
        this.personagem = personagem;
    }

    public void setEscudo(int escudo) {
        this.escudo = escudo;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean getPersonagem() {
        return personagem;
    }

    public int getEscudo() {
        return escudo;
    }
}
