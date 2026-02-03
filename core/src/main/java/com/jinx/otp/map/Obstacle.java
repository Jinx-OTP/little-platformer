package com.jinx.otp.map;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class Obstacle implements Json.Serializable {

    private ObstacleType type;
    private float posX;
    private float posY;
    private float width;
    private float height;

    public ObstacleType getType() {
        return type;
    }

    public void setType(ObstacleType type) {
        this.type = type;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
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

    enum ObstacleType {
        PLATFORM("platform");

        private String name;

        ObstacleType(String name) {
            this.name = name;
        }

        public String getValue() {
            return name;
        }

        public static ObstacleType create(String typeName) {
            for (ObstacleType type : values()) {
                if (type.name.equals(typeName)) {
                    return type;
                }
            }
            return null;
        }
    }

    @Override
    public void write(Json json) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'write'");
    }

    @Override
    public void read(Json json, JsonValue jsonData) {

        for (JsonValue entry = jsonData.child; null != entry; entry = entry.next) {
            switch (entry.name) {
                case "type":
                    type = ObstacleType.create(entry.asString());
                    break;
                case "posX":
                    posX = entry.asFloat();
                    break;
                case "posY":
                    posY = entry.asFloat();
                    break;
                case "width":
                    width = entry.asFloat();
                    break;
                case "height":
                    height = entry.asFloat();
                    break;
                default:
                    System.out.println(
                        "Unknown field in deserialization of an Obstacle object: " + entry.name);
                    break;
            }
        }
    }

    @Override
    public String toString() {
        return "Obstacle [type=" + type + ", posX=" + posX + ", posY=" + posY + ", width=" + width + ", height="
                + height + "]";
    }

    

}
