package com.application.dormitorysystem.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("admin")
public class Administrator extends User {
    @OneToMany(mappedBy = "admin_of_dorm", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Dormitory> dorms;


    public Administrator() {
    }

    public Administrator(String login, String password, String name, String surname, String phone_num, String email, List<Dormitory> dorms) {
        super(login, password, name, surname, phone_num, email);
        this.dorms = dorms;
    }

    public List<Dormitory> getDorms() {
        return dorms;
    }


    public void setDorms(List<Dormitory> dorms) {
        this.dorms = dorms;
    }
}
