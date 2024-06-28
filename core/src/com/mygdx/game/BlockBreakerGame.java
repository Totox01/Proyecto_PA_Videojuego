package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

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

	// Hitsounds y Background
	private Sound bounceSound;
	private Sound breakSound;
	private Music backgroundMusic;

	// Menu
	private OptionsMenu optionsMenu;

	@Override
	public void create() {
		inicializarGraficos();
		inicializarObjetos();
		inicializarSonidos();
		inicializarMusicaDeFondo();
		optionsMenu = new OptionsMenu(this, viewport);
	}

	private void inicializarGraficos() {
		camera = new OrthographicCamera();
		viewport = new ExtendViewport(Block.WORLD_WIDTH, Block.WORLD_HEIGHT, camera);
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.getData().setScale(6, 4);
		shape = new ShapeRenderer();
	}

	private void inicializarObjetos() {
		pad = Paddle.getInstance(Math.round(viewport.getWorldWidth() / 2 - 50), 40);
		block = new Block();
		nivel = 1;
		block.createBlocks(2 + nivel);
		ball = new PingBall(pad.getX() + pad.getWidth() / 2 - 10, pad.getY() + pad.getHeight() + 1, 10, 5, 7, true);
		vidas = 3;
		puntaje = 0;
	}

	private void inicializarMusicaDeFondo() {
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("background.mp3"));
		backgroundMusic.setLooping(true); // Configurar para que la música se repita
		backgroundMusic.setVolume(0.3f);
		backgroundMusic.play(); // Iniciar la reproducción de la música
	}

	public Music getBackgroundMusic() {
		return backgroundMusic;
	}

	private void inicializarSonidos() {
		bounceSound = Gdx.audio.newSound(Gdx.files.internal("bounce.wav"));
		breakSound = Gdx.audio.newSound(Gdx.files.internal("break.wav"));
	}

	@Override
	public void render() {
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			optionsMenu.toggleVisibility();
		}

		if (optionsMenu.isVisible()) {
			optionsMenu.update();
			drawMenu();
		} else {
			gameRender();
		}
	}

	private void gameRender() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		viewport.apply();
		shape.setProjectionMatrix(camera.combined);
		shape.begin(ShapeRenderer.ShapeType.Filled);

		pad.draw(shape);
		pad.mover();

		movimientoPelota();

		if (ball.getY() < 0) {
			manejarLimites();
		}

		if (vidas <= 0) {
			resetGame();
		}

		if (block.isEmpty()) {
			manejarNiveles();
		}

		block.draw(shape);
		manejarColisiones();

		shape.end();
		dibujaTextos();
	}

	private void movimientoPelota() {
		if (ball.isStill()) {
			ball.setPosition(pad.getX() + pad.getWidth() / 2 - ball.getWidth() / 2, pad.getY() + pad.getHeight() + 1);
			if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) ball.setStill(false);
		} else {
			ball.update();
		}
	}

	private void manejarLimites() {
		vidas--;
		ball.reset(pad.getX() + pad.getWidth() / 2 - ball.getWidth() / 2, pad.getY() + pad.getHeight() + 1);
	}

	private void manejarNiveles() {
		nivel++;
		block.createBlocks(2 + nivel);
		ball.reset(pad.getX() + pad.getWidth() / 2 - ball.getWidth() / 2, pad.getY() + pad.getHeight() + 1);
	}

	private void resetGame() {
		vidas = 3;
		nivel = 1;
		puntaje = 0;
		block.clearBlocks();
		block.createBlocks(2 + nivel);
	}



	private void manejarColisiones() {
		if (block.collision(ball)) {
			breakSound.play();
			ball.handleBlockCollision(); // Manejar colisión con bloques
		}
		if (!ball.isStill() && ball.collision(pad)) {
			bounceSound.play();
		}
		ball.draw(shape);
	}

	private void dibujaTextos() {
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		GlyphLayout layout = new GlyphLayout();
		String vidasText = "Vidas: " + vidas;
		layout.setText(font, vidasText);
		float vidasWidth = layout.width;
		font.draw(batch, "Puntos: " + puntaje, 10, 70);
		font.draw(batch, vidasText, viewport.getWorldWidth() - vidasWidth - 10, 70);
		batch.end();
	}

	private void drawMenu() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		viewport.apply();
		shape.setProjectionMatrix(camera.combined);
		optionsMenu.render(batch, shape);
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
		shape.dispose();
		bounceSound.dispose();
		breakSound.dispose();
		backgroundMusic.dispose();
	}
}

