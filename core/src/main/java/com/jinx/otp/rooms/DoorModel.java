package com.jinx.otp.rooms;

import com.badlogic.gdx.math.Rectangle;

public class DoorModel {

    private int posX;
    private int posY;

    private int width;
    private int height;

    private int roomId;

    private String imagePath;

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imageFile) {
        this.imagePath = imageFile;
    }
 
    public Rectangle getBoundingRectangle() {
        return new Rectangle(posX, posY, width, height);
    }

}
