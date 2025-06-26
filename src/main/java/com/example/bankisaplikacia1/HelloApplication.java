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
    public static String username = "";
    public static String password = "";


    @Override
    public void start(Stage stage) {
        try{
            Parent root = FXMLLoader.load(HelloApplication.class.getResource("connector.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();


        } catch(IOException io){
        io.getMessage();
    }

    }
    public static void main(String[] args) throws SQLException {

        launch();
    }
}