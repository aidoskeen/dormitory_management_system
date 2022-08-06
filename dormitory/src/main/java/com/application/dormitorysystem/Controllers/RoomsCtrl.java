package com.application.dormitorysystem.Controllers;

import com.application.dormitorysystem.Main;
import com.application.dormitorysystem.hibernate.DormHibCtrl;
import com.application.dormitorysystem.hibernate.RoomHibCtrl;
import com.application.dormitorysystem.hibernate.UserHibCtrl;
import com.application.dormitorysystem.model.Dormitory;
import com.application.dormitorysystem.model.Room;
import com.application.dormitorysystem.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class RoomsCtrl{
    public TableColumn<RoomsParameters,Integer> roomnumCol;
    public TableColumn <RoomsParameters, LocalDate>issuedateCol;
    public TableColumn<RoomsParameters,String> typeCol;
    public TableColumn <RoomsParameters,String>availableCol;
    public TextField dormNumText;
    public TextField roomissueText;
    public TableView roomsTab;
    public TableColumn<RoomsParameters,Void> emptyField;
    public TextField addDormIdText;
    public TextField addRoomtypeText;
    public ComboBox studentsCombo;
    public ComboBox dormsCombo;
    public Label labelRoom;
    public Text textRoomNum;
    public Button addButton;
    public Button issueButton;
    public Label studidLabel;
    public Text issueLabel;
    public Label addroomLabel;
    public Label roomLabel;
    private int user_id;

    public void setUser_id(int user_id) {

        this.user_id = user_id;
        fillTables();
    }

    private ObservableList<RoomsParameters> obsList = FXCollections.observableArrayList();
    EntityManagerFactory emf= Persistence.createEntityManagerFactory("DormitoryManagement");
    RoomHibCtrl rHibCtrl = new RoomHibCtrl(emf);
    DormHibCtrl dormHibCtrl=new DormHibCtrl(emf);
    UserHibCtrl userHibCtrl=new UserHibCtrl(emf);

    public void fillTables() {
        List<Student>students=userHibCtrl.getAllStudents();
        List<Dormitory>dorms=dormHibCtrl.getAllDorms();
        students.forEach(u -> studentsCombo.getItems().add(u.getUserId() + ":" + u.getName() + ":" + u.getSurname()));
        dorms.forEach(u -> dormsCombo.getItems().add(u.getDorm_num() + ":" + u.getAddress()));
        roomsTab.setEditable(true);
        roomnumCol.setCellValueFactory(new PropertyValueFactory<>("roomnum"));
        issuedateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        availableCol.setCellValueFactory(new PropertyValueFactory<>("available"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("rtype"));
        String type=userHibCtrl.getUserById(this.user_id).getLevel_of_access();
        if(type.equals("sysadmin") || type.equals("dormadmin")) {

            typeCol.setCellFactory(TextFieldTableCell.forTableColumn());
            typeCol.setOnEditCommit(
                    t -> {
                        t.getTableView().getItems().get(
                                t.getTablePosition().getRow()).setRtype(t.getNewValue());
                        Room room = rHibCtrl.getRoomByNum(t.getTableView().getItems().get(
                                t.getTablePosition().getRow()).getRoomnum());
                        room.setRoom_type(t.getNewValue());
                        rHibCtrl.editRoom(room);
                    }
            );

            Callback<TableColumn<RoomsParameters, Void>, TableCell<RoomsParameters, Void>> cellFactory = new Callback<TableColumn<RoomsParameters, Void>, TableCell<RoomsParameters, Void>>() {
                @Override
                public TableCell<RoomsParameters, Void> call(final TableColumn<RoomsParameters, Void> param) {
                    final TableCell<RoomsParameters, Void> cell = new TableCell<RoomsParameters, Void>() {

                        private final Button button = new Button("Delete");

                        {
                            button.setOnAction((ActionEvent event) -> {
                                RoomsParameters data = getTableView().getItems().get(getIndex());
                                rHibCtrl.removeRoom(data.getRoomnum());
                                try {
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
        }
        else{
            roomissueText.setVisible(false);
            addRoomtypeText.setVisible(false);
            studentsCombo.setVisible(false);
            dormsCombo.setVisible(false);
            textRoomNum.setVisible(false);
            issueButton.setVisible(false);
            addButton.setVisible(false);
            issueLabel.setVisible(false);
            addroomLabel.setVisible(false);
            roomLabel.setVisible(false);
           studidLabel.setVisible(false);
            labelRoom.setText("Your room is");
            Student student=userHibCtrl.getStudentById(this.user_id);
            if(student.getIssued_room()!=null )
            textRoomNum.setText(student.getIssued_room().toString());
            else  textRoomNum.setText("not issued to you yet");
        }
        try {
            loadData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void loadData() throws SQLException {
        if(dormNumText.getText()!="") {
            Dormitory dorm = dormHibCtrl.getDormByNum(Integer.parseInt(dormNumText.getText()));
            roomsTab.setEditable(true);
            roomsTab.getItems().clear();
            List<Room> listOfRooms = dorm.getRooms();

            for (Room r : listOfRooms) {
                RoomsParameters roomParameters = new RoomsParameters();
                roomParameters.setRoomnum(r.getRoom_num());
                roomParameters.setRtype(r.getRoom_type());
                roomParameters.setAvailable(Boolean.toString(r.isAvailable()));
                roomParameters.setDate(r.getIssue_date());
                obsList.add(roomParameters);
            }
            roomsTab.setItems(obsList);
        }
    }
    public void seeRooms(ActionEvent actionEvent) throws SQLException {
        int dormitory_number=Integer.parseInt(dormNumText.getText());
        loadData();
    }
    public void issueRoom(ActionEvent actionEvent) throws SQLException {
       Student user = userHibCtrl.getStudentById(Integer.parseInt(studentsCombo.getValue().toString().split(":")[0]));
       Room room = rHibCtrl.getRoomByNum(Integer.parseInt(roomissueText.getText()));
           if(user!=null) {
               user.setIssued_room(room);
               room.setAvailable(false);
               rHibCtrl.editRoom(room);
               userHibCtrl.editUser(user);
               SignIN.openMessageBox("ISSUED!");
           }
           else SignIN.openMessageBox("No such student!");

        }



    public void goback(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainPage.fxml"));
        Parent parent1=fxmlLoader.load();
        MainPageCtrl mainPageCtrl = fxmlLoader.getController();
        mainPageCtrl.setUser_id(this.user_id);
        Stage stage=(Stage)studentsCombo.getScene().getWindow();
        Scene scene = new Scene(parent1);
        stage.setScene(scene);
        stage.show();
    }


    public void addRoom(ActionEvent actionEvent) {
        Dormitory dorm=dormHibCtrl.getDormByNum(Integer.parseInt(dormsCombo.getValue().toString().split(":")[0]));
        Room room=new Room(addRoomtypeText.getText(),true,LocalDate.now());
        room.setDormitory(dorm);
        dorm.getRooms().add(room);
        rHibCtrl.addRoom(room);
        dormHibCtrl.editDorm(dorm);
        rHibCtrl.editRoom(room);
        SignIN.openMessageBox("Success!");
    }
}
