package com.mygdx.game.Entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Mago extends Personagem {
    public Mago(Boolean resVeneno, int[] modificadores) {
        super(resVeneno, modificadores);

        vida += 100;
        ataque += 1;
        armadura += 1;
        poder += 4;
        resistencia += 2;
        velocidade += 2;

        TextureRegion[][] andando = TextureRegion.split(new Texture("mago_andando.png"), SIZE, SIZE);
        for(int i = 0; i < 4; i++)
            rolls[i] = new Animation(SPEED, andando[i]); //0=UP,1=LEFT,2=DOWN,3=RIGHT
        TextureRegion[][] atacando = TextureRegion.split(new Texture("mago_atacando.png"), SIZE, SIZE);
        for(int i = 4; i < 8; i++)
            rolls[i] = new Animation(SPEED, atacando[i-4]); //0=UP,1=LEFT,2=DOWN,3=RIGHT
        TextureRegion[][] magia = TextureRegion.split(new Texture("mago_castando.png"), SIZE, SIZE);
        for(int i = 8; i < 12; i++)
            rolls[i] = new Animation(SPEED, magia[i-8]); //0=UP,1=LEFT,2=DOWN,3=RIGHT
        TextureRegion[][] morte  = TextureRegion.split(new Texture("mago_morrendo.png"), SIZE, SIZE);
        rolls[12] = new Animation(SPEED, morte[0]);
        texture = new Texture("mago.png");

        skills[0] = new Habilidade("Choque do Trovão",3, "SM", false, false, true, true, false, 5, 5);
        skills[1] = new Habilidade("Bola de Fogo", 3, "SM", false, false, false, false, false, 20, 25);
        skills[2] = new Habilidade("Pó de Diamante", 3, "SC", false, true, false, false, false, 20, 0);
        skills[3] = new Habilidade("Explosão Arcana", 4, "AM", false, false, false, false, false, 40, 30);
    }
}

