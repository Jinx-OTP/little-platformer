package com.jinx.otp.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

public class MapLoader {

    private final String MAP_FILE_NAME = "maps/map1.json";

    public Map load() {

        Json json = new Json();

        FileHandle content = Gdx.files.internal(MAP_FILE_NAME);

        MapModel model = json.fromJson(MapModel.class, content);

        System.out.println(model);

        return null;
    }
}
