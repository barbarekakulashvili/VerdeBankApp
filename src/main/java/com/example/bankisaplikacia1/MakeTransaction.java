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

public class MakeTransaction {
    @FXML
    private Label mainLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Button takeOutButton;
    @FXML
    private Button inputButton;
    @FXML
    private Button sendButton;
    @FXML
    private Button backButton;


    @FXML
    private void TransactionsToMainMenu(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
    @FXML
    private void TransactionsToTakeOut(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("takeOut.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
    @FXML
    private void TransactionsToInput(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("input.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void TransactionsToSend(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("send.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }


}
