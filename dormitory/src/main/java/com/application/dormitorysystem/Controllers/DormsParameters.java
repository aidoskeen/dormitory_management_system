package com.application.dormitorysystem.Controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DormsParameters{
    private SimpleIntegerProperty dormitorynum = new SimpleIntegerProperty();
    private SimpleStringProperty address = new SimpleStringProperty();
    private SimpleIntegerProperty rcount = new SimpleIntegerProperty();

    public int getDormitorynum() {
        return dormitorynum.get();
    }

    public SimpleIntegerProperty dormitorynumProperty() {
        return dormitorynum;
    }

    public void setDormitorynum(int dormitorynum) {
        this.dormitorynum.set(dormitorynum);
    }

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public int getRcount() {
        return rcount.get();
    }

    public SimpleIntegerProperty rcountProperty() {
        return rcount;
    }

    public void setRcount(int rcount) {
        this.rcount.set(rcount);
    }
}
