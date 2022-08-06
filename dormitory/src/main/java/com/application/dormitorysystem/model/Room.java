package com.application.dormitorysystem.model;

import javax.persistence.*;
import java.time.LocalDate;
@Entity
public class Room {
    @Id
    @Column(name="room_num")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int room_num;
    private String room_type;
    private boolean available;
    private LocalDate issue_date;
    @OneToOne(mappedBy = "issued_room")
    private Student student;
    @ManyToOne
    private Dormitory dormitory;
    public Room(){}

    public void setStudent(Student student) {
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }

    public Dormitory getDormitory() {
        return dormitory;
    }

    public Room(String room_type) {
        this.room_type = room_type;
    }

    public Room(String room_type, boolean available, LocalDate issue_date) {
        this.room_type = room_type;
        this.available = available;
        this.issue_date = issue_date;
    }

    public Room(int room_num, String room_type, boolean available) {
        this.room_num = room_num;
        this.room_type = room_type;
        this.available = available;
    }

    public void setDormitory(Dormitory dormitory) {
        this.dormitory = dormitory;
    }

    public int getRoom_num() {
        return room_num;
    }

    public void setRoom_num(int room_num) {
        this.room_num = room_num;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public LocalDate getIssue_date() {
        return issue_date;
    }

    public void setIssue_date(LocalDate issue_date) {
        this.issue_date = issue_date;
    }
}
