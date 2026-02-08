package com.jinx.otp.player;

import static com.jinx.otp.constants.Constants.PLAYER_IMAGE_FILE_NAME;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {

    private Texture playerTexture;
    private Sprite playerSprite;
    private PlayerModel model;

    public Player(PlayerModel model) {
        this.model = model;
        setupSprite();
    }

    private void setupSprite() {
        final FileHandle imageFile = Gdx.files.internal(PLAYER_IMAGE_FILE_NAME);
        playerTexture = new Texture(imageFile);
        playerSprite = new Sprite(playerTexture);
        final float width = model.getWidth();
        final float height = model.getHeight();
        playerSprite.setSize(width, height);
        final float startX = model.getPosX();
        final float startY = model.getPosY();
        playerSprite.setPosition(startX, startY);
    }

    public PlayerModel getModel() {
        return model;
    }

    public void draw(SpriteBatch batch) {
        updatePlayerSprite();
        playerSprite.draw(batch);
    }

    private void updatePlayerSprite() {
        final float posX = model.getPosX();
        final float posY = model.getPosY();
        playerSprite.setPosition(posX, posY);
        final float width = model.getWidth();
        final float height = model.getHeight();
        playerSprite.setSize(width, height);
    }

    public void dispose() {
        playerTexture.dispose();
    }

}
