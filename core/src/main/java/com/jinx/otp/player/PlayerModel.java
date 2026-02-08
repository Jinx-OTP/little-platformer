package com.jinx.otp.player;

import static com.jinx.otp.constants.Constants.PLAYER_HEIGHT;
import static com.jinx.otp.constants.Constants.PLAYER_WIDTH;

import com.badlogic.gdx.math.Rectangle;
import com.jinx.otp.map.MapModel;
import com.jinx.otp.map.Obstacle;

public class PlayerModel {

    private float posX;
    private float posY;
    private float verticalVelocity = 0f;
    private boolean isMidAir = true;
    private Obstacle currentPlatform;

    public PlayerModel() {
    }

    public PlayerModel(MapModel map) {
        posX = map.getPlayerStartX();
        posY = map.getPlayerStartY();
    }
    
    public float getPosX() {
        return posX;
    }
    
    public void setPosX(float posX) {
        this.posX = posX;
    }

    public void translateX(float amount) {
        this.posX += amount;
    }
    
    public float getPosY() {
        return posY;
    }
    
    public void setPosY(float posY) {
        this.posY = posY;
    }

    public void translateY(float amount) {
        this.posY += amount;
    }
    
    public float getWidth() {
        return PLAYER_WIDTH;
    }

    public float getHeight() {
        return PLAYER_HEIGHT;
    }

    public float getVerticalVelocity() {
        return verticalVelocity;
    }

    public void increaseVerticalVelocity(float amount) {
        verticalVelocity += amount;
    }

    public void decreaseVerticalVelocity(float amount) {
        verticalVelocity -= amount;
    }

    public Rectangle getBoundingRectangle() {
        return new Rectangle(posX, posY, PLAYER_WIDTH, PLAYER_HEIGHT);
    }

    public boolean isMidAir() {
        return isMidAir;
    }

    public void setMidAir(boolean isMidAir) {
        this.isMidAir = isMidAir;
    }

    @Override
    public String toString() {
        return "PlayerModel [posX=" + posX + ", posY=" + posY + ", verticalVelocity=" + verticalVelocity + "]";
    }

    public void setVerticalVelocity(float verticalVelocity) {
        this.verticalVelocity = verticalVelocity;
    }

    public Obstacle getCurrentPlatform() {
        return currentPlatform;
    }

    public void setCurrentPlatform(Obstacle currentPlatform) {
        this.currentPlatform = currentPlatform;
    }

}
