package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class PaddleUpdateStrategy implements UpdateStrategy {
    private static final float SPEED = 1250;

    @Override
    public void update(GameObject gameObject, float deltaTime) {
        Paddle paddle = (Paddle) gameObject;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            paddle.setX(paddle.getX() - SPEED * deltaTime);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            paddle.setX(paddle.getX() + SPEED * deltaTime);
        }
    }
}
