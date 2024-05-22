package com.mygdx.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;
import java.util.Random;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Gdx;

public class Block implements Colisionable{
    Color cc;
    private ArrayList<DestroyableObject> blocks;

    public void createBlocks(int filas){
        int blockWidth = 70;
        int blockHeight = 26;
        int y = Gdx.graphics.getHeight();
        for (int cont = 0; cont<filas; cont++ ) {
            y -= blockHeight+10;
            for (int x = 5; x < Gdx.graphics.getWidth(); x += blockWidth + 10) {
                blocks.add(new DestroyableObject(x, y, blockWidth, blockHeight) {
                });
            }
        }
    }
    private void destroy(int i) {
        if (blocks.get(i).isDestroyed) {
            blocks.remove(i);
        }
    }
    public boolean isEmpty(){
        return blocks.isEmpty();
    }
    public int blockCount(){
        return blocks.size();
    }

    @Override
    public boolean collidesWith(GameObject obj, GameObject other) {
        return obj.getX() < other.getX() + other.getWidth() &&
                obj.getX() + obj.getWidth() > other.getX() &&
                obj.getY() < other.getY() + other.getHeight() &&
                obj.getY() + obj.getHeight() > other.getY();
    }
    @Override
    public boolean collision(GameObject ball) {
        for (int i = 0; i < blocks.size(); i++) {
            if (collidesWith(blocks.get(i),ball)) {
                blocks.get(i).isDestroyed = true;
                destroy(i);
                i--;
                return true;
            }
        }
        return false;
    }

    public void draw(ShapeRenderer shape){
    	for (int i = 0; i < blocks.size(); i++) {
            if (!blocks.get(i).isDestroyed) {
                Random r = new Random(blocks.get(i).getX()+blocks.get(i).getY());
                cc = new Color(0.1f+r.nextFloat(1), r.nextFloat(1), r.nextFloat(1), 10);
                shape.setColor(cc);
                shape.rect(blocks.get(i).getX(), blocks.get(i).getY(), blocks.get(i).getWidth(), blocks.get(i).getHeight());
            }
        }
    }
}
