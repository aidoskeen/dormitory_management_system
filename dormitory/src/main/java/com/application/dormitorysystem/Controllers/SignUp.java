package com.application.dormitorysystem.Controllers;

import com.application.dormitorysystem.Main;
import com.application.dormitorysystem.hibernate.UserHibCtrl;
import com.application.dormitorysystem.model.Dormitory;
import com.application.dormitorysystem.model.Student;
import com.application.dormitorysystem.model.User;
import com.application.dormitorysystem.model.Administrator;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SignUp implements Initializable {
    public TextField idText;
    public TextField loginText;
    
    public PasswordField passText;
    public TextField nameText;
    public TextField surnameText;
    public TextField facText;
    public Button signupButton;
    public Button goBackButton;
    public Label facultyLabel;
    public TextField phoneText;
    public TextField emailText;
    public RadioMenuItem adminChoice;
    public RadioMenuItem studentChoice;
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("DormitoryManagement");
    UserHibCtrl hibCtrl = new UserHibCtrl(emf);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameText.setVisible(false);
        surnameText.setVisible(false);
        facText.setVisible(false);
        facultyLabel.setVisible(false);

    }

    public void signUserUp(ActionEvent actionEvent) throws IOException{
            if(adminChoice.isSelected()) {
                User user = hibCtrl.getUserByLogin(loginText.getText());
                if (user != null) {
                    SignIN.openMessageBox("This user already exist!");
                } else {
                    List<Dormitory> dorms=new ArrayList<>();
                    Administrator admin = new Administrator(loginText.getText(), passText.getText(), nameText.getText(), surnameText.getText(), phoneText.getText(), emailText.getText(),dorms);
                    admin.setLevel_of_access("dormadmin");
                    hibCtrl.createUser(admin);
                    SignIN.openMessageBox("User was created successfully.");
                    Previous();
                }
            }
            else{
                User user = hibCtrl.getUserByLogin(loginText.getText());
                if (user != null) {
                    SignIN.openMessageBox("This user already exist!");
                } else {
                    Student student = new Student(loginText.getText(), passText.getText(), nameText.getText(), surnameText.getText(), phoneText.getText(), emailText.getText(), facText.getText());
                    student.setLevel_of_access("student");
                    hibCtrl.createUser(student);
                    SignIN.openMessageBox("User was created successfully.");
                    Previous();
                }
            }


    }


    private void Previous() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("SignIN.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        Stage stage = (Stage) loginText.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    public void goBack(ActionEvent actionEvent) throws IOException {
        Previous();
    }


    public void showAdminFields(ActionEvent actionEvent) {
        nameText.setVisible(true);
        surnameText.setVisible(true);
        facText.setVisible(false);
        facultyLabel.setVisible(false);

    }

    public void showStudentFields(ActionEvent actionEvent) {
        nameText.setVisible(true);
        surnameText.setVisible(true);
        facText.setVisible(true);
        facultyLabel.setVisible(true);

    }
}

