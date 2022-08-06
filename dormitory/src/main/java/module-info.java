module com.example.labwork {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires mysql.connector.java;
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires spring.context;
    requires spring.web;
    requires spring.core;
    requires com.google.gson;

    opens com.application.dormitorysystem to javafx.fxml;
    exports com.application.dormitorysystem;
    exports com.application.dormitorysystem.webcontrollers;
    exports com.application.dormitorysystem.Controllers;
    opens com.application.dormitorysystem.Controllers to javafx.fxml;
    exports com.application.dormitorysystem.model;
    opens com.application.dormitorysystem.model to javafx.fxml, org.hibernate.orm.core;
    opens com.application.dormitorysystem.webcontrollers to javafx.fxml, com.google.gson;
}