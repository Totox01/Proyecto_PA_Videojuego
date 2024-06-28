package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import java.util.ArrayList;

public class Block implements Colisionable {
    public static final float WORLD_WIDTH = 1920;
    public static final float WORLD_HEIGHT = 1080;
    private static final float BLOCK_WIDTH = 0.09f * WORLD_WIDTH;
    private static final float BLOCK_HEIGHT = 0.05f * WORLD_HEIGHT;

    private final ArrayList<GameBlock> blocks;
    private final ArrayList<PowerUp> powerUps;
    private final OrthographicCamera camera;

    public Block() {
        this.blocks = new ArrayList<>();
        this.powerUps = new ArrayList<>();
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
    }

    public void createBlocks(int filas) {
        float y = WORLD_HEIGHT - BLOCK_HEIGHT + 50;
        for (int cont = 0; cont < filas; cont++) {
            y -= BLOCK_HEIGHT + 20;
            for (float x = 5; x < WORLD_WIDTH; x += BLOCK_WIDTH + 20) {
                if (Math.random() < 0.1) { // 10% chance to create a special block
                    SpecialBlock block = new SpecialBlock.Builder(x, y)
                            .width(BLOCK_WIDTH)
                            .height(BLOCK_HEIGHT)
                            .color(Color.GOLD) // Special blocks are gold
                            .powerUp(x, y) // Pass the x and y coordinates to the powerUp method
                            .build();
                    blocks.add(block);
                }else {
                    Color randomColor = new Color((float) Math.random(), (float) Math.random(), (float) Math.random(), 1);
                    GameBlock block = new GameBlock.Builder(x, y)
                            .width(BLOCK_WIDTH)
                            .height(BLOCK_HEIGHT)
                            .color(randomColor) // Normal blocks are a random color
                            .build();
                    blocks.add(block);
                }
            }
        }
    }

    public void clearBlocks() {
        blocks.clear();
    }

    private void destroy(int i) {
        if (i >= 0 && i < blocks.size()) {
            GameBlock block = blocks.get(i);
            if (!block.isDestroyed()) {
                // If the block is a special block, create a power-up
                if (block instanceof SpecialBlock) {
                    PowerUp powerUp = ((SpecialBlock) block).getPowerUp();
                    powerUps.add(powerUp);
                }
                blocks.remove(i);
                BlockBreakerGame.puntaje++;
            }
        }
    }

    public boolean isEmpty() {
        return blocks.isEmpty();
    }

    @Override
    public boolean collidesWith(GameObject obj, GameObject other) {
        return obj.getX() < other.getX() + other.getWidth() &&
                obj.getX() + obj.getWidth() > other.getX() &&
                obj.getY() < other.getY() + other.getHeight() &&
                obj.getY() + obj.getHeight() > other.getY();
    }

    @Override
    public boolean collision(GameObject gameObject) {
        if (!(gameObject instanceof PingBall)) {
            return false;
        }

        PingBall ball = (PingBall) gameObject;

        for (int i = 0; i < blocks.size(); i++) {
            GameBlock block = blocks.get(i);
            if (block.isDestroyed()) {
                continue; // Si el bloque ya está destruido, pasa al siguiente
            }
            if (collidesWith(block, ball)) {
                destroy(i);
                return true;
            }
        }

        // Check for collisions between the paddle and power-ups
        for (int i = 0; i < powerUps.size(); i++) {
            PowerUp powerUp = powerUps.get(i);
            if (collidesWith(powerUp, Paddle.getInstance(0, 0))) {
                powerUp.applyEffect(Paddle.getInstance(0, 0), ball); // Apply the power-up effect to the paddle
                powerUps.remove(i);
                i--;
            }
        }

        return false;
    }


    public void draw(ShapeRenderer shape) {
        shape.setProjectionMatrix(camera.combined);
        for (GameBlock block : blocks) {
            if (!block.isDestroyed()) {
                block.draw(shape);
            }
        }
        for (PowerUp powerUp : powerUps) {
            powerUp.draw(shape);
        }
    }

    public void update(float deltaTime) {
        for (PowerUp powerUp : powerUps) {
            powerUp.update(deltaTime);
        }
    }

    public void clearPowerUps() {
        powerUps.clear();
    }
}
