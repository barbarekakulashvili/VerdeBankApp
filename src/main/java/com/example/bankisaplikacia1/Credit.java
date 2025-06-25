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

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class Credit implements Initializable {
    @FXML
    private Label mainLabel;
    @FXML
    private Label amountLabel;
    @FXML
    private Label incomeLabel;
    @FXML
    private Label durationLabel;
    @FXML
    private Label pinCodeLabel;
    @FXML
    private Label answerLabel;
    @FXML
    private TextField amountField;
    @FXML
    private TextField incomeField;
    @FXML
    private TextField durationField;
    @FXML
    private Button submitButton;
    @FXML
    private Button backButton;
    @FXML
    private TextField deductedField;
    @FXML
    private Button submitButton1;
    @FXML
    private PasswordField pinCodePasswordField;
    @FXML
    private  TextField visiblePinCode;
    @FXML
    private CheckBox showPinCode;



    public static int deductedAmountInt;

    @FXML
    private void CreditToMainMenu(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void submitMethod(ActionEvent e) throws SQLException {
        String amountString = amountField.getText();
        String incomeString = incomeField.getText();
        String durationString = durationField.getText();
        String pinCodeString = pinCodePasswordField.getText();

        if(amountString.isEmpty() || incomeString.isEmpty() || durationString.isEmpty() || pinCodeString.isEmpty()){
            answerLabel.setText("The information provided is insufficient.\nPlease complete all required fields!");
            return;
        }
        double amountDouble = Double.parseDouble(amountString);
        double incomeDouble = Double.parseDouble(incomeString);
        int durationInt = Integer.parseInt(durationString);
        int pinCodeInt = Integer.parseInt(pinCodeString);

        if(amountDouble <= 0 || incomeDouble <= 0 || durationInt <= 0 || pinCodeInt <= 0){
            answerLabel.setText("Invalid input. Please re-enter the details!");
            return;
        }

        double payBackAmount = amountDouble * 0.1 * durationInt / 12 + amountDouble;
        double monthlyPayBack = payBackAmount / durationInt;

        Connection connection = DriverManager.getConnection(HelloApplication.url, HelloApplication.username,
                HelloApplication.password);

        PreparedStatement preparedStatement1 = connection.prepareStatement("select pinCode from customers where username = ?");
        preparedStatement1.setString(1, LogIn.currentUser);
        ResultSet resultSet1 = preparedStatement1.executeQuery();
        if(resultSet1.next()){
            int pinCode = resultSet1.getInt("pinCode");
            if(pinCode != pinCodeInt){
                answerLabel.setText("Incorrect or missing PIN!");
                return;
            } else{
                answerLabel.setText("");
            }
            resultSet1.close();
        }

        PreparedStatement preparedStatement = connection.prepareStatement("select loan from customers where username = ?");
        preparedStatement.setString(1, LogIn.currentUser);
        ResultSet resultSet = preparedStatement.executeQuery();

        double loan = 0;
        if(resultSet.next()){
            loan = resultSet.getDouble("loan");
        }

        if(loan != 0){
            answerLabel.setText("You currently have an active loan.\n Please repay it before requesting a new one.");
        }
        else if(monthlyPayBack + 2000 > incomeDouble){
            answerLabel.setText("Loan request denied.\nPlease consider increasing the repayment duration.");
        }
        else{
                deductedField.setVisible(true);
                deductedField.setManaged(true);
                submitButton1.setVisible(true);
                submitButton1.setManaged(true);
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
    }



    @FXML
    private void submitMethod1(ActionEvent e) {
        String deductedAmount = deductedField.getText();
        if(deductedAmount.isEmpty()){
            answerLabel.setText("The information provided is insufficient.\nPlease complete all required fields!");
            return;
        }

        double amountDouble = Double.parseDouble(amountField.getText());
        int durationInt = Integer.parseInt(durationField.getText());
        double payBackAmount = amountDouble * 0.1 * durationInt / 12 + amountDouble;

        try {
            double deductedAmountInt = Double.parseDouble(deductedAmount);
            if(deductedAmountInt <= 0){
                answerLabel.setText("Invalid input. Please re-enter the details!");
            }
            Connection connection = DriverManager.getConnection(HelloApplication.url,
                    HelloApplication.username, HelloApplication.password);


            PreparedStatement preparedStatement = connection.prepareStatement("update customers set deducted_amount = ?" +
                    " where username = ?"
            );
            preparedStatement.setDouble(1, deductedAmountInt);
            preparedStatement.setString(2, LogIn.currentUser);
            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {

                PreparedStatement preparedStatement2= connection.prepareStatement("update customers set cash = cash + ? where username = ?");
                preparedStatement2.setDouble(1, amountDouble);
                preparedStatement2.setString(2, LogIn.currentUser);
                int rows2 = preparedStatement2.executeUpdate();

                PreparedStatement preparedStatement1 = connection.prepareStatement("update customers set loan = ? " +
                        "where username = ?");
                preparedStatement1.setDouble(1, payBackAmount);
                preparedStatement1.setString(2, LogIn.currentUser);
                preparedStatement1.executeUpdate();

                answerLabel.setText("Your loan has been approved, your money is on your account!");
                CreditToMainMenu(e);

                if(rows2 == 0) {
                    answerLabel.setText("Loan approved but cash not updated!");
                }

                PreparedStatement preparedStatement7 = connection.prepareStatement("insert into Transactions(sender, recipient, " +
                        "amount, type) values(?, ?, ?, ?) ");
                preparedStatement7.setString(1, LogIn.currentUser);
                preparedStatement7.setString(2, LogIn.currentUser);
                preparedStatement7.setDouble(3, amountDouble);
                preparedStatement7.setString(4, "Loan activated");

                int rows7 = preparedStatement7.executeUpdate();
                if(rows7 > 0) {
                    connection.commit();
                    preparedStatement7.close();
                }
                else {
                    connection.rollback();
                    answerLabel.setText("Transaction failed. Please try again.");
                }


            } else {
                answerLabel.setText("Error occurred!");
            }

            preparedStatement.close();
            connection.close();
        } catch (SQLException sql) {
            sql.printStackTrace();

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        deductedField.setVisible(false);
        deductedField.setManaged(false);
        submitButton1.setVisible(false);
        submitButton1.setManaged(false);

        visiblePinCode.setVisible(false);
        visiblePinCode.setManaged(false);
    }

    @FXML
    private void pinCodeVisibility(ActionEvent e){
        if(showPinCode.isSelected()){
            visiblePinCode.setText(pinCodePasswordField.getText());
            visiblePinCode.setVisible(true);
            visiblePinCode.setManaged(true);

            pinCodePasswordField.setVisible(false);
            pinCodePasswordField.setManaged(false);
        } else{
            pinCodePasswordField.setText(visiblePinCode.getText());
            visiblePinCode.setVisible(false);
            visiblePinCode.setManaged(false);

            pinCodePasswordField.setVisible(true);
            pinCodePasswordField.setManaged(true);
        }
    }
}
