package com.jinx.otp;

import static com.jinx.otp.constants.Constants.DEFAULT_CAMERA_WIDTH;
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
import com.jinx.otp.map.MapModel;
import com.jinx.otp.player.Player;
import com.jinx.otp.player.PlayerModel;
import com.jinx.otp.services.InputProcessorService;
import com.jinx.otp.services.PlayerMovementService;

public class GameScreen implements Screen {

    private LittlePlatformerGame game;

    private Camera camera;

    private final PlayerMovementService playerMovementService;

    private GameMap map;
    private MapLoader mapLoader;
    private Player player;

    private InputProcessorService inputProcessorService = InputProcessorService.getInputProcessorService();

    public GameScreen(LittlePlatformerGame game) {
        this.game = game;
        this.playerMovementService = PlayerMovementService.getPlayerMovementService();
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
        final MapModel mapModel = map.getModel();
        final PlayerModel playerModel = new PlayerModel(mapModel);
        player = new Player(playerModel);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        logic(delta);
        draw();
    }

    private void logic(float delta) {
        final PlayerModel playerModel = player.getModel();
        final MapModel mapModel = map.getModel();
        inputProcessorService.processPlayerMovement(delta, playerModel);
        playerMovementService.handleGravitation(playerModel, delta);
        playerMovementService.handleObstacleCollision(playerModel, mapModel);
        playerMovementService.clampToMapBorders(playerModel, mapModel);
        centerCameraOnPlayer();
    }

    private void centerCameraOnPlayer() {
        final PlayerModel playerModel = player.getModel();
        final MapModel mapModel = map.getModel();

        final float playerX = playerModel.getPosX();
        final float playerY = playerModel.getPosY();
        
        final float cameraX = playerX + (PLAYER_WIDTH / 2);
        final float minCameraX = camera.viewportWidth / 2;
        final float maxCameraX = mapModel.getWidth() - (camera.viewportWidth / 2);
        final float adjustedCameraX = MathUtils.clamp(cameraX, minCameraX, maxCameraX);

        final float cameraY = playerY + (PLAYER_HEIGHT / 2);
        final float minCameraY = camera.viewportHeight / 2;
        final float maxCameraY = mapModel.getHeight() - (camera.viewportHeight / 2);
        final float adjustedCameraY = MathUtils.clamp(cameraY, minCameraY, maxCameraY);

        final float newCameraZ = camera.position.z;
        camera.position.set(adjustedCameraX, adjustedCameraY, newCameraZ);
        camera.update();
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
