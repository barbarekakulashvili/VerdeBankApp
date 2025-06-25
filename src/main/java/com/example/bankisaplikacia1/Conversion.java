package com.example.bankisaplikacia1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Conversion implements Initializable {
    @FXML
    private Label questionLabel;
    @FXML
    private Label fromWhichLabel;
    @FXML
    private Label toWhichLabel;
    @FXML
    private ChoiceBox<String> fromWhichBox;
    @FXML
    private ChoiceBox<String> toWhichBox;
    @FXML
    private Label amountLabel;
    @FXML
    private TextField amountTextField;
    @FXML
    private Button backButton;
    @FXML
    private Button convertButton;
    @FXML
    private Label answerLabel;

    @FXML
    private void conversionToMainMenu(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] choice1 = {"GEL", "USD", "EUR"};
        fromWhichBox.getItems().addAll(choice1);
        toWhichBox.getItems().addAll(choice1);
    }



    private void GELtoUSD(double amountOfMoney){
            double answer = amountOfMoney / 2.7;
            answerLabel.setText(amountOfMoney + " GEL is " + answer + " USD");

    }

    private void GELtoEUR(double amountOfMoney){
            double answer = amountOfMoney / 3.2;
            answerLabel.setText(amountOfMoney + " GEL is " + answer + " EUR");
    }

    private void USDtoGEL(double amountOfMoney){
            double answer = amountOfMoney * 2.7;
            answerLabel.setText(amountOfMoney + " USD is " + answer + " GEL");
    }

    private void USDtoEUR(double amountOfMoney){
            double answer = amountOfMoney / 1.08;
            answerLabel.setText(amountOfMoney + " USD is " + answer + " EUR");

    }

    private void EURtoGEL(double amountOfMoney){
            double answer = amountOfMoney * 3.2;
            answerLabel.setText(amountOfMoney + " EUR is " + answer + " GEL");
    }

    private void EURtoUSD(double amountOfMoney){
            double answer = amountOfMoney * 1.08;
            answerLabel.setText(amountOfMoney + " EUR is " + answer + " USD");
    }

    @FXML
    private void ConversionMethod(ActionEvent e){
        String fromWhich1 = fromWhichBox.getValue();
        String toWhich1 = toWhichBox.getValue();
        String amountOfMoneyString = amountTextField.getText();

        if(fromWhich1 == null || toWhich1 == null || amountOfMoneyString.isEmpty()){
            answerLabel.setText("The information provided is insufficient.\nPlease complete all required fields!");
        }
        double amountOfMoney = Double.parseDouble(amountOfMoneyString);

        if(fromWhich1.equals("GEL") && toWhich1.equals("USD")) {
            GELtoUSD(amountOfMoney);
        }
        else if(fromWhich1.equals("GEL") && toWhich1.equals("EUR")) {
            GELtoEUR(amountOfMoney);
        }
        else if(fromWhich1.equals("USD") && toWhich1.equals("EUR")) {
            USDtoEUR(amountOfMoney);
        }
        else if(fromWhich1.equals("USD") && toWhich1.equals("GEL")) {
            USDtoGEL(amountOfMoney);
        }
        else if(fromWhich1.equals("EUR") && toWhich1.equals("USD")) {
            EURtoUSD(amountOfMoney);
        }
        else if(fromWhich1.equals("EUR") && toWhich1.equals("GEL")) {
            EURtoGEL(amountOfMoney);
        }
    }

}
