package com.mygdx.game.Entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;

public class Guerreiro extends Personagem {
    public Guerreiro(Boolean resVeneno, int[] modificadores) {
        super(resVeneno, modificadores);

        vida += 100;
        ataque += 4;
        armadura += 3;
        poder += 1;
        resistencia += 3;
        velocidade += 1;

        TextureRegion[][] andando = TextureRegion.split(new Texture("guerreiro_andando.jpeg"), SIZE, SIZE);
        for(int i = 0; i < 4; i++)
            rolls[i] = new Animation(SPEED, andando[i]); //0=UP,1=LEFT,2=DOWN,3=RIGHT
        TextureRegion[][] atacando = TextureRegion.split(new Texture("guerreiro_atacando.jpeg"), SIZE, SIZE);
        for(int i = 4; i < 8; i++)
            rolls[i] = new Animation(SPEED, atacando[i]); //0=UP,1=LEFT,2=DOWN,3=RIGHT
        TextureRegion[][] magia = TextureRegion.split(new Texture("guerreiro_castando.jpeg"), SIZE, SIZE);
        for(int i = 8; i < 12; i++)
            rolls[i] = new Animation(SPEED, magia[i]); //0=UP,1=LEFT,2=DOWN,3=RIGHT
        TextureRegion[][] morte  = TextureRegion.split(new Texture("guerreiro_morrendo.jpeg"), SIZE, SIZE);
        rolls[12] = new Animation(SPEED, morte[0]);

        skills[0] = new Habilidade("Golpe de Espada", 2, "SF", false, false, false, false, false, 0, 10);
        skills[1] = new Habilidade("Escudo Protetor", 1, "AE", false, false, false, false, false, 20, 30);
        skills[2] = new Habilidade("Investida Selvagem", 5, "LF", false, false, false, false, false, 20, 20);
        skills[3] = new Habilidade("Tremor Violento", 1, "AF", false, false, false, false, false, 30, 30);
    }
}
