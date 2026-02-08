package com.jinx.otp;

import static com.jinx.otp.constants.Constants.PLAYER_HEIGHT;
import static com.jinx.otp.constants.Constants.PLAYER_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.jinx.otp.input_processors.PlayerMoveInputProcessor;
import com.jinx.otp.map.GameMap;
import com.jinx.otp.map.MapLoader;
import com.jinx.otp.player.Player;
import com.jinx.otp.services.InputProcessorService;

public class GameScreen implements Screen {

    private final float DEFAULT_CAMERA_WIDTH = 10f;

    private LittlePlatformerGame game;

    private Camera camera;

    private GameMap map;
    private MapLoader mapLoader;
    private Player player;

    private InputProcessorService inputProcessorService = InputProcessorService.getInputProcessorService();

    public GameScreen(LittlePlatformerGame game) {
        this.game = game;
        setupCamera();
        setupMap();
        setupPlayer();
        Gdx.input.setInputProcessor(new PlayerMoveInputProcessor());
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
        logic(delta);
        draw();
    }


    private void centerCameraOnPlayer() {
        final float playerX = player.getPosX();
        final float playerY = player.getPosY();
        
        final float cameraX = playerX + (PLAYER_WIDTH / 2);
        final float minCameraX = camera.viewportWidth / 2;
        final float maxCameraX = map.getWidth() - (camera.viewportWidth / 2);
        final float adjustedCameraX = MathUtils.clamp(cameraX, minCameraX, maxCameraX);

        final float cameraY = playerY + (PLAYER_HEIGHT / 2);
        final float minCameraY = camera.viewportHeight / 2;
        final float maxCameraY = map.getHeight() - (camera.viewportHeight / 2);
        final float adjustedCameraY = MathUtils.clamp(cameraY, minCameraY, maxCameraY);

        final float newCameraZ = camera.position.z;
        camera.position.set(adjustedCameraX, adjustedCameraY, newCameraZ);
        camera.update();
    }

    private void logic(float delta) {
        inputProcessorService.processPlayerMovement(delta, player);
        centerCameraOnPlayer();
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
