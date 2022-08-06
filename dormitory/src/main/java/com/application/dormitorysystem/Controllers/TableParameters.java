package com.application.dormitorysystem.Controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TableParameters {
    private SimpleIntegerProperty userid = new SimpleIntegerProperty();
    private SimpleStringProperty login = new SimpleStringProperty();
    private SimpleStringProperty password = new SimpleStringProperty();
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleStringProperty surname = new SimpleStringProperty();
    private SimpleStringProperty email = new SimpleStringProperty();
    private SimpleStringProperty phone = new SimpleStringProperty();
    private SimpleStringProperty usertype = new SimpleStringProperty();
    private SimpleIntegerProperty dorm_num= new SimpleIntegerProperty();
    private SimpleStringProperty faculty= new SimpleStringProperty();
    private SimpleIntegerProperty room_num= new SimpleIntegerProperty();

    public int getUserid() {
        return userid.get();
    }

    public SimpleIntegerProperty useridProperty() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid.set(userid);
    }

    public String getLogin() {
        return login.get();
    }

    public SimpleStringProperty loginProperty() {
        return login;
    }

    public void setLogin(String login) {
        this.login.set(login);
    }

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSurname() {
        return surname.get();
    }

    public SimpleStringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getPhone() {
        return phone.get();
    }

    public SimpleStringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public String getUsertype() {
        return usertype.get();
    }

    public SimpleStringProperty usertypeProperty() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype.set(usertype);
    }

    public int getDorm_num() {
        return dorm_num.get();
    }

    public SimpleIntegerProperty dorm_numProperty() {
        return dorm_num;
    }

    public void setDorm_num(int dorm_num) {
        this.dorm_num.set(dorm_num);
    }

    public String getFaculty() {
        return faculty.get();
    }

    public SimpleStringProperty facultyProperty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty.set(faculty);
    }

    public int getRoom_num() {
        return room_num.get();
    }

    public SimpleIntegerProperty room_numProperty() {
        return room_num;
    }

    public void setRoom_num(int room_num) {
        this.room_num.set(room_num);
    }
}
