package com.application.dormitorysystem.model;

import javax.persistence.*;

@Entity
@DiscriminatorValue("stud")
public class Student extends User {
    private String Faculty;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_num", referencedColumnName = "room_num")
    private Room issued_room;

    public Student() {
    }

    public Student(String faculty) {
        Faculty = faculty;
    }

    public Student(Room issued_room) {
        this.issued_room = issued_room;
    }

    public Student(String login, String password, String name, String surname, String phone_num, String email, String faculty) {
        super(login, password, name, surname, phone_num, email);
        Faculty = faculty;
    }

    public Student(String login, String password, String faculty) {
        super(login, password);
        Faculty = faculty;
    }

    public String getFaculty() {
        return Faculty;
    }

    public void setFaculty(String faculty) {
        Faculty = faculty;
    }

    public Room getIssued_room() {
        return issued_room;
    }

    public void setIssued_room(Room issued_room) {
        this.issued_room = issued_room;
        this.issued_room.setStudent(this);
    }
}
