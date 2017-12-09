package com.mygdx.game.Entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Arqueiro extends Personagem {
    public Arqueiro(Boolean resVeneno, int[] modificadores) {
        super(resVeneno, modificadores);

        vida += 100;
        ataque += 2;
        armadura += 1;
        poder += 2;
        resistencia += 2;
        velocidade += 3;

        TextureRegion[][] spritesheet = TextureRegion.split(new Texture("arqueiro.png"), 32, 32);

        for(int i = 0; i < 6; i++)
            rolls[i] = new Animation(SPEED, spritesheet[i]);

        // Habilidade(nome, alcance, tipo, envenenar, congelar, danoExtra, lentidao, stun, mana, dano);
        skills[0] = new Habilidade("Flechada", 4, "SF", false, false, false, false, false, 0, 3);
        skills[1] = new Habilidade("Atirar em Movimento", 4, "SV", false, false, false, false, false, 15, 3);
        skills[2] = new Habilidade("Armadilha de Caçador", 3, "SC", false, false, false, false, true, 20, 0);
        skills[3] = new Habilidade("Flecha Envenenada", 4, "SF", true, false, false, false, false, 40, 20);
    }
}