package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class PingBall extends GameObject implements Colisionable {
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

	public void draw(ShapeRenderer shape) {
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
		if (x < 0) x = 0;
		if (x > Block.WORLD_WIDTH - width) x = Block.WORLD_WIDTH - width;
	}

	@Override
	public boolean collidesWith(GameObject obj, GameObject other) {
		float objX = obj.getX();
		float objY = obj.getY();
		float objWidth = obj.getWidth();
		float objHeight = obj.getHeight();
		float otherX = other.getX();
		float otherY = other.getY();
		float otherWidth = other.getWidth();
		float otherHeight = other.getHeight();

		boolean intersectsX = (objX < otherX + otherWidth) && (objX + objWidth > otherX);
		boolean intersectsY = (objY < otherY + otherHeight) && (objY + objHeight > otherY);

		return intersectsX && intersectsY;
	}

	@Override
	public boolean collision(GameObject other) {
		if (other instanceof Paddle) {
			if (collidesWith(this, other)) {
				ySpeed = -ySpeed;
				float newY = other.getY() + other.getHeight() + 1;
				setPosition(getX(), newY);
				setColor(Color.GREEN);
				return true;
			} else {
				setColor(Color.WHITE);
			}
		}
		return false;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void reset(float x, float y) {
		setPosition(x, y);
		setStill(true);
	}
}