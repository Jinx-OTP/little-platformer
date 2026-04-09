package com.jinx.otp.rooms;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

public class RoomLoader {

    private final String MAP_FILE_NAME = "rooms/all.json";

    public RoomLoader() {
        
    }

    public RoomRepresentation load(int id) {

        Json json = new Json();

        FileHandle content = Gdx.files.internal(MAP_FILE_NAME);
        RoomModel model = json.fromJson(RoomModel.class, content);

        RoomRepresentation map = new RoomRepresentation(model);



        return map;
    }
}
