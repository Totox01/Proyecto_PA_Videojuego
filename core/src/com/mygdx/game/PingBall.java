package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class PingBall extends GameObject implements Colisionable {
	private float speed;
	private float xSpeed;
	private float ySpeed;
	private Color color;
	private boolean isStill;

	public PingBall(float x, float y, int size, boolean startsStill) {
		super(x, y, size * 2, size * 2);
		this.xSpeed = 500; // Adjust the initial speed of the ball
		this.ySpeed = 500; // Adjust the initial speed of the ball
		this.speed = 500; // Adjust the initial speed of the ball
		this.isStill = startsStill;
		this.color = isStill ? Color.WHITE : Color.GREEN;
	}

	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
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
		x += xSpeed * Gdx.graphics.getDeltaTime();
		y += ySpeed * Gdx.graphics.getDeltaTime();
		if (x < 0 || x > Block.WORLD_WIDTH - width) {
			xSpeed = -xSpeed;
			normalizeSpeed();
		}
		if (y > Block.WORLD_HEIGHT - height) {
			ySpeed = -ySpeed;
			normalizeSpeed();
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
				handlePaddleCollision((Paddle) other);
				return true;
			} else {
				setColor(Color.WHITE);
			}
		}
		return false;
	}

	private void handlePaddleCollision(Paddle paddle) {
		ySpeed = Math.abs(ySpeed); // Asegurar que la pelota siempre se mueva hacia arriba

		// Calcular la posición relativa de la colisión en el paddle
		float ballCenter = this.getX() + this.getWidth() / 2;
		float paddleCenter = paddle.getX() + paddle.getWidth() / 2;
		float relativeIntersectX = (ballCenter - paddleCenter) / (paddle.getWidth() / 2);

		// Ajustar la velocidad X en función de la posición de colisión
		xSpeed = relativeIntersectX * speed;

		// Recalcular y normalizar las velocidades para mantener la velocidad constante
		normalizeSpeed();

		// Mover la pelota encima del paddle para evitar múltiples colisiones
		float newY = paddle.getY() + paddle.getHeight() + 1;
		setPosition(getX(), newY);

		// Cambiar el color de la pelota para indicar colisión
		setColor(Color.GREEN);
	}

	public void handleBlockCollision() {
		ySpeed = -ySpeed;
		normalizeSpeed();
	}

	private void normalizeSpeed() {
		float currentSpeed = (float) Math.sqrt(xSpeed * xSpeed + ySpeed * ySpeed);
		xSpeed = (xSpeed / currentSpeed) * speed;
		ySpeed = (ySpeed / currentSpeed) * speed;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void reset(float x, float y) {
		setPosition(x, y);
		setStill(true);
	}

	public void slowDown() {
		this.xSpeed *= 0.8f;
		this.ySpeed *= 0.8f;
	}

	public void speedUp() {
		this.xSpeed *= 1.2f;
		this.ySpeed *= 1.2f;
	}

	public void move(Paddle pad) {
		if (isStill()) {
			setPosition(pad.getX() + pad.getWidth() / 2 - getWidth() / 2, pad.getY() + pad.getHeight() + 1);
		} else {
			update();
		}
	}
}