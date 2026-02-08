package com.jinx.otp.player;

import static com.jinx.otp.constants.Constants.PLAYER_HEIGHT;
import static com.jinx.otp.constants.Constants.PLAYER_SPEED;
import static com.jinx.otp.constants.Constants.PLAYER_WIDTH;

import java.util.List;

import static com.jinx.otp.constants.Constants.PLAYER_IMAGE_FILE_NAME;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.jinx.otp.constants.Direction;
import com.jinx.otp.map.GameMap;

public class Player {


    private Texture playerTexture;
    private Sprite playerSprite;

    private GameMap map;

    public Player(GameMap map) {
        this.map = map;
        setupSprite();
    }

    private void setupSprite() {
        final FileHandle imageFile = Gdx.files.internal(PLAYER_IMAGE_FILE_NAME);
        playerTexture = new Texture(imageFile);
        playerSprite = new Sprite(playerTexture);
        playerSprite.setSize(PLAYER_WIDTH, PLAYER_HEIGHT);
        final float startX = map.getPlayerStartX();
        final float startY = map.getPlayerStartY();
        playerSprite.setPosition(startX, startY);
    }

    public void move(float delta, Direction direction) {
        final float distance = PLAYER_SPEED * delta;
        switch (direction) {
            case UP:
                playerSprite.translateY(distance);
                break;
            case DOWN:
                playerSprite.translateY(-distance);
                break;
            case LEFT:
                playerSprite.translateX(-distance);
                break;
            case RIGHT:
                playerSprite.translateX(distance);
                break;
        }
        final Rectangle playerBounds = playerSprite.getBoundingRectangle();
        final List<Sprite> overlapingObstacles = map.getOverlapingObstacles(playerBounds);
        if (0 < overlapingObstacles.size()) {
            for (Sprite obstacle : overlapingObstacles) {
                switch (direction) {
                    case LEFT:
                        clampPositionToRightObstacleBorder(obstacle);
                        break;
                    case RIGHT:
                        clampPositionToLeftObstacleBorder(obstacle);
                        break;
                    case UP:
                        clampPostionToBottomObstacleBorder(obstacle);
                        break;
                    case DOWN:
                        clampPositionToTopObstacleBorder(obstacle);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void clampPositionToRightObstacleBorder(Sprite obstacle) {
        final float rightObstacleBorder = obstacle.getWidth() + obstacle.getX();
        final float rightMapBorder = map.getWidth();
        final float playerAdjustedRightMapBorder = rightMapBorder - PLAYER_WIDTH;
        final float playerPosX = playerSprite.getX();
        final float adjustedPosX = MathUtils.clamp(playerPosX, rightObstacleBorder, playerAdjustedRightMapBorder);
        playerSprite.setX(adjustedPosX);
    }

    private void clampPositionToLeftObstacleBorder(Sprite obstacle) {
        final float leftObstacleBorder = obstacle.getX();
        final float playerWidthAdjustedBorder = leftObstacleBorder - PLAYER_WIDTH;
        final float leftMapBorder = 0f;
        final float playerPosX = playerSprite.getX();
        final float adjustedPosX = MathUtils.clamp(playerPosX, leftMapBorder, playerWidthAdjustedBorder);
        playerSprite.setX(adjustedPosX);
    }

    private void clampPostionToBottomObstacleBorder(Sprite obstacle) {
        final float bottomObstacleBorder = obstacle.getY();
        final float playerHeightAdjustedBOrder = bottomObstacleBorder - PLAYER_HEIGHT;
        final float bottomMapBorder = 0f;
        final float playerPosY = playerSprite.getY();
        final float adjustedPosY = MathUtils.clamp(playerPosY, bottomMapBorder, playerHeightAdjustedBOrder);
        playerSprite.setY(adjustedPosY);
    }

    private void clampPositionToTopObstacleBorder(Sprite obstacle) {
        final float topObstacleBorder = obstacle.getY() + obstacle.getWidth();
        final float playerAdjustedTopMapBorder = map.getHeight() - PLAYER_HEIGHT;
        final float playerPosY = playerSprite.getY();
        final float adjustedPosY = MathUtils.clamp(playerPosY, topObstacleBorder, playerAdjustedTopMapBorder);
        playerSprite.setY(adjustedPosY);
    }

    public float getPosX() {
        return playerSprite.getX();
    }

    public float getPosY() {
        return playerSprite.getY();
    }

    public void draw(SpriteBatch batch) {
        playerSprite.draw(batch);
    }

    public void dispose() {
        playerTexture.dispose();
    }

}
