package com.jinx.otp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jinx.otp.map.GameMap;
import com.jinx.otp.map.MapLoader;
import com.jinx.otp.player.Player;

public class GameScreen implements Screen {

    private final float DEFAULT_CAMERA_WIDTH = 10f;

    private LittlePlatformerGame game;

    private Camera camera;

    private GameMap map;
    private MapLoader mapLoader;
    private Player player;


    public GameScreen(LittlePlatformerGame game) {
        this.game = game;
        setupCamera();
        setupMap();
        setupPlayer();
    }

    private void setupCamera() {
        final float screenWidth = Gdx.graphics.getWidth();
        final float screenHeight = Gdx.graphics.getHeight();
        final float adjustedCameraHeight = DEFAULT_CAMERA_WIDTH * (screenHeight / screenWidth);
        camera = new OrthographicCamera(DEFAULT_CAMERA_WIDTH, adjustedCameraHeight);
        final float posX = 0f;
        final float posY = 0f;
        final float posZ = 0f;
        camera.position.set(posX, posY, posZ);
        camera.update();
    }

    private void setupMap() {
        mapLoader = new MapLoader();
        map = mapLoader.load();
    }

    private void setupPlayer() {
        player = new Player(map);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        input();
        logic();
        draw();
    }

    private void input() {

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.translate(0, 1, 0);
            camera.update();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.translate(0, -1, 0);
            camera.update();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.translate(-1, 0, 0);
            camera.update();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.translate(1, 0, 0);
            camera.update();
        }

    }

    private void logic() {

    }

    private void draw() {
        final SpriteBatch batch = game.batch;
        
        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        map.draw(batch);
        player.draw(batch);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        if (0 <= width || 0 <= height) return;

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
        map.dispose();
    }

}
