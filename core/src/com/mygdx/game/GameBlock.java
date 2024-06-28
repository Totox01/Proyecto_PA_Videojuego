package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameBlock extends DestroyableObject {
    private final Color color;

    GameBlock(Builder builder) {
        super(builder.x, builder.y, builder.width, builder.height);
        this.color = builder.color;
    }

    public static class Builder {
        private final float x;
        private final float y;
        private float width;
        private float height;
        private Color color;

        public Builder(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public Builder width(float width) {
            this.width = width;
            return this;
        }

        public Builder height(float height) {
            this.height = height;
            return this;
        }

        public Builder color(Color color) {
            this.color = color;
            return this;
        }

        public GameBlock build() {
            return new GameBlock(this);
        }
    }

    @Override
    public void draw(ShapeRenderer shape) {
        shape.setColor(this.color);
        shape.rect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
}
