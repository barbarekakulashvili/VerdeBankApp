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
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainMenu implements Initializable {
    @FXML
    private Button CheckBalanceButton;
    @FXML
    private Button LogOutButton;
    @FXML
    private Button TransactionsButton;
    @FXML
    private Button CurrencyConverterButton;
    @FXML
    private Button PiggyBankButton;
    @FXML
    private Button OffersButton;
    @FXML
    private Button BlockCardButton;
    @FXML
    private Button CardFreezeButton;
    @FXML
    private Button CardUnfreezeButton;
    @FXML
    private Button loanButton;
    @FXML
    private Button historyButton;
    @FXML
    private Button deductedAmountChange;
    @FXML
    private Label mainLabel;
    @FXML
    private Label helloLabel;


    @FXML
    private void balanceScene(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("balance.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void currencyConverterScene(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("conversion.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void MainMenuToLogIn(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
    @FXML
    private void MainMenuToTransactions(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("makeTransaction.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
    @FXML
    private void MainMenuToHistory(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("transactionsHistory.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
    @FXML
    private void MainMenuToPiggyBank(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("piggyBank.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
    @FXML
    private void MainMenuToOffers(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("offers.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void MainMenuToBlockCard(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("blockCard.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void MainMenuToCardFreeze(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("cardFreeze.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void MainMenuToCardUnfreeze(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("unfreeze.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void MainMenuToLoan(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("credit.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
    @FXML
    private void MainMenuToDeductedAmountChange(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("deductedAmountChange.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        helloLabel.setText("Hello, " + LogIn.currentUser);
    }
}
