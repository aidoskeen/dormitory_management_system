package com.application.dormitorysystem.Controllers;

import com.application.dormitorysystem.Main;
import com.application.dormitorysystem.hibernate.UserHibCtrl;
import com.application.dormitorysystem.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignIN {
    @FXML
    public Button signINBut;
    @FXML
    public Button signUPBut;
    @FXML
    public TextField loginID;
    @FXML
    public PasswordField passID;
    private Connection connection;
    private PreparedStatement preparedStatement;

    //When You have persistence, then create EntityManagerFactory that will take info from persistence.xml. Basically tell to what db You are connecting and where tables should be created

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("DormitoryManagement");

    //When You have this connection, You can work with entity manager --> persist objects to db --> save object, but the data is automatically inserted in db
    //For this I have created hibernate controller class for users, see UserHibController
    UserHibCtrl userHibControl = new UserHibCtrl(entityManagerFactory);

    public void SignUserin(ActionEvent actionEvent) throws SQLException, IOException {


        User user = userHibControl.getUserByLoginData(loginID.getText(), passID.getText());

        if (user != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainPage.fxml"));
            Parent root = fxmlLoader.load();

            MainPageCtrl mainPageCtrl = fxmlLoader.getController();
            mainPageCtrl.setUser_id(user.getUserId());
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Main page");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } else {
            openMessageBox("Incorrect input");
        }
    }
    //Do not forget to close open connections and statements
    //DBclass.disconnectDb(connection,preparedStatement);


    public static void openMessageBox(String mess) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Message text:");
        alert.setContentText(mess);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

    public void nextForm(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("SignUp.fxml"));
        Parent parent1 = fxmlLoader.load();
        Stage stage = (Stage) loginID.getScene().getWindow();
        Scene scene = new Scene(parent1);
        stage.setScene(scene);
        stage.show();
    }
}
