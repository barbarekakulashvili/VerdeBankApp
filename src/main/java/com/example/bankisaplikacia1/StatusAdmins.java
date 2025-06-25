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

import javax.swing.*;
import java.io.IOException;
import java.sql.*;

public class StatusAdmins  {
    @FXML
    private Label mainLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private Button checkButton;
    @FXML
    private Button backButton;
    @FXML
    private Label answerLabel;

    @FXML
    private void StatusCustomerToMainMenuAdmins(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainMenuAdmins.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void statusCheckMethod(ActionEvent e){
        try{
            Connection connection = DriverManager.getConnection(HelloApplication.url, HelloApplication.username,
                    HelloApplication.password);

            String username = usernameTextField.getText();
            PreparedStatement preparedStatement = connection.prepareStatement("select status from customers where username = ?");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String status = resultSet.getString("status");
                if(status.equals("frozen")) answerLabel.setText(username + "'s status is locked");
                else answerLabel.setText(username + "'s status is active");
            } else{
                answerLabel.setText("No account matches the provided username.");
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException sql) {
            sql.printStackTrace();
        }
    }



}
