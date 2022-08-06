package com.application.dormitorysystem.Controllers;

import com.application.dormitorysystem.model.Comments;
import com.application.dormitorysystem.Main;
import com.application.dormitorysystem.hibernate.CommentsHibCtrl;
import com.application.dormitorysystem.hibernate.DormHibCtrl;
import com.application.dormitorysystem.model.Dormitory;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class NewComment {

    public TextField commentText;
    private int DormId;
    private int MainCommentId;
    private int userId;
    private int currentCommentId;
    EntityManagerFactory emf= Persistence.createEntityManagerFactory("DormitoryManagement");
    CommentsHibCtrl commHibCtrl = new CommentsHibCtrl(emf);
    DormHibCtrl dormHibCtrl=new DormHibCtrl(emf);
    public void setData( int parentId, int projectId, int userId, int currentCommID) {
        this.DormId=projectId;
        this.MainCommentId=parentId;
        this.userId=userId;
        this.currentCommentId=currentCommID;
    }
    public void SaveChanges(ActionEvent event) throws IOException {
        if (MainCommentId != 0) {
            Comments parentTask = commHibCtrl.getTaskById(MainCommentId);
            Comments task = new Comments(LocalDate.now(),commentText.getText(),parentTask );
            parentTask.getReply().add(task);
            commHibCtrl.edit(parentTask);
        } else if(DormId != 0){
            Dormitory project = dormHibCtrl.getDormByNum(DormId);
            Comments task = new Comments(LocalDate.now(),commentText.getText(), project);
            project.getDormitoryComments().add(task);
            dormHibCtrl.editDorm(project);
        } else {
            Comments task = commHibCtrl.getTaskById(currentCommentId);
            task.setComment(commentText.getText());
            //ir t.t.
            commHibCtrl.edit(task);
        }
        returnToMain();
    }

    public void returnToMain() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("comments-form.fxml"));
        Parent parent1=fxmlLoader.load();
        CommentsCtrl commentsCtrl = fxmlLoader.getController();
        commentsCtrl.setUser_id(this.userId);
        Stage stage=(Stage)commentText.getScene().getWindow();
        Scene scene = new Scene(parent1);
        stage.setScene(scene);
        stage.show();
    }


}
