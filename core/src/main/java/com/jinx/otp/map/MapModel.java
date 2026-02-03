package com.jinx.otp.map;

import java.util.List;

public class MapModel {

    private int id;
    private String name;
    private String backgroundImageName;
    private List<Obstacle> obstacles;
    private float width;
    private float height;

    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public void setObstacles(List<Obstacle> obstacles) {
        this.obstacles = obstacles;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBackgroundImageName() {
        return backgroundImageName;
    }

    public void setBackgroundImageName(String backgroundImageName) {
        this.backgroundImageName = backgroundImageName;
    }

    @Override
    public String toString() {
        return "MapModel [id=" + id + ", name=" + name + ", obstacles=" + obstacles + ", width=" + width + ", height="
                + height + "]";
    }    
}
