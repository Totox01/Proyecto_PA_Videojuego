package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;

public interface BlockFactory {
    GameBlock createBlock(float x, float y, float width, float height, Color color);
}
