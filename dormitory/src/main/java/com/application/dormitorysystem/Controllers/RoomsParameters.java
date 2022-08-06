package com.application.dormitorysystem.Controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class RoomsParameters {
    private SimpleIntegerProperty roomnum = new SimpleIntegerProperty();
    private SimpleIntegerProperty dormnum = new SimpleIntegerProperty();
    private SimpleStringProperty rtype = new SimpleStringProperty();
    private SimpleStringProperty available = new SimpleStringProperty();
    private LocalDate date;

    public int getRoomnum() {
        return roomnum.get();
    }

    public SimpleIntegerProperty roomnumProperty() {
        return roomnum;
    }

    public void setRoomnum(int roomnum) {
        this.roomnum.set(roomnum);
    }

    public int getDormnum() {
        return dormnum.get();
    }

    public SimpleIntegerProperty dormnumProperty() {
        return dormnum;
    }

    public void setDormnum(int dormnum) {
        this.dormnum.set(dormnum);
    }

    public String getRtype() {
        return rtype.get();
    }

    public SimpleStringProperty rtypeProperty() {
        return rtype;
    }

    public void setRtype(String rtype) {
        this.rtype.set(rtype);
    }

    public String getAvailable() {
        return available.get();
    }

    public SimpleStringProperty availableProperty() {
        return available;
    }

    public void setAvailable(String available) {
        this.available.set(available);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
