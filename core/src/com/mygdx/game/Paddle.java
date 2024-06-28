package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

public class Paddle extends GameObject {
    private final ArrayList<PowerUp> activePowerUps;
    private static Paddle instance = null;
    private static final float SPEED = 1250; // Ajuste de la velocidad del paddle
    private final float initialWidth;

    public Paddle(float x, float y) {
        super(x, y, 0.16f * Block.WORLD_WIDTH, 0.03f * Block.WORLD_HEIGHT);
        initialWidth = width;
        this.activePowerUps = new ArrayList<>();
    }

    public void reset() {
        x = (Block.WORLD_WIDTH / 2) - (width / 2); // Center the paddle
        width = initialWidth; // Reset the paddle width
    }

    public static Paddle getInstance(float x, float y) {
        if (instance == null) {
            instance = new Paddle(x, y);
        }
        return instance;
    }

    public void setWidth(float width) {
        this.width = width;
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

    public void enlarge() {
        this.width *= 1.2f;
    }

    public void shrink() {
        this.width *= 0.8f;
    }

    public void update(PingBall pingBall) { // Remove the unused deltaTime parameter
        // Check for active power-ups and remove the ones that are no longer active
        for (int i = 0; i < activePowerUps.size(); i++) {
            PowerUp powerUp = activePowerUps.get(i);
            if (!powerUp.isActive()) {
                powerUp.removeEffect(this, pingBall); // Pass the PingBall object to the removeEffect method
                activePowerUps.remove(i);
                i--;
            }
        }
    }

    public void addPowerUp(PowerUp powerUp) {
        activePowerUps.add(powerUp);
    }
}