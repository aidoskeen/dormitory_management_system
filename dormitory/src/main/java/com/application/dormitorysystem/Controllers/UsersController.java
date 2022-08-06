package com.application.dormitorysystem.Controllers;

import com.application.dormitorysystem.Main;
import com.application.dormitorysystem.hibernate.UserHibCtrl;
import com.application.dormitorysystem.model.Administrator;
import com.application.dormitorysystem.model.Student;
import com.application.dormitorysystem.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class UsersController implements Initializable {
    public TableColumn <TableParameters,Integer> user_idCol;
    public TableColumn<TableParameters,String> loginCol;
    public TableColumn<TableParameters,String> phonenumCol;
    public TableColumn<TableParameters,String> emailCol;
    public TableColumn<TableParameters,String> nameCol;
    public TableColumn<TableParameters,String> facultyCol;
    public TableColumn<TableParameters,Integer> DormnumCol;
    public TableColumn<TableParameters,Void> emptyField;
    public TableColumn<TableParameters,String> surnameCol;
    public TableColumn<TableParameters,String> utCol;
    public TableColumn<TableParameters,Integer> roomCol;
    public int user_id;

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public TableView<TableParameters> allusers=new TableView<>();
    private final ObservableList<TableParameters> obsList = FXCollections.observableArrayList();
   // private Connection connection;
   // private Statement statement;
    EntityManagerFactory emf= Persistence.createEntityManagerFactory("DormitoryManagement");
    UserHibCtrl hibCtrl=new UserHibCtrl(emf);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        allusers.setEditable(true);
        user_idCol.setCellValueFactory(new PropertyValueFactory<>("userid"));
        DormnumCol.setCellValueFactory(new PropertyValueFactory<>("dorm_num"));
        roomCol.setCellValueFactory(new PropertyValueFactory<>("room_num"));
        utCol.setCellValueFactory(new PropertyValueFactory<>("usertype"));
        loginCol.setCellValueFactory(new PropertyValueFactory<>("login"));
        loginCol.setCellFactory(TextFieldTableCell.forTableColumn());
        loginCol.setOnEditCommit(
                t -> {
                    String newLogin =t.getNewValue();
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setLogin(newLogin);
                    //Update e record on change
                    User user =hibCtrl.getUserById( t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getUserid());
                    user.setLogin(newLogin);
                    hibCtrl.editUser(user);

                }
        );
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setOnEditCommit(
                t -> {
                    String newName =t.getNewValue();
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setName(newName);
                    //Update e record on change
                    User user =hibCtrl.getUserById( t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getUserid());
                    user.setName(newName);
                    hibCtrl.editUser(user);
                }
        );
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        surnameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        surnameCol.setOnEditCommit(
                t -> {
                    String surName = t.getNewValue();
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setSurname(surName);
                    //Update e record on change
                    User user = hibCtrl.getUserById(t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getUserid());
                    user.setSurname(surName);
                    hibCtrl.editUser(user);
                }
        );
        facultyCol.setCellValueFactory(new PropertyValueFactory<>("faculty"));
        facultyCol.setCellFactory(TextFieldTableCell.forTableColumn());
        facultyCol.setOnEditCommit(
                t -> {
                    String fac = t.getNewValue();
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setFaculty(fac);
                    //Update e record on change
                    Student stud = (Student)hibCtrl.getUserById(t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getUserid());
                    stud.setFaculty(fac);
                    hibCtrl.editUser(stud);
                }
        );
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        emailCol.setOnEditCommit(
                t -> {
                    String newEmail = t.getNewValue();
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setEmail(newEmail);
                    //Update e record on change
                    User user = hibCtrl.getUserById(t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getUserid());
                    user.setEmail(newEmail);
                    hibCtrl.editUser(user);
                }
        );

        phonenumCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        phonenumCol.setCellFactory(TextFieldTableCell.forTableColumn());
        phonenumCol.setOnEditCommit(
                t -> {
                    String newphone = t.getNewValue();
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setPhone(newphone);
                    //Update e record on change
                    User user = hibCtrl.getUserById(t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getUserid());
                    user.setPhone_num(newphone);
                    hibCtrl.editUser(user);
                }
        );


        Callback<TableColumn<TableParameters, Void>, TableCell<TableParameters, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<TableParameters, Void> call(final TableColumn<TableParameters, Void> param) {
                final TableCell<TableParameters, Void> cell = new TableCell<>() {

                    private final Button button = new Button("Delete");

                    {
                        button.setOnAction((ActionEvent event) -> {
                            TableParameters data = getTableView().getItems().get(getIndex());
                            hibCtrl.removeUser(data.getUserid());

                            try {
                                //Immediately after delete, update info in table
                                loadData();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(button);
                        }
                    }
                };
                return cell;
            }
        };

        emptyField.setCellFactory(cellFactory);

        try {
            loadData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void loadData() throws SQLException {
        allusers.setEditable(true);
        allusers.getItems().clear();
        List<User> usersList=hibCtrl.getAllUsers();

        for (User u:usersList) {
            TableParameters tableParameters = new TableParameters();
            tableParameters.setUserid(u.getUserId());
            tableParameters.setLogin(u.getLogin());
            tableParameters.setName(u.getName());
            tableParameters.setSurname(u.getSurname());
            tableParameters.setPhone(u.getPhone_num());
            tableParameters.setEmail(u.getEmail());
            tableParameters.setUsertype(u.getUserType());
            if(Objects.equals(u.getUserType(), "stud")) {
                Student stud=hibCtrl.getStudentById(u.getUserId());
                tableParameters.setFaculty(stud.getFaculty());
                if(stud.getIssued_room()!=null)
                tableParameters.setRoom_num(stud.getIssued_room().getRoom_num());
            }
            else if(Objects.equals(u.getUserType(), "admin")){
                Administrator admin = hibCtrl.getAdminById(u.getUserId());
                if(admin.getDorms().size()!=0)
                    tableParameters.setDorm_num(admin.getDorms().get(0).getDorm_num());

            }
            obsList.add(tableParameters);
        }

        allusers.setItems(obsList);

    }

    public void goprevious(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainPage.fxml"));
        Parent parent1=fxmlLoader.load();
        MainPageCtrl mainPageCtrl = fxmlLoader.getController();
        mainPageCtrl.setUser_id(user_id);
        Stage stage=(Stage)allusers.getScene().getWindow();
        Scene scene = new Scene(parent1);
        stage.setScene(scene);
        stage.show();
    }
}
