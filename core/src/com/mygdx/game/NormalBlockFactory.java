package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;

public class NormalBlockFactory implements BlockFactory {
    @Override
    public GameBlock createBlock(float x, float y, float width, float height, Color color) {
        return new GameBlock.Builder(x, y)
                .width(width)
                .height(height)
                .color(color)
                .build();
    }
}
