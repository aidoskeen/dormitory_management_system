package com.application.dormitorysystem.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate dateWritten;
    private String comment;
    @OneToMany(mappedBy = "maincomment", cascade = {CascadeType.ALL}, orphanRemoval = true)
    @OrderBy("id ASC")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Comments> reply;
    @ManyToOne
    private Comments maincomment;
    @ManyToOne
    private Dormitory dorm;
    public Comments() {
    }

    public Comments(LocalDate dateWritten, String comment, Comments maincomment) {
        this.dateWritten = dateWritten;
        this.comment = comment;
        this.maincomment = maincomment;
    }

    public Comments(LocalDate dateWritten, String comment, Dormitory dorm) {
        this.dateWritten = dateWritten;
        this.comment = comment;
        this.dorm = dorm;
    }

    public Dormitory getDorm() {
        return dorm;
    }

    public void setDorm(Dormitory dorm) {
        this.dorm = dorm;
    }

    public int getId() {
        return id;
    }

    public List<Comments> getReply() {
        return reply;
    }

    public void setReply(List<Comments> reply) {
        this.reply = reply;
    }

    public Comments getMaincomment() {
        return maincomment;
    }

    public void setMaincomment(Comments maincomment) {
        this.maincomment = maincomment;
    }


    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDateWritten() {
        return dateWritten;
    }

    public void setDateWritten(LocalDate dateWritten) {
        this.dateWritten = dateWritten;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return id +
                ":" + comment;
    }
}
