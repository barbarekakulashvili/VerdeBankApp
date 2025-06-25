package com.example.bankisaplikacia1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Send implements Initializable {
    @FXML
    private Label sendLabel;
    @FXML
    private TextField sendField;
    @FXML
    private TextField recipientField;
    @FXML
    private Button sendButton;
    @FXML
    private Button backButton;
    @FXML
    private Label notEnoughInfoLabel;
    @FXML
    private PasswordField pinCodeField;
    @FXML
    private Button sendPinButton;
    @FXML
    private Label additionalLabel;
    @FXML
    private Button unfreezeButton;

    @FXML
    private CheckBox showPinCode;
    @FXML
    private TextField visiblePinCode;

    private boolean pinNeeded = false;

    @FXML
    private void SendToTransactions(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("makeTransaction.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void sendMethod(ActionEvent e) {
        additionalLabel.setText("");
        notEnoughInfoLabel.setText("");
        String amountString = sendField.getText();
        String recipient = recipientField.getText();

        if (amountString.isEmpty() || recipient.isEmpty()) {
            notEnoughInfoLabel.setText("The information provided is insufficient.\nPlease complete all required fields!");
            return;
        }
        double amountDouble;

        try{
            amountDouble = Double.parseDouble(amountString);
            if(amountDouble <= 0){
                notEnoughInfoLabel.setText("Invalid input. Please re-enter the details!");
                return;
            }
        } catch (NumberFormatException ex) {
            notEnoughInfoLabel.setText("Amount must be a valid number!");
            return;
        }


        try {
            Connection connection = DriverManager.getConnection(HelloApplication.url, HelloApplication.username,
                    HelloApplication.password);

            PreparedStatement preparedStatement4 = connection.prepareStatement("select status from customers where username = ?");
            preparedStatement4.setString(1, LogIn.currentUser);
            ResultSet resultSet4 = preparedStatement4.executeQuery();
            if (resultSet4.next()) {
                String status  = resultSet4.getString("status");

                if (status.equals("frozen")) {
                    notEnoughInfoLabel.setText("Your card is locked! Press this button to reactivate it ->");
                    unfreezeButton.setVisible(true);
                    unfreezeButton.setManaged(true);
                    return;
                }
            }

            PreparedStatement preparedStatement6 = connection.prepareStatement("select * from customers where username = ?");
            preparedStatement6.setString(1, recipient);
            ResultSet resultSet6 = preparedStatement6.executeQuery();
            if(!resultSet6.next()){
                notEnoughInfoLabel.setText("No account matches the provided recipient.");
                recipientField.clear();
                connection.close();
                return;
            }
            if(recipient.equals(LogIn.currentUser)){
                notEnoughInfoLabel.setText("You can't send money to yourself.");
                recipientField.clear();
                return;
            }
            PreparedStatement preparedStatement = connection.prepareStatement("select cash from customers where username = ?");
            preparedStatement.setString(1, LogIn.currentUser);
            ResultSet resultSet = preparedStatement.executeQuery();

            PreparedStatement preparedStatement7 = connection.prepareStatement("select deducted_amount from customers where username = ?");
            preparedStatement7.setString(1, LogIn.currentUser);
            ResultSet resultSet7 = preparedStatement7.executeQuery();
            double deductedAmount = 0;
            if(resultSet7.next()){
                deductedAmount = resultSet7.getInt("deducted_amount");
            }
            resultSet7.close();
            preparedStatement7.close();

            double totalDeductedAmount = deductedAmount + amountDouble;

            PreparedStatement preparedStatement3 = connection.prepareStatement("update customers set points = points + ?" +
                    " where username = ?");
            int points = (int) amountDouble / 10;
            preparedStatement3.setInt(1, points);
            preparedStatement3.setString(2, LogIn.currentUser);

            if (resultSet.next()) {
                double balance = resultSet.getDouble("cash");
                int rows = preparedStatement3.executeUpdate();
                if(rows > 0) {
                    notEnoughInfoLabel.setText("");
                    additionalLabel.setText("");
                }
                if (balance < amountDouble)
                    notEnoughInfoLabel.setText("Insufficient funds. Your current balance is: " + balance);
                else {
                    if (amountDouble >= 500 && !pinNeeded) {
                        pinNeeded = true;
                        pinCodeField.setVisible(true);
                        pinCodeField.setManaged(true);
                        sendPinButton.setVisible(true);
                        sendPinButton.setManaged(true);
                        showPinCode.setVisible(true);
                        showPinCode.setManaged(true);
                        notEnoughInfoLabel.setText("Please input PIN to continue.");
                        return;
                    }

                    if (pinNeeded) {
                        String pincode = pinCodeField.getText();
                        if (pincode.isEmpty()) {
                            notEnoughInfoLabel.setText("Incorrect or missing PIN!");
                            return;
                        }
                        if (!pinChecker(pincode)) {
                            notEnoughInfoLabel.setText("Incorrect PIN!");
                            return;
                        }
                    }
                    PreparedStatement preparedStatement1 = connection.prepareStatement("update customers set cash = cash - ? where username = ? and " +
                            "cash >= ?");
                    preparedStatement1.setDouble(1, totalDeductedAmount);
                    preparedStatement1.setString(2, LogIn.currentUser);
                    preparedStatement1.setDouble(3, totalDeductedAmount);
                    Balance.currentBalance = balance - totalDeductedAmount;

                    PreparedStatement preparedStatement2 = connection.prepareStatement("update customers set cash = cash + ? where username = ?");
                    preparedStatement2.setDouble(1, amountDouble);
                    preparedStatement2.setString(2, recipient);


                    int amountOfRows = preparedStatement1.executeUpdate();
                    if (amountOfRows > 0){
                        preparedStatement2.executeUpdate();

                        PreparedStatement preparedStatement5 = connection.prepareStatement("insert into Transactions values(?, ?, ?, ?) ");
                        preparedStatement5.setString(1, LogIn.currentUser);
                        preparedStatement5.setString(2, recipient);
                        preparedStatement5.setDouble(3, amountDouble);
                        preparedStatement5.setString(4, "Sent money");

                        int rows5 = preparedStatement5.executeUpdate();
                        if(rows5 > 0) {
                            notEnoughInfoLabel.setText("");
                            additionalLabel.setText("Money successfully sent to " + recipient + "\nPiko Points have been added to your Piko Card!");
                        }
                    }
                    else notEnoughInfoLabel.setText("Transaction failed. Please try again!");
                }
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException sql) {
            sql.printStackTrace();
        }
    }


    @FXML
    private void SendToUnfreeze(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("unfreeze.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pinCodeField.setVisible(false);
        pinCodeField.setManaged(false);
        sendPinButton.setVisible(false);
        sendPinButton.setManaged(false);
        unfreezeButton.setVisible(false);
        unfreezeButton.setManaged(false);

        visiblePinCode.setVisible(false);
        visiblePinCode.setVisible(false);
        showPinCode.setVisible(false);
        showPinCode.setManaged(false);

    }

    @FXML
    private void pinCodeVisibility(ActionEvent e){
        if(showPinCode.isSelected()){
            visiblePinCode.setText(pinCodeField.getText());
            visiblePinCode.setVisible(true);
            visiblePinCode.setManaged(true);

            pinCodeField.setVisible(false);
            pinCodeField.setManaged(false);
        } else{
            pinCodeField.setText(visiblePinCode.getText());
            visiblePinCode.setVisible(false);
            visiblePinCode.setManaged(false);

            pinCodeField.setVisible(true);
            pinCodeField.setManaged(true);
        }
    }

    private boolean pinChecker(String pinCode) {
        try {
            Connection connection = DriverManager.getConnection(HelloApplication.url, HelloApplication.username,
                    HelloApplication.password);
            PreparedStatement preparedStatement = connection.prepareStatement("select pinCode from customers where username = ?");
            preparedStatement.setString(1, LogIn.currentUser);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String pin = resultSet.getString("pinCode");
                return pin.equals(pinCode);
            } else return false;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
