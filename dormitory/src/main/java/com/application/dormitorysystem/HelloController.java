package com.application.dormitorysystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    private Stage stage1;
    private Scene scene1;
    private Parent parent1;

    public void openNewPage(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("SceneBuild1.fxml"));
        Parent parent1=fxmlLoader.load();
    }
}