package com.application.dormitorysystem.Tools;

import com.application.dormitorysystem.model.Room;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class DBclass {
    public static Connection connectDb() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String DB_URL = "jdbc:mysql://localhost:3306/dormdb";
            String USER = "root";
            String PASS = "";
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException | ClassNotFoundException t) {
            t.printStackTrace();
        }
        return conn;
    }

    public static void disconnectDb(Connection connection, Statement statement) {

        try {
            if (connection != null && statement != null) {
                connection.close();
                statement.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteUser(String table,String param,int Id) {
        Connection connection = connectDb();
        String sql = "DELETE FROM "+table+" where "+param+" = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, Id);
            preparedStatement.execute();
            disconnectDb(connection, preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static void updateField(String table, String dbColName,String param, String value, int Id) {
        Connection connection = connectDb();
        String sql = "UPDATE "+table+" SET " + dbColName + " = ? WHERE "+ param+" = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, value);
            preparedStatement.setInt(2, Id);
            preparedStatement.execute();
            disconnectDb(connection, preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateField(String table, String dbColName,String param, int value, int Id) {
        Connection connection = connectDb();
        String sql = "UPDATE "+table+" SET " + dbColName + " = ? WHERE "+param+" = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, value);
            preparedStatement.setInt(2, Id);
            preparedStatement.execute();
            disconnectDb(connection, preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Room> getRoomsbyDormnum(int dormnum) {
        Connection connection = connectDb();
        ArrayList<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms WHERE rooms.dormnum = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, dormnum);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
                rooms.add(new Room(rs.getInt(1), rs.getString(2), rs.getInt(4) == 1));
            disconnectDb(connection, preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }
}
