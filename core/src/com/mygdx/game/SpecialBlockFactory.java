package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;

public class SpecialBlockFactory implements BlockFactory {
    @Override
    public GameBlock createBlock(float x, float y, float width, float height, Color color) {
        return new SpecialBlock.Builder(x, y)
                .width(width)
                .height(height)
                .color(color)
                .powerUp(x, y)
                .build();
    }
}
