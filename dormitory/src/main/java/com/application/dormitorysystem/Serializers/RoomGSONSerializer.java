package com.application.dormitorysystem.Serializers;

import com.application.dormitorysystem.model.Room;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.LocalDate;

public class RoomGSONSerializer implements JsonSerializer<Room> {
    int room_num;
    String room_type;
    boolean available;
    LocalDate issue_date;

    @Override
    public JsonElement serialize(Room room, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject roomJson = new JsonObject();
        roomJson.addProperty("room_num", room.getRoom_num());
        roomJson.addProperty("room_type", room.getRoom_type());
        roomJson.addProperty("available", Boolean.toString(room.isAvailable()));
        roomJson.addProperty("available", room.getIssue_date().toString());


        //dar json array, kur yra Task serializer, kad nebutu amzino duomenu buildinimo
        return roomJson;
    }
}
