package com.mygdx.game;

public abstract class DestroyableObject extends GameObject implements Destroyeable {
    public DestroyableObject(int x, int y, int width, int height) {
        super(x, y, width, height);
    }
    public boolean isDestroyed = false;
}
