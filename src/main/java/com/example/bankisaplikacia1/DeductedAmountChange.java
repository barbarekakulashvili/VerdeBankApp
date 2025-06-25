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

public class DeductedAmountChange implements Initializable {

    @FXML
    private Label mainLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private PasswordField pinCodeTextField;
    @FXML
    private Button changeButton;
    @FXML
    private Button backButton;
    @FXML
    private Label errorLabel;
    @FXML
    private CheckBox showPassword;
    @FXML
    private TextField visiblePassword;
    @FXML
    private CheckBox showPinCode;
    @FXML
    private TextField visiblePinCode;
    @FXML
    private TextField deductedAmountField;


    @FXML
    private void changeDeductedAmount(ActionEvent e){
        String username = usernameTextField.getText();

        String password;
        if (passwordTextField.isVisible()) {
            password = passwordTextField.getText();
        } else {
            password = visiblePassword.getText();
        }

        String pinCodeString;
        if (pinCodeTextField.isVisible()) {
            pinCodeString = pinCodeTextField.getText();
        } else {
            pinCodeString = visiblePinCode.getText();
        }


        String deductedAmountString = deductedAmountField.getText();

        if(username.isEmpty() || password.isEmpty() || pinCodeString.isEmpty() || deductedAmountString.isEmpty()){
            errorLabel.setText("The information provided is insufficient.\nPlease complete all required fields!");
            return;
        }

        if (!username.equals(LogIn.currentUser)) {
            errorLabel.setText("Invalid username. Please re-enter!");
            return;
        }


        double deductedAmountDouble;
        int pinCode;
        try {
            deductedAmountDouble = Double.parseDouble(deductedAmountString);
            pinCode = Integer.parseInt(pinCodeString);
            if(deductedAmountDouble <= 0 || pinCode <= 0){
                errorLabel.setText("Invalid input. Please re-enter the details!");
                return;
            }
        } catch (NumberFormatException ex) {
            errorLabel.setText("PIN and amount must be valid numbers.");
            return;
        }

      try{
          Connection connection = DriverManager.getConnection(HelloApplication.url, HelloApplication.username,
                  HelloApplication.password);
          PreparedStatement preparedStatement = connection.prepareStatement("select password, pinCode,deducted_amount from customers where username = ?");
          preparedStatement.setString(1, LogIn.currentUser);
          ResultSet resultSet = preparedStatement.executeQuery();
          if(resultSet.next()){
              String password1 = resultSet.getString("password");
              int pinCode1 = resultSet.getInt("pinCode");
              if(!password1.equals(password) || pinCode1 != pinCode){
                  errorLabel.setText("Password or PIN is incorrect. Please re-enter.");
                  passwordTextField.clear();
                  pinCodeTextField.clear();
              } else{
                  double deducted_amount = resultSet.getDouble("deducted_amount");
                  if(deducted_amount == 0) {
                      errorLabel.setText("You do not have a loan!");
                  }
                  else{
                      if(deducted_amount == deductedAmountDouble) {
                          errorLabel.setText("Your deducted amount already equals " + deducted_amount);
                      } else{
                          PreparedStatement preparedStatement1 = connection.prepareStatement("update customers set deducted_amount = ? where username = ?");
                          preparedStatement1.setDouble(1, deductedAmountDouble);
                          preparedStatement1.setString(2, LogIn.currentUser);
                           preparedStatement1.executeUpdate();
                           errorLabel.setText("Deducted amount successfully changed.");
                      }

                  }
              }
          }

      }catch(SQLException sql){
          sql.printStackTrace();
      }
    }


    @FXML
    private void DeductedAmountChangeToMainMenu(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        visiblePassword.setVisible(false);
        visiblePassword.setManaged(false);
        visiblePinCode.setVisible(false);
        visiblePinCode.setManaged(false);
    }

    @FXML
    private void passwordVisibility(ActionEvent e){
        if(showPassword.isSelected()){
            visiblePassword.setText(passwordTextField.getText());
            visiblePassword.setVisible(true);
            visiblePassword.setManaged(true);

            passwordTextField.setVisible(false);
            passwordTextField.setManaged(false);
        } else{
            passwordTextField.setText(visiblePassword.getText());
            visiblePassword.setVisible(false);
            visiblePassword.setManaged(false);

            passwordTextField.setVisible(true);
            passwordTextField.setManaged(true);
        }
    }

    @FXML
    private void pinCodeVisibility(ActionEvent e){
        if(showPinCode.isSelected()){
            visiblePinCode.setText(pinCodeTextField.getText());
            visiblePinCode.setVisible(true);
            visiblePinCode.setManaged(true);

            pinCodeTextField.setVisible(false);
            pinCodeTextField.setManaged(false);
        } else{
            pinCodeTextField.setText(visiblePinCode.getText());
            visiblePinCode.setVisible(false);
            visiblePinCode.setManaged(false);

            pinCodeTextField.setVisible(true);
            pinCodeTextField.setManaged(true);
        }
    }
}
