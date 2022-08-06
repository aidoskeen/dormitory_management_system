package com.application.dormitorysystem.Serializers;

import com.application.dormitorysystem.model.Dormitory;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.List;

public class DormListGSONSerializer implements JsonSerializer<List<Dormitory>> {
    @Override
    public JsonElement serialize(List<Dormitory> dormitories, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray jsonArray = new JsonArray();
        Gson parser = new GsonBuilder().create();

        for (Dormitory d : dormitories) {
            jsonArray.add(parser.toJson(d));
        }
        System.out.println(jsonArray);
        return jsonArray;
    }
}
