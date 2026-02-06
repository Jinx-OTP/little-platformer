package com.jinx.otp.map;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.jinx.otp.exceptions.InvalidArgumentException;
import com.jinx.otp.exceptions.InvalidFilePathException;
import com.jinx.otp.map.Obstacle.ObstacleType;

public class GameMap {

    private final String platformTexturePath = "platform.png"; // TODO extract to config file

    private MapModel model;

    private Texture backgroundTexture;
    private Texture platformTexture;

    private Sprite backgroundSprite;
    private Array<Sprite> obstacleSprites;


    public GameMap(MapModel model) {
        loadDefaultTextures();
        build(model);
    }

    public float getPlayerStartX() {
        return model.getPlayerStartX();
    }

    public float getPlayerStartY() {
        return model.getPlayerStartY();
    }

    private void loadDefaultTextures() {
        final FileHandle platformImageFile = Gdx.files.internal(platformTexturePath);
        platformTexture = new Texture(platformImageFile);
    }

    public void build(MapModel newMap) {
        this.model = newMap;
        buildBackground();
        buildObstacles();
    }

    private void buildBackground() {
        FileHandle backgroundImageFile = openBackgroundImageFile();
        backgroundTexture = new Texture(backgroundImageFile);
        backgroundSprite = new Sprite(backgroundTexture);
        final float posX = 0f;
        final float posY = 0f;
        backgroundSprite.setPosition(posX, posY);
        final float width = model.getWidth();
        final float height = model.getHeight();
        backgroundSprite.setSize(width, height);
    }

    private FileHandle openBackgroundImageFile() {
        final String backgroundImageFilePath = model.getBackgroundImageName();
        final FileHandle backgroundImageFile = Gdx.files.internal(backgroundImageFilePath);
        if (!backgroundImageFile.exists()) {
            final int mapId = model.getId();
            final String fileNotFoundMessage = "The background image file with the internal path: " + 
                backgroundImageFilePath + " for map with id: " + mapId + 
                " Does not exist";
            throw new InvalidFilePathException(fileNotFoundMessage);
        }
        return backgroundImageFile;
    }

    private void buildObstacles() {
        obstacleSprites = new Array<>();
        final List<Obstacle> obstacles = model.getObstacles();
        for (Obstacle obstacle : obstacles) {
            final Texture texture = getObstacleTexture(obstacle);
            final Sprite obstacleSprite = new Sprite(texture);
            final float posX = obstacle.getPosX();
            final float posY = obstacle.getPosY();
            obstacleSprite.setPosition(posX, posY);
            final float width = obstacle.getWidth();
            final float height = obstacle.getHeight();
            obstacleSprite.setSize(width, height);
            obstacleSprites.add(obstacleSprite);
        }
    }

    private Texture getObstacleTexture(Obstacle obstacle) {
        final ObstacleType type = obstacle.getType();
        switch (type) {
            case PLATFORM:
                return platformTexture;
            default:
                final String errorMessage = "Unexpected Obstacle Type: " + type.getValue();
                throw new InvalidArgumentException(errorMessage);
        }
    }

    public void draw(SpriteBatch batch) {
        backgroundSprite.draw(batch);
        for (Sprite obstacle : obstacleSprites) {
            obstacle.draw(batch);
        }
    }

    public void dispose() {
        backgroundTexture.dispose();
        platformTexture.dispose();
    }
}
