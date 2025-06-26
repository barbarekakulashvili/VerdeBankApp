package com.example.bankisaplikacia1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;
import java.sql.*;

public class Connector {
    @FXML
    private Label label1;
    @FXML
    private TextField userField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label errorLabel;
    @FXML
    private Button startButton;

    @FXML
    private void ConnectorToWelcome(javafx.event.ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Welcome.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }


    @FXML
    private void Method(ActionEvent e) throws IOException {
        HelloApplication.username = userField.getText();
        HelloApplication.password = passwordField.getText();

        try {

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", HelloApplication.username,
                    HelloApplication.password);
            Statement statement = connection.createStatement();
            statement.execute("create database if not exists bank");

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
                    "deducted_amount decimal(10,2) default 0 not null," +
                    "points int default 0 );");

            statement.executeUpdate("create table if not exists admins" +
                    "( username varchar(20) not null, " +
                    "password varchar(20) not null, " +
                    "code int default 0 not null );");


            if (!statement.executeQuery("select * from customers").next()) {
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
                preparedStatement1.setDouble(9, 0);
                preparedStatement1.setInt(10, 0);

                preparedStatement1.setString(11, "username2");
                preparedStatement1.setString(12, "password2");
                preparedStatement1.setString(13, "Female");
                preparedStatement1.setString(14, "Rustavi");
                preparedStatement1.setDouble(15, 0);
                preparedStatement1.setInt(16, 2222);
                preparedStatement1.setString(17, "active");
                preparedStatement1.setDouble(18, 0);
                preparedStatement1.setDouble(19, 0);
                preparedStatement1.setInt(20, 0);

                preparedStatement1.executeUpdate();

                //statement.execute("drop table if exists admins");

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


            }
            ConnectorToWelcome(e);
        } catch(SQLException a){
            errorLabel.setText("The information provided is insufficient.\nPlease complete all required fields!");
            System.out.println(a.getMessage());
        }
    }
}
