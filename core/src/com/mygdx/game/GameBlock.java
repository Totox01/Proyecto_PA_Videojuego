package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Random;

public class GameBlock extends DestroyableObject {
    public GameBlock(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    @Override
    public void draw(ShapeRenderer shape) {
        Random r = new Random((long) (this.getX() + this.getY()));
        Color cc = new Color(0.1f + r.nextFloat(), r.nextFloat(), r.nextFloat(), 1);
        shape.setColor(cc);
        shape.rect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
}
