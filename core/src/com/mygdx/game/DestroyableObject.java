package com.mygdx.game;

public abstract class DestroyableObject extends GameObject implements Destroyeable {
    private final boolean destroyed;

    public DestroyableObject(float x, float y, float width, float height) {
        super(x, y, width, height);
        this.destroyed = false;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

}
