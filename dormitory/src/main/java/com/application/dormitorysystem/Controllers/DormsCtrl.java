package com.application.dormitorysystem.Controllers;

import com.application.dormitorysystem.Main;
import com.application.dormitorysystem.hibernate.RoomHibCtrl;
import com.application.dormitorysystem.hibernate.UserHibCtrl;
import com.application.dormitorysystem.model.Dormitory;
import com.application.dormitorysystem.model.Room;
import com.application.dormitorysystem.model.User;
import com.application.dormitorysystem.hibernate.DormHibCtrl;
import com.application.dormitorysystem.model.Administrator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DormsCtrl  {

    public ListView dormsList;
    public TextField dormText;
    public TableColumn<DormsParameters,Integer> dormCol;
    public TableColumn<DormsParameters,String> addrCol;
    public TableColumn <DormsParameters,Integer>rcountCol;
    public TableColumn<DormsParameters,Void> emptyCol;
    public Button goBack;
    public TableView dormTable;
    public TextField dormIdText;
    public TextField adminID_to_view;
    public TextField adminID_to_assign;
    public TextField addressText;
    public Button addButton;
    public Button enterButton;
    private int user_id;
    private ObservableList<DormsParameters> obsList = FXCollections.observableArrayList();
    EntityManagerFactory emf= Persistence.createEntityManagerFactory("DormitoryManagement");
    RoomHibCtrl rHibCtrl = new RoomHibCtrl(emf);
    DormHibCtrl dormHibCtrl=new DormHibCtrl(emf);
    UserHibCtrl userHibCtrl=new UserHibCtrl(emf);

    public void setUser_id(int user_id) {
        this.user_id = user_id;
        User u = userHibCtrl.getUserById(this.user_id);
        if(u.getLevel_of_access().equals("dormadmin")){
            adminID_to_assign.setDisable(true);
            enterButton.setDisable(true);
            addButton.setDisable(true);
            dormIdText.setDisable(true);
            addressText.setDisable(true);
        }
        populateTables();
    }


    public void populateTables(){
        dormTable.setEditable(true);
        dormCol.setCellValueFactory(new PropertyValueFactory<>("dormitorynum"));
        rcountCol.setCellValueFactory(new PropertyValueFactory<>("rcount"));
        addrCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        addrCol.setCellFactory(TextFieldTableCell.forTableColumn());
        addrCol.setOnEditCommit(
                t -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setAddress(t.getNewValue());
                    //Update e record on change
                   Dormitory d = dormHibCtrl.getDormByNum(t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getDormitorynum());
                   d.setAddress(t.getNewValue());
                   dormHibCtrl.editDorm(d);
                }
        );

        Callback<TableColumn<DormsParameters, Void>, TableCell<DormsParameters, Void>> cellFactory = new Callback<TableColumn<DormsParameters, Void>, TableCell<DormsParameters, Void>>() {
            @Override
            public TableCell<DormsParameters, Void> call(final TableColumn<DormsParameters, Void> param) {
                final TableCell<DormsParameters, Void> cell = new TableCell<DormsParameters, Void>() {

                    private final Button button = new Button("Delete");

                    {
                        button.setOnAction((ActionEvent event) -> {
                            DormsParameters data = getTableView().getItems().get(getIndex());
                            dormHibCtrl.removeDorm(data.getDormitorynum());
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

        emptyCol.setCellFactory(cellFactory);

        try {
            loadData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void loadData() throws SQLException {
        dormTable.setEditable(true);
        dormTable.getItems().clear();
        User u = userHibCtrl.getUserById(this.user_id);
        if(u.getLevel_of_access().equals("dormadmin")){
            for (Dormitory d : dormHibCtrl.getDormByAdminId(this.user_id)) {
                DormsParameters dormParameters = new DormsParameters();
                dormParameters.setDormitorynum(d.getDorm_num());
                dormParameters.setRcount(d.getRooms_count());
                dormParameters.setAddress(d.getAddress());
                obsList.add(dormParameters);
            }
        }
        else {
            for (Dormitory d : dormHibCtrl.getAllDorms()) {
                DormsParameters dormParameters = new DormsParameters();
                dormParameters.setDormitorynum(d.getDorm_num());
                dormParameters.setRcount(d.getRooms_count());
                dormParameters.setAddress(d.getAddress());
                obsList.add(dormParameters);
            }
        }

        dormTable.setItems(obsList);

    }
    private void fillTables(int adminid) {
        dormsList.getItems();
        List<Dormitory> dorms=dormHibCtrl.getDormByAdminId(adminid);
        for ( Dormitory d : dorms) {
            dormsList.getItems().add( d.getDorm_num()+" | "+d.getRooms_count() + " | " + d.getAddress() );
        }
    }


    public void gobackmain(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainPage.fxml"));
        Parent parent1=fxmlLoader.load();
        MainPageCtrl mainPageCtrl = fxmlLoader.getController();
        mainPageCtrl.setUser_id(this.user_id);
        Stage stage=(Stage)adminID_to_view.getScene().getWindow();
        Scene scene = new Scene(parent1);
        stage.setScene(scene);
        stage.show();
    }

    public void viewDorms(ActionEvent actionEvent) {
        fillTables(Integer.parseInt(adminID_to_view.getText()));
    }

    public void assignDorm(ActionEvent actionEvent) {
        Administrator adm= userHibCtrl.getAdminById(Integer.parseInt(adminID_to_assign.getText()));
        Dormitory dormitory = dormHibCtrl.getDormByNum(Integer.parseInt(dormIdText.getText()));
        adm.getDorms().add(dormitory);
        dormitory.setAdmin_of_dorm(adm);
        userHibCtrl.editUser(adm);
        dormHibCtrl.editDorm(dormitory);
        SignIN.openMessageBox("Dormitory "+dormitory.getDorm_num()+" is assigned to "+adm.getClass());
    }

    public void addDorm(ActionEvent actionEvent) {
        List <Room>rooms=new ArrayList<>();
        Dormitory dormitory=new Dormitory(addressText.getText(),rooms);
        dormHibCtrl.addnewDorm(dormitory);
        SignIN.openMessageBox("Dormitory is added");
    }
}
