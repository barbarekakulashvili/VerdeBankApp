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

public class InternetOffer {
    @FXML
    private Label mainLabel;
    @FXML
    private Label internetLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label balanceLabel;
    @FXML
    private Button internetButton;
    @FXML
    private Button phoneButton;
    @FXML
    private Button balanceButton;
    @FXML
    private Button backButton;
    @FXML
    private Label successfulInternetLabel;
    @FXML
    private Label successfulInternetLabel1;
    @FXML
    private Label successfulInternetLabel2;

    @FXML
    private void InternetOfferToOffers(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("offers.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void internetOfferMethod(ActionEvent e){
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

                    if (points < 1000) {
                    successfulInternetLabel.setText("You don't have enough Piko points! You have "
                            + PiggyBank.currentPoints + " Piko points!");
                    connection.close();
                    return;
                }

                PreparedStatement preparedStatement2 = connection.prepareStatement("update customers set points = points - ? where username = ?");
                preparedStatement2.setInt(1, 1000);
                preparedStatement2.setString(2, LogIn.currentUser);
                int rows = preparedStatement2.executeUpdate();

                preparedStatement2.close();
                connection.close();

                    if (rows > 0) {
                        PiggyBank.currentPoints = points - 1000;
                        successfulInternetLabel.setText("You successfully activated this offer!");
                    } else successfulInternetLabel.setText("Offer activation failed!");

            } catch(SQLException sql){
                sql.printStackTrace();
            }

        }


    @FXML
    private void internetOfferMethod1(ActionEvent e) {
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

            if (points < 2500) {
                successfulInternetLabel1.setText("You don't have enough Piko points! You have "
                        + PiggyBank.currentPoints + " Piko points!");
                connection.close();
                return;
            }

            PreparedStatement preparedStatement2 = connection.prepareStatement("update customers set points = points - ? where username = ?");
            preparedStatement2.setInt(1, 2500);
            preparedStatement2.setString(2, LogIn.currentUser);
            int rows = preparedStatement2.executeUpdate();

            preparedStatement2.close();
            connection.close();

            if (rows > 0) {
                PiggyBank.currentPoints = points - 2500;
                successfulInternetLabel1.setText("You successfully activated this offer!");
            } else successfulInternetLabel1.setText("Offer activation failed!");

        } catch (SQLException sql) {
            sql.printStackTrace();
        }
    }

        @FXML
        private void internetOfferMethod2(ActionEvent e){
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

                if (points < 3500) {
                    successfulInternetLabel2.setText("You don't have enough Piko points! You have "
                            + PiggyBank.currentPoints + " Piko points!");
                    connection.close();
                    return;
                }

                PreparedStatement preparedStatement2 = connection.prepareStatement("update customers set points = points - ? where username = ?");
                preparedStatement2.setInt(1, 3500);
                preparedStatement2.setString(2, LogIn.currentUser);
                int rows = preparedStatement2.executeUpdate();

                preparedStatement2.close();
                connection.close();

                if (rows > 0) {
                    PiggyBank.currentPoints= points - 3500;
                    successfulInternetLabel2.setText("You successfully activated this offer!");
                } else successfulInternetLabel2.setText("Offer activation failed!");

            } catch(SQLException sql){
                sql.printStackTrace();
            }

        }

    }







