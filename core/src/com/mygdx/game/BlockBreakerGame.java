package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import static com.mygdx.game.PingBall.ySpeed;

public class BlockBreakerGame extends ApplicationAdapter {
	private OrthographicCamera camera;
	private Viewport viewport;
	private Block block;
	private SpriteBatch batch;
	private BitmapFont font;
	private ShapeRenderer shape;
	private PingBall ball;
	private Paddle pad;
	private int vidas;
	public static int puntaje;
	private int nivel;

	@Override
	public void create () {
		camera = new OrthographicCamera();
		viewport = new ExtendViewport(Block.WORLD_WIDTH, Block.WORLD_HEIGHT, camera);
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.getData().setScale(6, 4);
		shape = new ShapeRenderer();
		pad = new Paddle(Math.round(viewport.getWorldWidth()/2-50),40);
		block = new Block();
		nivel = 1;
		block.createBlocks(2+nivel);
		ball = new PingBall(pad.getX()+pad.getWidth()/2-5, pad.getY()+pad.getHeight()+11, 10, 5, 7, true);
		vidas = 3;
		puntaje = 0;
	}

	public void dibujaTextos() {
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		GlyphLayout layout = new GlyphLayout();
		String vidasText = "Vidas : " + vidas;
		layout.setText(font, vidasText);
		float vidasWidth = layout.width;
		font.draw(batch, "Puntos: " + puntaje, 10, 70); // Mueve las letras más hacia el centro
		font.draw(batch, vidasText, viewport.getWorldWidth() - vidasWidth - 150, 70); // Mueve las letras más hacia el centro
		batch.end();
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		viewport.apply();
		shape.setProjectionMatrix(camera.combined);
		shape.begin(ShapeRenderer.ShapeType.Filled);
		pad.draw(shape);
		// monitorear inicio del juego
		if (ball.isStill()) {
			ball.setPosition(pad.getX()+pad.getWidth()/2-5,pad.getY()+pad.getHeight()+11);
			if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) ball.setStill(false);
		} else {
			ball.update();
		}
		//verificar si se fue la bola x abajo
		if (ball.getY()<0) {
			vidas--;
			ball = new PingBall(pad.getX()+pad.getWidth()/2-5, pad.getY()+pad.getHeight()+11, 10, 5, 7, true);
		}
		// verificar game over
		if (vidas<=0) {
			vidas = 3;
			nivel = 1;
			block.createBlocks(2+nivel);
		}
		// verificar si el nivel se terminó
		if (block.isEmpty()) {
			nivel++;
			block.createBlocks(2+nivel);
			ball = new PingBall(pad.getX()+pad.getWidth()/2-5, pad.getY()+pad.getHeight()+11, 10, 5, 7, true);
		}


		block.draw(shape);
		if (block.collision(ball)){
			ySpeed = - ySpeed;
		}
		ball.checkCollision(pad);
		ball.draw(shape);
		shape.end();
		dibujaTextos();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}

	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		shape.dispose();
	}
}

