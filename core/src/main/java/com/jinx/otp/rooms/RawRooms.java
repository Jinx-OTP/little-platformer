package com.jinx.otp.rooms;

import java.util.List;

public class RawRooms {

    private String name;
    private List<RawRoom> rooms;

    public List<RawRoom> getRooms() {
        return rooms;
    }

    public void setRooms(List<RawRoom> rooms) {
        this.rooms = rooms;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
