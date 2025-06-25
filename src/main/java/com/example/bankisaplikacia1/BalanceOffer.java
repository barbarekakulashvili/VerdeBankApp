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

public class BalanceOffer {
    @FXML
    private Label mainBalanceLabel;
    @FXML
    private Label phoneBalanceLabel;
    @FXML
    private Label phoneBalanceLabel1;
    @FXML
    private Label phoneBalanceLabel2;
    @FXML
    private Button balanceButton;
    @FXML
    private Button balanceButton1;
    @FXML
    private Button balanceButton2;
    @FXML
    private Button backButton;
    @FXML
    private Label successfulBalanceLabel;
    @FXML
    private Label successfulBalanceLabel1;
    @FXML
    private Label successfulBalanceLabel2;


    @FXML
    private void balanceOfferToOffers(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("offers.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void balanceOfferMethod(ActionEvent e) {
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

            if (points < 3000) {
                successfulBalanceLabel.setText("You don't have enough Piko points! You have "
                        + PiggyBank.currentPoints + " Piko points!");
                connection.close();
                return;
            }

            PreparedStatement preparedStatement2 = connection.prepareStatement("update customers set points = points - ? where username = ?");
            preparedStatement2.setInt(1, 3000);
            preparedStatement2.setString(2, LogIn.currentUser);
            int rows = preparedStatement2.executeUpdate();
            preparedStatement2.close();

            PreparedStatement preparedStatement3 = connection.prepareStatement("update customers set cash = cash + ? where username = ?");
            preparedStatement3.setDouble(1, 5);
            preparedStatement3.setString(2, LogIn.currentUser);
            int rows1 = preparedStatement3.executeUpdate();
            preparedStatement3.close();

            PreparedStatement preparedStatement4 = connection.prepareStatement("select cash from customers where username = ?");
            preparedStatement4.setString(1, LogIn.currentUser);
            ResultSet resultSet2 = preparedStatement4.executeQuery();

            double balance = 0;
            if (resultSet2.next()) {
                balance = resultSet2.getDouble("cash");
                Balance.currentBalance = balance;
            }
            resultSet2.close();
            preparedStatement4.close();
            connection.close();

            if (rows > 0 && rows1 > 0) {
                PiggyBank.currentPoints = points - 3000;
                Balance.currentBalance = balance + 5;
                successfulBalanceLabel.setText("You successfully activated this offer!");
            } else successfulBalanceLabel.setText("Offer activation failed!");


        } catch (SQLException sql) {
            sql.printStackTrace();
        }

    }


    @FXML
    private void balanceOfferMethod1(ActionEvent e) {
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

            if (points < 5000) {
                successfulBalanceLabel1.setText("You don't have enough Piko points! You have "
                        + PiggyBank.currentPoints + " Piko points!");
                connection.close();
                return;
            }

            PreparedStatement preparedStatement2 = connection.prepareStatement("update customers set points = points - ? where username = ?");
            preparedStatement2.setInt(1, 5000);
            preparedStatement2.setString(2, LogIn.currentUser);
            int rows = preparedStatement2.executeUpdate();
            preparedStatement2.close();

            PreparedStatement preparedStatement3 = connection.prepareStatement("update customers set cash = cash + ? where username = ?");
            preparedStatement3.setDouble(1, 10);
            preparedStatement3.setString(2, LogIn.currentUser);
            int rows1 = preparedStatement3.executeUpdate();
            preparedStatement3.close();

            PreparedStatement preparedStatement4 = connection.prepareStatement("select cash from customers where username = ?");
            preparedStatement4.setString(1, LogIn.currentUser);
            ResultSet resultSet2 = preparedStatement4.executeQuery();

            double balance = 0;
            if (resultSet2.next()) {
                balance = resultSet2.getDouble("cash");
                Balance.currentBalance = balance;
            }
            resultSet2.close();
            preparedStatement4.close();
            connection.close();

            if (rows > 0 && rows1 > 0) {
                PiggyBank.currentPoints = points - 5000;
                Balance.currentBalance = balance + 10;
                successfulBalanceLabel1.setText("You successfully activated this offer!");
            } else successfulBalanceLabel1.setText("Offer activation failed!");


        } catch (SQLException sql) {
            sql.printStackTrace();
        }
    }

    @FXML
    private void balanceOfferMethod2(ActionEvent e) {
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

            if (points < 20000) {
                successfulBalanceLabel2.setText("You don't have enough Piko points! You have "
                        + PiggyBank.currentPoints + " Piko points!");
                connection.close();
                return;
            }

            PreparedStatement preparedStatement2 = connection.prepareStatement("update customers set points = points - ? where username = ?");
            preparedStatement2.setInt(1, 20000);
            preparedStatement2.setString(2, LogIn.currentUser);
            int rows = preparedStatement2.executeUpdate();
            preparedStatement2.close();

            PreparedStatement preparedStatement3 = connection.prepareStatement("update customers set cash = cash + ? where username = ?");
            preparedStatement3.setDouble(1, 50);
            preparedStatement3.setString(2, LogIn.currentUser);
            int rows1 = preparedStatement3.executeUpdate();
            preparedStatement3.close();

            PreparedStatement preparedStatement4 = connection.prepareStatement("select cash from customers where username = ?");
            preparedStatement4.setString(1, LogIn.currentUser);
            ResultSet resultSet2 = preparedStatement4.executeQuery();

            double balance = 0;
            if (resultSet2.next()) {
                balance = resultSet2.getDouble("cash");
                Balance.currentBalance = balance;
            }
            resultSet2.close();
            preparedStatement4.close();
            connection.close();

            if (rows > 0 && rows1 > 0) {
                PiggyBank.currentPoints = points - 20000;
                Balance.currentBalance = balance + 50;
                successfulBalanceLabel2.setText("You successfully activated this offer!");
            } else successfulBalanceLabel2.setText("Offer activation failed!");


        } catch (SQLException sql) {
            sql.printStackTrace();
        }

    }
}
