package com.example.bankisaplikacia1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class PiggyBank implements Initializable {
    @FXML
    private Label piggyBankLabel;
    @FXML
    private Label pointsLabel;
    @FXML
    private Button backButton;

    public static int currentPoints;

    @FXML
    private void PiggyBankToMainMenu(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            Connection connection = DriverManager.getConnection(HelloApplication.url, HelloApplication.username,
                    HelloApplication.password);
            PreparedStatement preparedStatement = connection.prepareStatement("select points from customers where username = ?");
            preparedStatement.setString(1, LogIn.currentUser);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int points = resultSet.getInt("points");
                currentPoints = points;
                pointsLabel.setText("You have " + points + " Piko points");
            } else{
                pointsLabel.setText("No account matches the provided username.");
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
