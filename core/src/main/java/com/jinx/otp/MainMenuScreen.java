package com.jinx.otp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/** First screen of the application. Displayed after the application is created. */
public class MainMenuScreen implements Screen {

    private final float WORLD_WIDTH = 5f;
    private final float WORLD_HEIGHT = 3f;

    private LittlePlatformerGame game;

    private BitmapFont font;
    private Viewport viewport;
    private SpriteBatch batch;


    public MainMenuScreen(LittlePlatformerGame game) {
        this.game = game;
        batch = game.batch;
        setupFont();
        setupViewport();
    }

    private void setupFont() {
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.setUseIntegerPositions(false);
        float windowAdjustedHeight = WORLD_HEIGHT / Gdx.graphics.getHeight();
        font.getData().setScale(windowAdjustedHeight);
    }

    private void setupViewport() {
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        input();
        draw();
    }

    private void input() {
        if (Gdx.input.isTouched()) {
            game.changeScreen(Screens.GAME, this);
        }
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();

        final float titleX = 1f;
        final float titleY = WORLD_HEIGHT - 1f;
        final String titleText = "Welcome to my first little plattformer! <3";
        font.draw(batch, titleText, titleX, titleY);

        final float subTitleX = 1f;
        final float subTitleY = WORLD_HEIGHT - 2;
        final String subTitleText = "Press anywhere to start.";
        font.draw(batch, subTitleText, subTitleX, subTitleY);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // If the window is minimized on a desktop (LWJGL3) platform, width and height are 0, which causes problems.
        // In that case, we don't resize anything, and wait for the window to be a normal size before updating.
        if(width <= 0 || height <= 0) return;

        viewport.update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        font.dispose();
    }
}