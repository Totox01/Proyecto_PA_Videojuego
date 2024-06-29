package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.viewport.Viewport;

public class OptionsMenu {
    private final BitmapFont font;
    private boolean visible;
    private float volume;
    private final BlockBreakerGame game;
    private final Viewport viewport;

    public OptionsMenu(BlockBreakerGame game, Viewport viewport) {
        this.game = game;
        this.viewport = viewport;
        this.font = new BitmapFont();
        this.font.getData().setScale(6); // Ajustar la escala del texto
        this.font.setColor(Color.WHITE); // Ajustar el color del texto
        this.visible = false;
        this.volume = game.getBackgroundMusic().getVolume();
    }

    public boolean isVisible() {
        return visible;
    }

    public void toggleVisibility() {
        this.visible = !this.visible;
    }

    public void update() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            volume = Math.min(1.0f, volume + 0.01f);
            game.getBackgroundMusic().setVolume(volume);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            volume = Math.max(0.0f, volume - 0.01f);
            game.getBackgroundMusic().setVolume(volume);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            game.nextTrack();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            toggleVisibility();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            Gdx.app.exit();
        }
    }


    public void render(SpriteBatch batch, ShapeRenderer shape) {
        if (!visible) return;

        shape.begin(ShapeType.Filled);
        shape.setColor(0, 0, 0, 0.7f);
        shape.rect(viewport.getWorldWidth() / 2 - 220, viewport.getWorldHeight() / 2 - 140, 440, 280); // Centrar el fondo del menú
        shape.end();

        batch.begin();
        font.draw(batch, "Menu de opciones", viewport.getWorldWidth() / 2 - 320, viewport.getWorldHeight() / 2 + 500);
        font.draw(batch, "Volumen: " + (int) (volume * 100) + "%", viewport.getWorldWidth() / 2 - 270, viewport.getWorldHeight() / 2 + 250);
        font.draw(batch, "Press UP/DOWN para ajustar el volume", viewport.getWorldWidth() / 2 - 830, viewport.getWorldHeight() / 2 + 100);
        font.draw(batch, "Press RIGHT para cambiar de cancion", viewport.getWorldWidth() / 2 - 800, viewport.getWorldHeight() / 2 - 50);
        font.draw(batch, "Press R para continuar", viewport.getWorldWidth() / 2 + 0, viewport.getWorldHeight() / 2 - 300);
        font.draw(batch, "Press E para salir", viewport.getWorldWidth() / 2 - 870, viewport.getWorldHeight() / 2 - 300);
        batch.end();
    }
}
