package com.mygdx.game;

public abstract class GameObject {
    protected float x;
    protected float y;
    protected float width;
    protected float height;

    public GameObject(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }


    public float getHeight() {
        return height;
    }
    public float getWidth() {
        return width;
    }
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
}