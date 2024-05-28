package com.mygdx.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import java.util.ArrayList;
import java.util.Random;

public class Block implements Colisionable {
    public static final float WORLD_WIDTH = 1920;
    public static final float WORLD_HEIGHT = 1080;
    private static final float BLOCK_WIDTH = 0.09f * WORLD_WIDTH;
    private static final float BLOCK_HEIGHT = 0.05f * WORLD_HEIGHT;

    private final ArrayList<GameBlock> blocks;
    private final OrthographicCamera camera;

    public Block() {
        this.blocks = new ArrayList<>();
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
    }

    public void createBlocks(int filas) {
        float y = WORLD_HEIGHT - BLOCK_HEIGHT + 50;
        for (int cont = 0; cont < filas; cont++) {
            y -= BLOCK_HEIGHT + 20;
            for (float x = 5; x < WORLD_WIDTH; x += BLOCK_WIDTH + 20) {
                blocks.add(new GameBlock(x, y, BLOCK_WIDTH, BLOCK_HEIGHT));
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
    public boolean collision(GameObject ball) {
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
        return false;
    }


    public void draw(ShapeRenderer shape) {
        shape.setProjectionMatrix(camera.combined);
        for (GameBlock block : blocks) {
            if (!block.isDestroyed()) {
                Random r = new Random((long) (block.getX() + block.getY()));
                Color cc = new Color(0.1f + r.nextFloat(), r.nextFloat(), r.nextFloat(), 1);
                shape.setColor(cc);
                shape.rect(block.getX(), block.getY(), block.getWidth(), block.getHeight());
            }
        }
    }
}
