package com.application.dormitorysystem.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="user_type",
        discriminatorType = DiscriminatorType.STRING)
public abstract class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //This is id in your table, will be autoincremented
    //No need to add to other fields, they will be converted to columns of table user
    private int userId;
    @Column(unique = true)
    private String login;
    private String password;
    private String name;
    private String surname;
    private String phone_num;
    private String email;
    @Column(insertable = false,updatable = false)
    private String user_type;
    private String level_of_access;

    public String getLevel_of_access() {
        return level_of_access;
    }

    public void setLevel_of_access(String level_of_access) {
        this.level_of_access = level_of_access;
    }

    //Entity class must have no args constructor, very good that this is here
    public User() {
    }

    public User(int user_id, String login, String password, String name, String surname, String phone_num, String email, String user_type) {
        this.userId = user_id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.phone_num = phone_num;
        this.email = email;
        this.user_type = user_type;
    }

    public User(String login, String password, String name, String surname, String phone_num, String email) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.phone_num = phone_num;
        this.email = email;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    //Getters and setters for all fields for entity class


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return user_type;
    }

    public void setUserType(String userType) {
        this.user_type = user_type;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "{" +
                "\"userId\":" + userId +
                ", \"name\":'" + name + '\'' +
                ", \"surname\":'" + surname + '\'' +
                ", \"phone_num\":='" + phone_num + '\'' +
                ", \"email\":'" + email + '\'' +
                ", \"user_type\"" + user_type + '\'' +
                '}';
    }
}
