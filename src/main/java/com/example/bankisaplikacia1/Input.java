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

public class Input implements Initializable {
    @FXML
    private Label inputLabel;
    @FXML
    private TextField inputField;
    @FXML
    private Button inputButton;
    @FXML
    private Button backButton;
    @FXML
    private Label notEnoughInfoLabel;
    @FXML
    private PasswordField pinCodeField;
    @FXML
    private Button inputPinButton;
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
    private void InputToTransactions(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("makeTransaction.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
    @FXML
    private void InputToUnfreeze(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("unfreeze.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }


    @FXML
    private void inputMethod(ActionEvent e) {
        String amountString = inputField.getText();
        if (amountString.isEmpty()) {
            notEnoughInfoLabel.setText("The information provided is insufficient.\nPlease complete all required fields!");
            return;
        }
        double amountDouble = Double.parseDouble(amountString);
        if (amountDouble <= 0) {
            notEnoughInfoLabel.setText("Invalid input. Please re-enter the details!");
            return;
        }

        try {
            Connection connection = DriverManager.getConnection(HelloApplication.url, HelloApplication.username, HelloApplication.password);
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement1 = connection.prepareStatement(
                    "select status from customers where username = ?"
            );
            preparedStatement1.setString(1, LogIn.currentUser);
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            resultSet1.next();
            String status = resultSet1.getString("status");

            if (status.equals("frozen")) {
                notEnoughInfoLabel.setText("Your card is locked! Press this button to reactivate it ->");
                unfreezeButton.setVisible(true);
                unfreezeButton.setManaged(true);
                resultSet1.close();
                preparedStatement1.close();
                connection.close();
                return;
            }
            resultSet1.close();
            preparedStatement1.close();

            PreparedStatement preparedStatement2 = connection.prepareStatement("select cash, loan, deducted_amount" +
                    " from customers where username = ?"
            );
            preparedStatement2.setString(1, LogIn.currentUser);
            ResultSet resultSet2 = preparedStatement2.executeQuery();

            if (!resultSet2.next()) {
                notEnoughInfoLabel.setText("No account matches the provided username.");
                resultSet2.close();
                preparedStatement2.close();
                connection.close();
                return;
            }

            double balance = resultSet2.getDouble("cash");
            double loan = resultSet2.getDouble("loan");
            double deductedAmount = resultSet2.getDouble("deducted_amount");
            resultSet2.close();
            preparedStatement2.close();

            if (amountDouble >= 500 && !pinNeeded) {
                pinNeeded = true;
                pinCodeField.setVisible(true);
                pinCodeField.setManaged(true);
                inputPinButton.setVisible(true);
                inputPinButton.setManaged(true);
                showPinCode.setVisible(true);
                showPinCode.setManaged(true);
                additionalLabel.setText("Please input PIN to continue");
                connection.close();
                return;
            }

            if (pinNeeded) {
                String pinCode = pinCodeField.getText();
                if (pinCode.isEmpty() || !pinChecker(pinCode)) {
                    notEnoughInfoLabel.setText("Incorrect or missing PIN!");
                    connection.close();
                    return;
                }
            }

            if (loan < deductedAmount) {
                notEnoughInfoLabel.setText("The loan is less than required deduction!");
                connection.close();
                return;
            }

            double amountToAdd = amountDouble - deductedAmount;

            PreparedStatement preparedStatement3 = connection.prepareStatement("update customers set cash = cash + ? " +
                    "where username = ?"
            );
            preparedStatement3.setDouble(1, amountToAdd);
            preparedStatement3.setString(2, LogIn.currentUser);

            PreparedStatement preparedStatement4 = connection.prepareStatement("update customers set loan " +
                    "= loan - ? where username = ?"
            );
            preparedStatement4.setDouble(1, deductedAmount);
            preparedStatement4.setString(2, LogIn.currentUser);

            PreparedStatement preparedStatement5 = connection.prepareStatement("update customers set points = points + ?" +
                    " where username = ?"
            );
            int points = (int) amountDouble / 10;
            preparedStatement5.setInt(1, points);
            preparedStatement5.setString(2, LogIn.currentUser);

            PreparedStatement preparedStatement6 = connection.prepareStatement(
                    "update customers set deducted_amount = ? where username = ?"
            );
            preparedStatement6.setDouble(1, deductedAmount);
            preparedStatement6.setString(2, LogIn.currentUser);

            int rows3 = preparedStatement3.executeUpdate();
            int rows4 = preparedStatement4.executeUpdate();
            int rows5 = preparedStatement5.executeUpdate();
            int rows6 = preparedStatement6.executeUpdate();

            if (rows3 > 0 && rows4 > 0 && rows5 > 0 && rows6 > 0) {
                Balance.currentBalance = balance + amountToAdd;

                PreparedStatement preparedStatement7 = connection.prepareStatement("insert into Transactions(sender, recipient, " +
                        "amount, type) values(?, ?, ?, ?) ");
                preparedStatement7.setString(1, LogIn.currentUser);
                preparedStatement7.setString(2, LogIn.currentUser);
                preparedStatement7.setDouble(3, amountDouble);
                preparedStatement7.setString(4, "Deposit money");

                int rows7 = preparedStatement7.executeUpdate();
                if(rows7 > 0) {
                    connection.commit();
                    notEnoughInfoLabel.setText("");
                    additionalLabel.setText("The withdrawal was completed successfully!\nPiko Points have been added to your Piko Card!");
                }
            } else {
                connection.rollback();
                notEnoughInfoLabel.setText("Transaction failed. Please try again!");
            }
            preparedStatement3.close();
            preparedStatement4.close();
            preparedStatement5.close();
            preparedStatement6.close();

            pinCodeField.setVisible(false);
            pinCodeField.setManaged(false);
            pinCodeField.clear();
            inputPinButton.setVisible(false);
            inputPinButton.setManaged(false);
            showPinCode.setVisible(false);
            showPinCode.setManaged(false);
            pinNeeded = false;

            connection.close();

        } catch (SQLException sql) {
            sql.printStackTrace();
        }
    }
    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){
        pinCodeField.setVisible(false);
        pinCodeField.setManaged(false);

        inputPinButton.setVisible(false);
        inputPinButton.setManaged(false);

        unfreezeButton.setVisible(false);
        unfreezeButton.setManaged(false);

        visiblePinCode.setVisible(false);
        visiblePinCode.setManaged(false);
        showPinCode.setVisible(false);
        showPinCode.setManaged(false);
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
}
