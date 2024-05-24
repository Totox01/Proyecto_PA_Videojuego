package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Paddle extends GameObject{
    public Paddle(float x, float y) {
        super(x, y + 50, 0.16f * Block.WORLD_WIDTH, 0.03f * Block.WORLD_HEIGHT);
    }

    public void draw(ShapeRenderer shape){
        shape.setColor(Color.BLUE);
        float x2 = x;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) x2 =x-45;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) x2=x+45;
        if (x2 > 0 && x2+width < Block.WORLD_WIDTH) {
            x = x2;
        } else if (x2 <= 0) {
            x = 0;
        } else if (x2+width >= Block.WORLD_WIDTH) {
            x = Block.WORLD_WIDTH - width;
        }
        shape.rect(x, y, width, height);
    }
}

