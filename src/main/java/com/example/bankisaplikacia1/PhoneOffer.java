package com.example.bankisaplikacia1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class PhoneOffer {
    @FXML
    private Label mainPhoneLabel;
    @FXML
    private Label phoneInternetLabel;
    @FXML
    private Label phoneInternetLabel1;
    @FXML
    private Label phoneInternetLabel2;
    @FXML
    private Button phoneButton;
    @FXML
    private Button phoneButton1;
    @FXML
    private Button phoneButton2;
    @FXML
    private Button backButton;
    @FXML
    private Label successfulInternetLabel;
    @FXML
    private Label successfulInternetLabel1;
    @FXML
    private Label successfulInternetLabel2;


    @FXML
    private void PhoneOfferToOffers(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("offers.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void phoneOfferMethod(ActionEvent e){
        try{
            Connection connection = DriverManager.getConnection(HelloApplication.url, HelloApplication.username,
                    HelloApplication.password);

            PreparedStatement preparedStatement1 = connection.prepareStatement("select points from customers where username = ?");
            preparedStatement1.setString(1, LogIn.currentUser);

            ResultSet resultSet = preparedStatement1.executeQuery();
            int points = 0;
            if(resultSet.next()){
                points = resultSet.getInt("points");
                PiggyBank.currentPoints = points;
            }
            resultSet.close();
            preparedStatement1.close();

            if (points < 2000) {
                successfulInternetLabel.setText("You don't have enough Piko points! You have "
                        + PiggyBank.currentPoints + " Piko points!");
                connection.close();
                return;
            }

            PreparedStatement preparedStatement2 = connection.prepareStatement("update customers set points = points - ? where username = ?");
            preparedStatement2.setInt(1, 2000);
            preparedStatement2.setString(2, LogIn.currentUser);
            int rows = preparedStatement2.executeUpdate();

            preparedStatement2.close();
            connection.close();

            if (rows > 0) {
                PiggyBank.currentPoints = points - 2000;
                successfulInternetLabel.setText("You successfully activated this offer!");
            } else successfulInternetLabel.setText("Offer activation failed!");

        } catch(SQLException sql){
            sql.printStackTrace();
        }

    }


    @FXML
    private void phoneOfferMethod1(ActionEvent e) {
        try {
            Connection connection = DriverManager.getConnection(HelloApplication.url, HelloApplication.username,
                    HelloApplication.password);

            PreparedStatement preparedStatement1 = connection.prepareStatement("select points from customers where username = ?");
            preparedStatement1.setString(1, LogIn.currentUser);

            ResultSet resultSet = preparedStatement1.executeQuery();
            int points = 0;
            if (resultSet.next()) {
                points = resultSet.getInt("points");
                PiggyBank.currentPoints = points;
            }
            resultSet.close();
            preparedStatement1.close();

            if (points < 4000) {
                successfulInternetLabel1.setText("You don't have enough Piko points! You have "
                        + PiggyBank.currentPoints + " Piko points!");
                connection.close();
                return;
            }

            PreparedStatement preparedStatement2 = connection.prepareStatement("update customers set points = points - ? where username = ?");
            preparedStatement2.setInt(1, 4000);
            preparedStatement2.setString(2, LogIn.currentUser);
            int rows = preparedStatement2.executeUpdate();

            preparedStatement2.close();
            connection.close();

            if (rows > 0) {
                PiggyBank.currentPoints = points - 4000;
                successfulInternetLabel1.setText("You successfully activated this offer!");
            } else successfulInternetLabel1.setText("Offer activation failed!");

        } catch (SQLException sql) {
            sql.printStackTrace();
        }
    }

    @FXML
    private void phoneOfferMethod2(ActionEvent e){
        try{
            Connection connection = DriverManager.getConnection(HelloApplication.url, HelloApplication.username,
                    HelloApplication.password);

            PreparedStatement preparedStatement1 = connection.prepareStatement("select points from customers where username = ?");
            preparedStatement1.setString(1, LogIn.currentUser);

            ResultSet resultSet = preparedStatement1.executeQuery();
            int points = 0;
            if(resultSet.next()){
                points = resultSet.getInt("points");
                PiggyBank.currentPoints = points;
            }
            resultSet.close();
            preparedStatement1.close();

            if (points < 5000) {
                successfulInternetLabel2.setText("You don't have enough Piko points! You have "
                        + PiggyBank.currentPoints + " Piko points!");
                connection.close();
                return;
            }

            PreparedStatement preparedStatement2 = connection.prepareStatement("update customers set points = points - ? where username = ?");
            preparedStatement2.setInt(1, 5000);
            preparedStatement2.setString(2, LogIn.currentUser);
            int rows = preparedStatement2.executeUpdate();

            preparedStatement2.close();
            connection.close();

            if (rows > 0) {
                PiggyBank.currentPoints= points - 5000;
                successfulInternetLabel2.setText("You successfully activated this offer!");
            } else successfulInternetLabel2.setText("Offer activation failed!");

        } catch(SQLException sql){
            sql.printStackTrace();
        }

    }



}
