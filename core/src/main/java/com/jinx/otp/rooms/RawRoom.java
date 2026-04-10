package com.jinx.otp.rooms;

/**
 * Containing the room data found in `rooms.json` file.
 * Used as a handle to rooms detailed config file.
 */
public class RawRoom {

    private int id;
    
    private String name;
    
    private String filePath;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    
}
