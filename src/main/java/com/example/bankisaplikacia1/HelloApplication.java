package com.example.bankisaplikacia1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class HelloApplication extends Application {
    public static String url = "jdbc:mysql://localhost:3306/bank";
    public static String username = "root";
    public static String password = "anastasia";




    @Override
    public void start(Stage stage) {
        try{
            Parent root = FXMLLoader.load(HelloApplication.class.getResource("Welcome.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch(IOException io){
            io.getMessage();
        }

    }
    public static void main(String[] args) throws SQLException {

        Connection connection = DriverManager.getConnection(HelloApplication.url, HelloApplication.username,
                HelloApplication.password);
        Statement statement = connection.createStatement();
        statement.execute("use bank");
        //statement.execute("drop table if exists customers");
        statement.executeUpdate("create table if not exists customers" +
                "( username varchar(20) not null, " +
                "password varchar(20) not null, " +
                "gender varchar(10) not null, " +
                "city varchar(20) not null, " +
                "cash decimal(10, 2) default 0 not null, " +
                "pinCode int default 0 not null, " +
                "status varchar(10) default 'active', " +
                "loan decimal(10, 2) default 0 not null, " +
                "deducted_amount decimal(10,2) default 0 not null );");

       //statement.executeUpdate("alter table customers add column points int default 0");


        PreparedStatement preparedStatement1 = connection.prepareStatement("insert into customers values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)," +
                " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        preparedStatement1.setString(1, "username1");
        preparedStatement1.setString(2, "password1");
        preparedStatement1.setString(3, "Male");
        preparedStatement1.setString(4, "Tbilisi");
        preparedStatement1.setDouble(5, 0);
        preparedStatement1.setInt(6, 1111);
        preparedStatement1.setString(7, "active");
        preparedStatement1.setDouble(8, 0);
        preparedStatement1.setDouble(9,  0);
        preparedStatement1.setInt(10, 0);

        preparedStatement1.setString(11, "username2");
        preparedStatement1.setString(12, "password2");
        preparedStatement1.setString(13, "Female");
        preparedStatement1.setString(14, "Rustavi");
        preparedStatement1.setDouble(15, 0);
        preparedStatement1.setInt(16, 2222);
        preparedStatement1.setString(17, "active");
        preparedStatement1.setDouble(18, 0);
        preparedStatement1.setDouble(19,  0);
        preparedStatement1.setInt(20, 0);

        preparedStatement1.executeUpdate();


        //statement.execute("drop table if exists admins");
        statement.executeUpdate("create table if not exists admins" +
                "( username varchar(20) not null, " +
                "password varchar(20) not null, " +
                "code int default 0 not null );");

        PreparedStatement preparedStatement = connection.prepareStatement("insert into admins values(?, ?, ?)," +
                " (?, ?, ?), (?, ?, ?)");
        preparedStatement.setString(1, "admin1");
        preparedStatement.setString(2, "admin1password");
        preparedStatement.setInt(3, 7713);

        preparedStatement.setString(4, "admin2");
        preparedStatement.setString(5, "admin2password");
        preparedStatement.setInt(6, 7714);

        preparedStatement.setString(7, "admin3");
        preparedStatement.setString(8, "admin3password");
        preparedStatement.setInt(9, 7715);

        preparedStatement.executeUpdate();

        //statement.execute("drop table if exists Transactions");
        statement.executeUpdate("create table if not exists Transactions( " +
                "sender varchar(20), " +
                "recipient varchar(20), " +
                "amount decimal(10, 2), " +
                "type varchar(25) );");

        launch();
    }
}