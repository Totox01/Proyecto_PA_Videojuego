package com.mygdx.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class DestroyableObject extends GameObject implements Destroyeable {
    private final boolean destroyed;

    public DestroyableObject(float x, float y, float width, float height) {
        super(x, y, width, height);
        this.destroyed = false;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
    public abstract void draw(ShapeRenderer shape);
}
