package com.application.dormitorysystem.webcontrollers;

import com.application.dormitorysystem.model.Dormitory;
import com.application.dormitorysystem.model.Room;
import com.application.dormitorysystem.Serializers.RoomGSONSerializer;
import com.application.dormitorysystem.Serializers.RoomListGSON;
import com.application.dormitorysystem.hibernate.DormHibCtrl;
import com.application.dormitorysystem.hibernate.RoomHibCtrl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
@Controller
public class RoomWebController {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("DormitoryManagement");
    RoomHibCtrl roomHibController = new RoomHibCtrl(entityManagerFactory);
    DormHibCtrl dormHibController = new DormHibCtrl(entityManagerFactory);

    @RequestMapping(value = "/room/allRooms", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getAllRooms() {
        Gson gson = new Gson();
        return gson.toJson(roomHibController.getAllRooms().toString());
    }
    @RequestMapping(value = "/room/byDormitory/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getRoomsByDormitory(@PathVariable(name = "id") String id) {

        List<Room> roomlist =dormHibController.getDormByNum(Integer.parseInt(id)).getRooms();

        GsonBuilder gson = new GsonBuilder();
        Type roomType = new TypeToken<List<Room>>() {
        }.getType();
        gson.registerTypeAdapter(Room.class, new RoomGSONSerializer())
                .registerTypeAdapter(roomType, new RoomListGSON());
        Gson parser = gson.create();
        return parser.toJson(roomlist);
    }
    @RequestMapping(value = "/room/updateRoom/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String updateRoom(@RequestBody String request, @PathVariable(name = "id") int id) {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(request, Properties.class);

        Room room = roomHibController.getRoomByNum(id);

        room.setRoom_type(properties.getProperty("type"));
        room.setAvailable(Boolean.parseBoolean(properties.getProperty("available")));
        //pabaigsim

        //Person person = new Person(properties.getProperty("login"), properties.getProperty("psw"), properties.getProperty("name"), properties.getProperty("surname"), properties.getProperty("email"));
        roomHibController.editRoom(room);
        return "Success";
    }

    @RequestMapping(value = "/room/addRoom", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String addNewRoom(@RequestBody String request) {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(request, Properties.class);
        Dormitory dorm=dormHibController.getDormByNum(Integer.parseInt(properties.getProperty("DormitoryNumber")));
        Room room=new Room(properties.getProperty("roomType"));
        roomHibController.addRoom(room);
        dorm.getRooms().add(room);
        dormHibController.editDorm(dorm);
        return "Success";
    }

    @RequestMapping(value = "/room/deleteRoom/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String updateDorm(@PathVariable(name = "id") int id) {
        roomHibController.removeRoom(id);
        //Check if really deleted
        return "Success";
    }

}
