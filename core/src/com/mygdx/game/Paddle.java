package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Paddle extends GameObject {
    private static Paddle instance = null;
    private static final float SPEED = 1250; // Ajuste de la velocidad del paddle

    public Paddle(float x, float y) {
        super(x, y + 50, 0.16f * Block.WORLD_WIDTH, 0.03f * Block.WORLD_HEIGHT);
    }

    public static Paddle getInstance(float x, float y) {
        if (instance == null) {
            instance = new Paddle(x, y);
        }
        return instance;
    }

    public void draw(ShapeRenderer shape) {
        shape.setColor(Color.BLUE);
        shape.rect(x, y, width, height);
    }

    public void mover() {
        float x2 = x;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) x2 = x - SPEED * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) x2 = x + SPEED * Gdx.graphics.getDeltaTime();
        if (x2 > 0 && x2 + width < Block.WORLD_WIDTH) {
            x = x2;
        } else if (x2 <= 0) {
            x = 0;
        } else if (x2 + width >= Block.WORLD_WIDTH) {
            x = Block.WORLD_WIDTH - width;
        }
    }
}