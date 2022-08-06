package com.application.dormitorysystem.webcontrollers;
import com.application.dormitorysystem.model.Dormitory;
import com.application.dormitorysystem.model.Room;
import com.application.dormitorysystem.Serializers.DormGSONSerializer;
import com.application.dormitorysystem.Serializers.DormListGSONSerializer;
import com.application.dormitorysystem.hibernate.DormHibCtrl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
@Controller
public class DormitoryWebController {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("DormitoryManagement");
    DormHibCtrl dormHibController = new DormHibCtrl(entityManagerFactory);

    @RequestMapping(value = "/dormitory/allDorms", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getAllDorms() {
        List<Dormitory> dormList =dormHibController.getAllDorms();

        GsonBuilder gson = new GsonBuilder();
        Type dormType = new TypeToken<List<Dormitory>>() {
        }.getType();
        gson.registerTypeAdapter(Dormitory.class, new DormGSONSerializer())
                .registerTypeAdapter(dormType, new DormListGSONSerializer());
        Gson parser = gson.create();
        return parser.toJson(dormList);
    }

    @RequestMapping(value = "/dormitory/updateDorm/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String updateDorm(@RequestBody String request, @PathVariable(name = "id") int id) {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(request, Properties.class);

        Dormitory dorm = dormHibController.getDormByNum(id);

        dorm.setAddress(properties.getProperty("address"));
        //pabaigsim

        //Person person = new Person(properties.getProperty("login"), properties.getProperty("psw"), properties.getProperty("name"), properties.getProperty("surname"), properties.getProperty("email"));
        dormHibController.editDorm(dorm);
        return "Success";
    }

    @RequestMapping(value = "/dormitory/addDorm", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String addNewDorm(@RequestBody String request) {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(request, Properties.class);
        List<Room>rooms=new ArrayList<>();
        Dormitory dorm = new Dormitory(properties.getProperty("address"),rooms);
        dormHibController.addnewDorm(dorm);
        return "Success";
    }

    @RequestMapping(value = "/dormitory/deleteDorm/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String updateDorm(@PathVariable(name = "id") int id) {
        dormHibController.removeDorm(id);
        //Check if really deleted
        return "Success";
    }


}
