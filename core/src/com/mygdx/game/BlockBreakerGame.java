package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import java.util.ArrayList;
import java.util.List;

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
	private List<Music> musicList;
	private int currentTrackIndex;

	// Menu
	private OptionsMenu optionsMenu;

	// Backgrounds
	private List<Texture> backgrounds;
	private int currentBackgroundIndex;
	private float transitionAlpha;
	private float transitionTime;
	private float timeSinceLastChange;

	@Override
	public void create() {
		inicializarGraficos();
		inicializarObjetos();
		inicializarSonidos();
		inicializarMusicaDeFondo();
		inicializarFondos();
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
		ball = new PingBall(pad.getX() + pad.getWidth() / 2 - 10, pad.getY() + pad.getHeight() + 1, 10, true);
		vidas = 3;
		puntaje = 0;
	}

	private void inicializarMusicaDeFondo() {
		musicList = new ArrayList<>();
		musicList.add(Gdx.audio.newMusic(Gdx.files.internal("background1.mp3")));
		musicList.add(Gdx.audio.newMusic(Gdx.files.internal("background2.mp3")));
		musicList.add(Gdx.audio.newMusic(Gdx.files.internal("background3.mp3")));

		currentTrackIndex = 0;
		backgroundMusic = musicList.get(currentTrackIndex);
		backgroundMusic.setLooping(true); // Configurar para que la música se repita
		backgroundMusic.setVolume(0.3f);
		backgroundMusic.play(); // Iniciar la reproducción de la música
	}

	public void nextTrack() {
		backgroundMusic.stop();
		currentTrackIndex = (currentTrackIndex + 1) % musicList.size();
		backgroundMusic = musicList.get(currentTrackIndex);
		backgroundMusic.setLooping(true);
		backgroundMusic.setVolume(0.3f);
		backgroundMusic.play();
	}

	public Music getBackgroundMusic() {
		return backgroundMusic;
	}

	private void inicializarSonidos() {
		bounceSound = Gdx.audio.newSound(Gdx.files.internal("bounce.wav"));
		breakSound = Gdx.audio.newSound(Gdx.files.internal("break.wav"));
	}

	private void inicializarFondos() {
		backgrounds = new ArrayList<>();
		backgrounds.add(new Texture(Gdx.files.internal("background1.png")));
		backgrounds.add(new Texture(Gdx.files.internal("background2.png")));
		backgrounds.add(new Texture(Gdx.files.internal("background3.png")));

		currentBackgroundIndex = 0;
		transitionAlpha = 0;
		transitionTime = 40.0f; // Duración de la transición en segundos
		timeSinceLastChange = 0;
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
		block.update(Gdx.graphics.getDeltaTime());
		viewport.apply();
		shape.setProjectionMatrix(camera.combined);

		batch.setProjectionMatrix(camera.combined);

		timeSinceLastChange += Gdx.graphics.getDeltaTime();

		if (timeSinceLastChange >= transitionTime) {
			currentBackgroundIndex = (currentBackgroundIndex + 1) % backgrounds.size();
			timeSinceLastChange = 0;
		}

		transitionAlpha = Math.min(1, timeSinceLastChange / transitionTime);

		batch.begin();
		batch.draw(backgrounds.get(currentBackgroundIndex), 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

		int nextBackgroundIndex = (currentBackgroundIndex + 1) % backgrounds.size();
		batch.setColor(1, 1, 1, transitionAlpha);
		batch.draw(backgrounds.get(nextBackgroundIndex), 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
		batch.setColor(1, 1, 1, 1);

		batch.end();

		shape.begin(ShapeRenderer.ShapeType.Filled);

		pad.draw(shape);
		pad.mover();
		pad.update(ball);
		ball.move(pad);

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
		ball.draw(shape);
		checkUserInput();

		shape.end();
		dibujaTextos();
	}

	private void movimientoPelota() {
		if (!ball.isStill()) {
			ball.update(Gdx.graphics.getDeltaTime());
		}
	}

	private void manejarLimites() {
		vidas--;
		pad.reset();
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
		block.clearPowerUps();
		block.createBlocks(2 + nivel);
		pad.reset(); // Reset the paddle
		ball.reset(pad.getX() + pad.getWidth() / 2 - ball.getWidth() / 2, pad.getY() + pad.getHeight() + 1);
	}



	private void manejarColisiones() {
		if (block.collision(ball)) {
			breakSound.play();
			ball.handleBlockCollision();
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
		font.draw(batch, vidasText, viewport.getWorldWidth() - vidasWidth - 150, 70);
		batch.end();
	}

	private void drawMenu() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		viewport.apply();
		shape.setProjectionMatrix(camera.combined);
		optionsMenu.render(batch, shape);
	}

	private void checkUserInput() {
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && ball.isStill()) {
			ball.setStill(false);
		}
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
		for (Texture texture : backgrounds) {
			texture.dispose();
		}
	}
}

