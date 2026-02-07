package com.jinx.otp.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jinx.otp.map.GameMap;

public class Player {

    public static final float PLAYER_HEIGHT = 1f;
    public static final float PLAYER_WIDTH = 0.5f;

    private static final String playerImageFileName = "player.png";

    private Texture playerTexture;
    private Sprite playerSprite;

    private GameMap map;

    public Player(GameMap map, float startX, float startY) {
        final FileHandle imageFile = Gdx.files.internal(playerImageFileName);
        playerTexture = new Texture(imageFile);
        playerSprite = new Sprite(playerTexture);
        playerSprite.setSize(PLAYER_WIDTH, PLAYER_HEIGHT);
        playerSprite.setPosition(startX, startY);
        this.map = map;
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
