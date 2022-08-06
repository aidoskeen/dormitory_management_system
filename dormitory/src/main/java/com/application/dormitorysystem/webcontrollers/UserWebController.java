package com.application.dormitorysystem.webcontrollers;

import com.application.dormitorysystem.model.Dormitory;
import com.application.dormitorysystem.model.Student;
import com.application.dormitorysystem.model.User;
import com.application.dormitorysystem.hibernate.UserHibCtrl;
import com.application.dormitorysystem.model.Administrator;
import com.google.gson.Gson;
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
public class UserWebController {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("DormitoryManagement");
    UserHibCtrl userHibController = new UserHibCtrl(entityManagerFactory);

    @RequestMapping(value = "/user/allUsers", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getAllUsers() {
       List<User>userList=userHibController.getAllUsers();
        Gson gson = new Gson();
        Type userListType =new TypeToken<List<User>>(){}.getType();
       // return gson.toJson(userList.toString());
        return gson.toJson(userList,userListType);
    }

    @RequestMapping(value = "/user/updatePerson/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String updatePerson(@RequestBody String request, @PathVariable(name = "id") int id) {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(request, Properties.class);

        User user = userHibController.getUserById(id);

        user.setName(properties.getProperty("name"));
        user.setLogin(properties.getProperty("login"));
        //pabaigsim

        //Person person = new Person(properties.getProperty("login"), properties.getProperty("psw"), properties.getProperty("name"), properties.getProperty("surname"), properties.getProperty("email"));
        userHibController.editUser(user);
        return "Success";
    }
    @RequestMapping(value = "/user/userValidation", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String userValidation(@RequestBody String request) {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(request, Properties.class);
        return gson.toJson(userHibController.getUserByLoginData(properties.getProperty("login"),
                properties.getProperty("password")));
    }

    @RequestMapping(value = "/user/addStudent", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String addNewStudent(@RequestBody String request) {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(request, Properties.class);
        Student student = new Student(properties.getProperty("login"), properties.getProperty("psw"), properties.getProperty("name"), properties.getProperty("surname"), properties.getProperty("phone_num"),properties.getProperty("email"),properties.getProperty("faculty"));
        userHibController.createUser(student);
        return "Success";
    }
    @RequestMapping(value = "/user/addAdmin", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String addNewAdmin(@RequestBody String request) {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(request, Properties.class);
        List<Dormitory> dorms=new ArrayList<>();
        Administrator admin = new Administrator(properties.getProperty("login"), properties.getProperty("psw"), properties.getProperty("name"), properties.getProperty("surname"), properties.getProperty("phone_num"),properties.getProperty("email"),dorms);
        userHibController.createUser(admin);
        return "Success";
    }

    @RequestMapping(value = "/user/deleteUser/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String updatePerson(@PathVariable(name = "id") int id) {
        userHibController.removeUser(id);
        //Check if really deleted
        return "Success";
    }
}
