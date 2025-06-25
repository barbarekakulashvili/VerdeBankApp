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

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Offers implements Initializable {
    @FXML
    private Label mainLabel;
    @FXML
    private Label internetLabel;
    @FXML
    private Label phoneBalanceLabel;
    @FXML
    private Label balanceLabel;
    @FXML
    private Label internetOfferLabel;
    @FXML
    private Label phoneBalanceOfferLabel;
    @FXML
    private Label balanceOfferLabel;
    @FXML
    private Button continueInternetButton;
    @FXML
    private Button continuePhoneBalanceButton;
    @FXML
    private Button continueBalanceButton;
    @FXML
    private Button BackButton;

    @FXML
    private Button unfreezeButton;
    @FXML
    private Label unfreezeLabel;



    @FXML
    private void continue1(ActionEvent e){
        try {
            Connection connection = DriverManager.getConnection(HelloApplication.url, HelloApplication.username,
                    HelloApplication.password);

            PreparedStatement preparedStatement4 = connection.prepareStatement("select status from customers where username = ?");
            preparedStatement4.setString(1, LogIn.currentUser);
            ResultSet resultSet4 = preparedStatement4.executeQuery();
            if (resultSet4.next()) {
                String status = resultSet4.getString("status");

                if (status.equals("frozen")) {
                    unfreezeButton.setVisible(true);
                    unfreezeButton.setManaged(true);
                    unfreezeLabel.setVisible(true);
                    unfreezeButton.setManaged(true);
                } else{
                    OffersToInternetOffer(e);
                }
            }
        } catch(SQLException | IOException sql){
            sql.printStackTrace();
        }

    }

    @FXML
    private void continue2(ActionEvent e){
        try {
            Connection connection = DriverManager.getConnection(HelloApplication.url, HelloApplication.username,
                    HelloApplication.password);

            PreparedStatement preparedStatement4 = connection.prepareStatement("select status from customers where username = ?");
            preparedStatement4.setString(1, LogIn.currentUser);
            ResultSet resultSet4 = preparedStatement4.executeQuery();
            if (resultSet4.next()) {
                String status = resultSet4.getString("status");

                if (status.equals("frozen")) {
                    unfreezeButton.setVisible(true);
                    unfreezeButton.setManaged(true);
                    unfreezeLabel.setVisible(true);
                    unfreezeButton.setManaged(true);

                } else{
                    OffersToPhoneOffer(e);
                }
            }
        } catch(SQLException | IOException sql){
            sql.printStackTrace();
        }

    }



    @FXML
    private void continue3(ActionEvent e){
        try {
            Connection connection = DriverManager.getConnection(HelloApplication.url, HelloApplication.username,
                    HelloApplication.password);

            PreparedStatement preparedStatement4 = connection.prepareStatement("select status from customers where username = ?");
            preparedStatement4.setString(1, LogIn.currentUser);
            ResultSet resultSet4 = preparedStatement4.executeQuery();
            if (resultSet4.next()) {
                String status = resultSet4.getString("status");

                if (status.equals("frozen")) {
                    unfreezeButton.setVisible(true);
                    unfreezeButton.setManaged(true);
                    unfreezeLabel.setVisible(true);
                    unfreezeButton.setManaged(true);
                } else{
                    OffersToBalanceOffer(e);
                }
            }
        } catch(SQLException | IOException sql){
            sql.printStackTrace();
        }

    }

    @FXML
    private void OffersToMainMenu(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void OffersToInternetOffer(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("internetOffer.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }


    @FXML
    private void OffersToPhoneOffer(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("phoneOffer.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void OffersToBalanceOffer(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("balanceOffer.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void OffersToUnfreeze(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("unfreeze.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        unfreezeButton.setVisible(false);
        unfreezeButton.setManaged(false);
        unfreezeLabel.setVisible(false);
        unfreezeButton.setManaged(false);
    }
}
