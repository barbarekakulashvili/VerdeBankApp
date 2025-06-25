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

import java.io.IOException;

public class MainMenuAdmins {
    @FXML
    private Label mainLabel;
    @FXML
    private Button customersHistoryButton;
    @FXML
    private Button customersStatusButton;
    @FXML
    private Button freezeCustomersButton;
    @FXML
    private Button unfreezeCustomersButton;
    @FXML
    private Button removeCustomersButton;
    @FXML
    private Button backButton;


    @FXML
    private void MainMenuAdminsToCardUnfreeze(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("unfreezeAdmins.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void MainMenuAdminsToCardFreeze(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("freezeAdmins.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
    @FXML
    private void MainMenuAdminsToLogIn(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
    @FXML
    private void MainMenuAdminsToRemoveCustomer(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("removeAdmins.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
    @FXML
    private void MainMenuAdminsToStatusCustomers(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("statusAdmins.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
    @FXML
    private void MainMenuAdminsToHistory(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("historyAdmins.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }


}
