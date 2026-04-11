package com.jinx.otp.rooms;

import static com.jinx.otp.constants.Constants.PLATFORM_TEXTURE_PATH;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.jinx.otp.exceptions.InvalidArgumentException;
import com.jinx.otp.exceptions.InvalidFilePathException;
import com.jinx.otp.rooms.Obstacle.ObstacleType;

public class RoomRepresentation {

    private RoomModel model;

    private Texture backgroundTexture;
    private Texture platformTexture;

    private Sprite backgroundSprite;
    private Array<Sprite> obstacleSprites;
    private Array<Sprite> doorSprites;


    public RoomRepresentation(RoomModel model) {
        loadDefaultTextures();
        build(model);
    }

    public float getPlayerStartX() {
        return model.getPlayerStartX();
    }

    public float getPlayerStartY() {
        return model.getPlayerStartY();
    }

    public RoomModel getModel() {
        return model;
    }

    private void loadDefaultTextures() {
        final FileHandle platformImageFile = Gdx.files.internal(PLATFORM_TEXTURE_PATH);
        platformTexture = new Texture(platformImageFile);
    }

    public void build(RoomModel newMap) {
        this.model = newMap;
        buildBackground();
        buildObstacles();
        buildDoors();
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

    private void buildDoors() {
        doorSprites = new Array<>();
        model.getDoors().stream().forEach(this::buildDoor); 
    }

    private void buildDoor(DoorModel door) {
        final String imageLocation = door.getImagePath();
        final Texture texture = new Texture(imageLocation);
        final Sprite doorSprite = new Sprite(texture);

        final float doorWidth = door.getWidth();
        final float doorHeight = door.getHeight();
        doorSprite.setSize(doorWidth, doorHeight);

        final float doorX = door.getPosX();
        final float doorY = door.getPosY();
        doorSprite.setPosition(doorX, doorY);

        doorSprites.add(doorSprite);
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
        obstacleSprites.forEach((sprite) -> sprite.draw(batch));
        doorSprites.forEach((sprite) -> sprite.draw(batch));
    }

    public void dispose() {
        backgroundTexture.dispose();
        platformTexture.dispose();
        doorSprites.forEach((sprite) -> sprite.getTexture().dispose());
    }

}
