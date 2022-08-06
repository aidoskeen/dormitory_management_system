package com.application.dormitorysystem.Controllers;

import com.application.dormitorysystem.Main;
import com.application.dormitorysystem.model.Comments;
import com.application.dormitorysystem.model.Dormitory;
import com.application.dormitorysystem.hibernate.CommentsHibCtrl;
import com.application.dormitorysystem.hibernate.DormHibCtrl;
import com.application.dormitorysystem.hibernate.UserHibCtrl;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.List;

public class CommentsCtrl {
    public TreeView DormComments;
    public ListView myDorms;
    public MenuItem addItem;
    public MenuItem editComm;
    public MenuItem deleteComm;
    private int user_id;
    EntityManagerFactory emf= Persistence.createEntityManagerFactory("DormitoryManagement");
    CommentsHibCtrl commHibCtrl = new CommentsHibCtrl(emf);
    DormHibCtrl dormHibCtrl=new DormHibCtrl(emf);
    UserHibCtrl userHibCtrl=new UserHibCtrl(emf);
    public void setUser_id(int user_id) {
        this.user_id = user_id;
        if(userHibCtrl.getUserById(user_id).getLevel_of_access().equals("student")){
            editComm.setVisible(false);
            deleteComm.setVisible(false);
        }
        fillTables();
    }

    private void fillTables() {
        myDorms.getItems().clear();
        List<Dormitory> dorms = dormHibCtrl.getAllDorms();
        for (Dormitory p : dorms) {
            myDorms.getItems().add(p.getDorm_num() + ":" + p.getAddress());
        }
    }
    public void loadComments() {
        Dormitory selectedProject = dormHibCtrl.getDormByNum(
                Integer.parseInt(myDorms.getSelectionModel().getSelectedItem().toString().split(":")[0])
        );
        DormComments.setRoot(new TreeItem<String>("Dorm comments:"));
        DormComments.setShowRoot(false);
        DormComments.getRoot().setExpanded(true);
        selectedProject.getDormitoryComments().forEach(comment -> addTreeItem(comment, DormComments.getRoot()));
    }

    private void addTreeItem(Comments comment, TreeItem parentFolder) {
        TreeItem<Comments> treeItem = new TreeItem<>(comment);
        parentFolder.getChildren().add(treeItem);
        comment.getReply().forEach(sub -> addTreeItem(sub, treeItem));
    }

    public void addComment() throws IOException {
        TreeItem<Comments> taskTreeItem = (TreeItem<Comments>) DormComments.getSelectionModel().getSelectedItem();
        if (taskTreeItem == null) {
            loadTaskForm( 0, Integer.parseInt(myDorms.getSelectionModel().getSelectedItem().toString().split(":")[0]), 0);
        } else {
            loadTaskForm( taskTreeItem.getValue().getId(), 0, 0);
        }
    }

    public void editComment() throws IOException {
        TreeItem<Comments> taskTreeItem = (TreeItem<Comments>) DormComments.getSelectionModel().getSelectedItem();
        loadTaskForm( 0, 0, taskTreeItem.getValue().getId());
    }

    public void deleteComment() {
        TreeItem<Comments> taskTreeItem = (TreeItem<Comments>) DormComments.getSelectionModel().getSelectedItem();
        commHibCtrl.remove(taskTreeItem.getValue().getId());
    }

    private void loadTaskForm(int taskId, int projectId, int currentTask) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("new-comment-form.fxml"));
        Parent root = fxmlLoader.load();
        NewComment taskForm = fxmlLoader.getController();
        taskForm.setData(taskId, projectId, user_id, currentTask);
        Scene scene = new Scene(root);
        Stage stage = (Stage) myDorms.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void GoBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainPage.fxml"));
        Parent parent1=fxmlLoader.load();
        MainPageCtrl mainPageCtrl = fxmlLoader.getController();
        mainPageCtrl.setUser_id(user_id);
        Stage stage=(Stage)myDorms.getScene().getWindow();
        Scene scene = new Scene(parent1);
        stage.setScene(scene);
        stage.show();
    }


}
