package com.mygdx.game.Entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Arqueiro extends Personagem {
    public Arqueiro(Boolean resVeneno, int[] modificadores) {
        super(resVeneno, modificadores);

        vida += 70;
        ataque += 2;
        armadura += 1;
        poder += 2;
        resistencia += 2;
        velocidade += 3;

        TextureRegion[][] andando = TextureRegion.split(new Texture("arqueiro_andando.png"), SIZE, SIZE);
        for(int i = 0; i < 4; i++)
            rolls[i] = new Animation(SPEED, andando[i]); //0=UP,1=LEFT,2=DOWN,3=RIGHT
        TextureRegion[][] atacando = TextureRegion.split(new Texture("arqueiro_atacando.png"), SIZE, SIZE);
        for(int i = 4; i < 8; i++)
            rolls[i] = new Animation(SPEED, atacando[i-4]); //0=UP,1=LEFT,2=DOWN,3=RIGHT
        TextureRegion[][] magia = TextureRegion.split(new Texture("arqueiro_castando.png"), SIZE, SIZE);
        for(int i = 8; i < 12; i++)
            rolls[i] = new Animation(SPEED, magia[i-8]); //0=UP,1=LEFT,2=DOWN,3=RIGHT
        TextureRegion[][] morte  = TextureRegion.split(new Texture("arqueiro_morrendo.png"), SIZE, SIZE);
        rolls[12] = new Animation(SPEED, morte[0]);
        texture = new Texture("arqueiro.png");
        info = new Texture("infoArqueiro.png");

        setMaxMana(50);
        setMaxVida(70);

        // Habilidade(nome, alcance, tipo, envenenar, congelar, danoExtra, lentidao, stun, mana, dano);
        skills[0] = new Habilidade("Flechada", 4, "SF", false, false, false, false, false, 0, 3);
        skills[1] = new Habilidade("Atirar em Movimento", 4, "SV", false, false, false, false, false, 15, 3);
        skills[2] = new Habilidade("Armadilha de Caçador", 3, "SC", false, false, false, false, true, 20, 0);
        skills[3] = new Habilidade("Flecha Envenenada", 4, "SF", true, false, false, false, false, 40, 20);
    }
}