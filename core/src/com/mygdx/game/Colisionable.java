package com.mygdx.game;

import java.awt.*;

public interface Colisionable {
    boolean collision(GameObject other);
    boolean collidesWith(GameObject obj, GameObject other);
}
