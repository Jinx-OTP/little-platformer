package com.jinx.otp.rooms;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.jinx.otp.exceptions.InvalidArgumentException;

public class RoomLoader {

    private final String MAP_FILE_NAME = "rooms/all.json";
    private final Map<Integer, RawRoom> roomLocations;

    public RoomLoader() {
        roomLocations = new HashMap<>();
        final Json json = new Json();
        final FileHandle content = Gdx.files.internal(MAP_FILE_NAME);
        json.fromJson(RawRooms.class, content)
            .getRooms()
            .stream()
            .forEach((rawRoom) -> {
                roomLocations.put(rawRoom.getId(), rawRoom);
            });
    }

    public RoomRepresentation load(int id) {
        final RawRoom rawData = roomLocations.get(id);
        if (null == rawData) {
            throw new InvalidArgumentException("Room with id " + id + " does not exist!");
        }
        final Json json = new Json();
        final String filePath = rawData.getFilePath();
        final FileHandle content = Gdx.files.internal(filePath);
        final RoomModel model = json.fromJson(RoomModel.class, content);
        return new RoomRepresentation(model);
    }
}
