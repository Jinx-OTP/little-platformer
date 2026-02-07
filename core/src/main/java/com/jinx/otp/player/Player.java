package com.jinx.otp.player;

import static com.jinx.otp.constants.Constants.PLAYER_HEIGHT;
import static com.jinx.otp.constants.Constants.PLAYER_SPEED;
import static com.jinx.otp.constants.Constants.PLAYER_WIDTH;
import static com.jinx.otp.constants.Constants.PLAYER_IMAGE_FILE_NAME;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
        final float distance = PLAYER_SPEED;
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

        // todo check collision and clamp accordingly

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
