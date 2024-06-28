package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Random;

public class SpecialBlock extends GameBlock {
    private final PowerUp powerUp;

    public SpecialBlock(Builder builder) {
        super(builder);
        this.powerUp = builder.powerUp;
    }

    public PowerUp getPowerUp() {
        return powerUp;
    }

    @Override
    public void draw(ShapeRenderer shape) {
        // Draw the block in a special way to indicate that it's a special block
        shape.setColor(Color.GOLD);
        shape.rect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    public static class Builder extends GameBlock.Builder {
        private PowerUp powerUp;

        public Builder(float x, float y) {
            super(x, y);
        }

        @Override
        public Builder width(float width) {
            super.width(width);
            return this;
        }

        @Override
        public Builder height(float height) {
            super.height(height);
            return this;
        }

        @Override
        public Builder color(Color color) {
            super.color(color);
            return this;
        }

        public Builder powerUp(float x, float y) {
            PowerUp.Type[] positiveTypes = {PowerUp.Type.ENLARGE_PADDLE, PowerUp.Type.SLOW_BALL};
            PowerUp.Type[] negativeTypes = {PowerUp.Type.SHRINK_PADDLE, PowerUp.Type.SPEED_BALL};
            PowerUp.Type powerUpType;

            // Randomly choose a positive or negative power-up
            if (Math.random() < 0.5) {
                powerUpType = positiveTypes[new Random().nextInt(positiveTypes.length)];
            } else {
                powerUpType = negativeTypes[new Random().nextInt(negativeTypes.length)];
            }

            this.powerUp = new PowerUp(x, y, powerUpType);
            return this;
        }

        @Override
        public SpecialBlock build() {
            return new SpecialBlock(this);
        }
    }
}