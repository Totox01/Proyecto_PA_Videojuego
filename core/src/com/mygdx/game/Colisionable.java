package com.mygdx.game;

public interface Colisionable {
    boolean collision(GameObject other);
    boolean collidesWith(GameObject obj, GameObject other);
}
