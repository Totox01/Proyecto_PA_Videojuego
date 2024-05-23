package com.mygdx.game;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class PingBall extends GameObject {
	    private int xSpeed;
	    private int ySpeed;
	    private Color color = Color.WHITE;
	    private boolean estaQuieto;
	    
	    public PingBall(int x, int y, int size, int xSpeed, int ySpeed, boolean iniciaQuieto) {
			super(x, y, size, size);
	        this.xSpeed = xSpeed;
	        this.ySpeed = ySpeed;
	        estaQuieto = iniciaQuieto;
	    }
	    
	    public boolean estaQuieto() {
	    	return estaQuieto;
	    }
	    public void setEstaQuieto(boolean bb) {
	    	estaQuieto=bb;
	    }
	    public void setXY(int x, int y) {
	    	this.x = x;
	        this.y = y;
	    }

	    
	    public void draw(ShapeRenderer shape){
	        shape.setColor(color);
	        shape.rect(x, y, getWidth(), getHeight());
	    }
	    
	    public void update() {
	    	if (estaQuieto) return;
	        x += xSpeed;
	        y += ySpeed;
	        if (x-width < 0 || x+width > Gdx.graphics.getWidth()) {
	            xSpeed = -xSpeed;
	        }
	        if (y+height > Gdx.graphics.getHeight()) {
	            ySpeed = -ySpeed;
	        }
	    }
	    
	    public void checkCollision(Paddle paddle) {
	        if(collidesWith(paddle)){
	            color = Color.GREEN;
	            ySpeed = -ySpeed;
	        }
	        else{
	            color = Color.WHITE;
	        }
	    }
	    private boolean collidesWith(Paddle pp) {

	    	boolean intersectaX = (pp.getX() + pp.getWidth() >= x-width) && (pp.getX() <= x+width);
	        boolean intersectaY = (pp.getY() + pp.getHeight() >= y-height) && (pp.getY() <= y+height);
	    	return intersectaX && intersectaY;
	    }
	    
	    public void checkCollision(DestroyableObject block) {
	        if(collidesWith(block)){
	            ySpeed = - ySpeed;
				block.isDestroyed = true;
	        }
	    }
	    private boolean collidesWith(GameObject bb) {

	    	boolean intersectaX = (bb.x + bb.width >= x-width) && (bb.x <= x+width);
	        boolean intersectaY = (bb.y + bb.height >= y-height) && (bb.y <= y+height);
	    	return intersectaX && intersectaY;
	    }
	    
	}
