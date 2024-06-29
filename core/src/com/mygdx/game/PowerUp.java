package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class PowerUp extends GameObject {
    private float originalPaddleWidth;
    private float originalBallSpeed;
    private static final float FALL_SPEED = 500;
    private static final float DURATION = 7.0f;
    private float startTime;

    @Override
    public void update(float deltaTime) {
        y -= FALL_SPEED * deltaTime;
    }

    public enum Type {
        ENLARGE_PADDLE, SLOW_BALL, SHRINK_PADDLE, SPEED_BALL
    }

    private final Type type;

    public PowerUp(float x, float y, Type type) {
        super(x, y, 20, 20);
        this.type = type;
    }


    @Override
    public void draw(ShapeRenderer shape) {
        switch (type) {
            case ENLARGE_PADDLE:
                shape.setColor(Color.GREEN);
                break;
            case SLOW_BALL:
                shape.setColor(Color.GRAY);
                break;
            case SHRINK_PADDLE:
                shape.setColor(Color.RED);
                break;
            case SPEED_BALL:
                shape.setColor(Color.BLUE);
                break;
        }
        shape.rect(x, y, width, height);
    }

    public void applyEffect(Paddle paddle, PingBall pingBall) {
        startTime = System.currentTimeMillis() / 1000f;
        originalPaddleWidth = paddle.getWidth();
        originalBallSpeed = pingBall.getSpeed();
        switch (type) {
            case ENLARGE_PADDLE:
                paddle.enlarge();
                break;
            case SLOW_BALL:
                pingBall.slowDown();
                break;
            case SHRINK_PADDLE:
                paddle.shrink();
                break;
            case SPEED_BALL:
                pingBall.speedUp();
                break;
        }
        paddle.addPowerUp(this);
    }

    public void removeEffect(Paddle paddle, PingBall pingBall) {
        switch (type) {
            case ENLARGE_PADDLE:
            case SHRINK_PADDLE:
                paddle.setWidth(originalPaddleWidth);
                break;
            case SLOW_BALL:
            case SPEED_BALL:
                pingBall.setSpeed(originalBallSpeed);
                break;
        }
    }
    public boolean isActive() {
        float currentTime = System.currentTimeMillis() / 1000f;
        return currentTime - startTime < DURATION;
    }
}
