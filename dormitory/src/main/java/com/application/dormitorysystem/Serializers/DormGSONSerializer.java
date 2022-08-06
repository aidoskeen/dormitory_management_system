package com.application.dormitorysystem.Serializers;



    import com.application.dormitorysystem.model.Dormitory;
    import com.google.gson.JsonElement;
    import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;


import java.lang.reflect.Type;
public class DormGSONSerializer implements JsonSerializer<Dormitory> {
        @Override
        public JsonElement serialize(Dormitory dorm, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject dormJson = new JsonObject();
            dormJson.addProperty("dorm_num", dorm.getDorm_num());
            dormJson.addProperty("address", dorm.getAddress());
            dormJson.addProperty("rooms_count", dorm.getRooms_count());


            //dar json array, kur yra Task serializer, kad nebutu amzino duomenu buildinimo
            return dormJson;
        }
    }

