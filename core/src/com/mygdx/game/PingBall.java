package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class PingBall extends GameObject {
	private int xSpeed;
	public static int ySpeed;
	private Color color;
	private boolean isStill;

	public PingBall(float x, float y, int size, int xSpeed, int ySpeed, boolean startsStill) {
		super(x, y, size * 2, size * 2);
		this.xSpeed = xSpeed * 2;
		PingBall.ySpeed = ySpeed * 2;
		this.isStill = startsStill;
		this.color = isStill ? Color.WHITE : Color.GREEN;
	}

	public boolean isStill() {
		return isStill;
	}

	public void setStill(boolean isStill) {
		this.isStill = isStill;
	}

	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void draw(ShapeRenderer shape){

		shape.setColor(color);
		shape.rect(x, y, getWidth(), getHeight());
	}

	public void update() {
		if (isStill) return;
		x += xSpeed;
		y += ySpeed;
		if (x < 0 || x > Block.WORLD_WIDTH - width) {
			xSpeed = -xSpeed;
		}
		if (y > Block.WORLD_HEIGHT - height) {
			ySpeed = -ySpeed;
		}
		// Asegurarse de que la pelota no se sale de la pantalla
		if (x < 0) x = 0;
		if (x > Block.WORLD_WIDTH - width) x = Block.WORLD_WIDTH - width;
	}


	public void checkCollision(Paddle paddle) {
		if(collidesWith(paddle)){
			setColor(Color.GREEN);
			ySpeed = -ySpeed;
		}
		else{
			setColor(Color.WHITE);
		}
	}

	private boolean collidesWith(GameObject gameObject) {
		boolean intersectsX = (gameObject.getX() < x + width) && (gameObject.getX() + gameObject.getWidth() > x);
		boolean intersectsY = (gameObject.getY() < y + height) && (gameObject.getY() + gameObject.getHeight() > y);
		return intersectsX && intersectsY;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}