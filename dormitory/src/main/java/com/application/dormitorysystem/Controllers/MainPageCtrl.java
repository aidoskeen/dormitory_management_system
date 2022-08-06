package com.application.dormitorysystem.Controllers;

import com.application.dormitorysystem.Main;
import com.application.dormitorysystem.hibernate.UserHibCtrl;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

public class MainPageCtrl  {
    public Button usersbut;
    public Button dormBut;
    public Button roomBut;
    public Label sysAdminLabel;
    public Button commentBut;
    private int user_id;

    public int getUser_id() {
        return user_id;
    }

    EntityManagerFactory emf= Persistence.createEntityManagerFactory("DormitoryManagement");
    UserHibCtrl userHibCtrl = new UserHibCtrl(emf);
    public void setUser_id(int user_id) {
        sysAdminLabel.setVisible(false);
        this.user_id = user_id;
        String access_level= userHibCtrl.getUserById(user_id).getLevel_of_access();
        if(access_level.equals("sysadmin")){
            SignIN.openMessageBox("User with SYSTEM administration rights entered");
        }
        else if(access_level.equals("dormadmin")){
            SignIN.openMessageBox("User with dormitory administration rights entered");
            usersbut.setVisible(false);
        }
        else{
            SignIN.openMessageBox("User with regular rights entered");
            usersbut.setVisible(false);
            dormBut.setVisible(false);
        }
        if(access_level.equals("sysadmin") ){
            sysAdminLabel.setVisible(true);
        }
    }

    public void goAllusers(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("AllUsersData.fxml"));
        Parent parent1=fxmlLoader.load();
        UsersController usersCtrl  = fxmlLoader.getController();
        usersCtrl.setUser_id(user_id);
        Stage stage=(Stage)usersbut.getScene().getWindow();
        Scene scene = new Scene(parent1);
        stage.setScene(scene);
        stage.show();
    }

    public void goRooms(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Rooms.fxml"));
        Parent parent1=fxmlLoader.load();
        RoomsCtrl roomsCtrl = fxmlLoader.getController();
        roomsCtrl.setUser_id(user_id);
        Stage stage=(Stage)roomBut.getScene().getWindow();
        Scene scene = new Scene(parent1);
        stage.setScene(scene);
        stage.show();
    }

    public void goDorms(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Dormitories.fxml"));
        Parent parent1=fxmlLoader.load();
        DormsCtrl dormCtrl = fxmlLoader.getController();
        dormCtrl.setUser_id(user_id);
        Stage stage=(Stage)roomBut.getScene().getWindow();
        Scene scene = new Scene(parent1);
        stage.setScene(scene);
        stage.show();
    }

    public void gotoComments(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("comments-form.fxml"));
        Parent parent1=fxmlLoader.load();
        CommentsCtrl commentsCtrl = fxmlLoader.getController();
        commentsCtrl.setUser_id(this.user_id);
        Stage stage=(Stage)roomBut.getScene().getWindow();
        Scene scene = new Scene(parent1);
        stage.setScene(scene);
        stage.show();
    }
}
