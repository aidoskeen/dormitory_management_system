package com.application.dormitorysystem.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
public class Dormitory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dorm_num;
    private String address;
    private int rooms_count;
    @ManyToOne
    private Administrator admin_of_dorm;
    @OneToMany(mappedBy = "dormitory", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Room> rooms=new ArrayList();
    @OneToMany(mappedBy = "dorm", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Comments>DormitoryComments;

    public List<Comments> getDormitoryComments() {
        return DormitoryComments;
    }

    public void setDormitoryComments(List<Comments> dormitoryComments) {
        DormitoryComments = dormitoryComments;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public Dormitory(String address, List<Room> rooms) {
        this.address = address;
        this.rooms = rooms;
    }

    public Dormitory(){}

    public Administrator getAdmin_of_dorm() {
        return admin_of_dorm;
    }

    public void setAdmin_of_dorm(Administrator admin_of_dorm) {
        this.admin_of_dorm = admin_of_dorm;
    }

    public String getAddress() {
        return address;
    }
    public void setDorm_num(int dorm_num) {
        this.dorm_num = dorm_num;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public int getRooms_count() {
        return rooms_count;
    }
    public void setRooms_count(int rooms_count) {
        this.rooms_count = rooms_count;
    }
    public int getDorm_num() {
        return dorm_num;
    }

    @Override
    public String toString() {
        return "Dormitory{" +
                "dorm_num=" + dorm_num +
                ", address='" + address + '\'' +
                ", rooms_count=" + rooms_count +
                '}';
    }
}
