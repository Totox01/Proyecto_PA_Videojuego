package com.mygdx.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameBlock extends DestroyableObject {
    public GameBlock(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    @Override
    public void draw(ShapeRenderer shape) {
    }
}
