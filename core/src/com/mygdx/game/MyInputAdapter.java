package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

class MyInputAdapter extends InputAdapter
{
    private Vector2 touchPos    = new Vector2();
    private Vector2 dragPos     = new Vector2();
    private float   radius      = 200f;
    private Game jogo;

    MyInputAdapter(Game jogo) {
        this.jogo = jogo;

    }

    @Override
    public boolean touchDown(
            int screenX,
            int screenY,
            int pointer,
            int button)
    {
        touchPos.set(screenX, Gdx.graphics.getHeight() - screenY);

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        dragPos.set(screenX, Gdx.graphics.getHeight() - screenY);
        float distance = touchPos.dst(dragPos);

        if (distance <= radius)
        {
            // gives you a 'natural' angle
            float angle =
                    MathUtils.atan2(
                            touchPos.x - dragPos.x, dragPos.y - touchPos.y)
                            * MathUtils.radiansToDegrees + 90;
            if (angle < 0)
                angle += 360;
            // move according to distance and angle
        } else
        {
            // keep moving at constant speed
        }
        return true;
    }
}