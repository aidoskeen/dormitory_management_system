package com.application.dormitorysystem.Serializers;

import com.application.dormitorysystem.model.Room;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.List;

public class RoomListGSON implements JsonSerializer<List<Room>> {

    @Override
    public JsonElement serialize(List<Room> rooms, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray jsonArray = new JsonArray();
        Gson parser = new GsonBuilder().create();

        for (Room r : rooms) {
            jsonArray.add(parser.toJson(r));
        }
        System.out.println(jsonArray);
        return jsonArray;
    }
}
