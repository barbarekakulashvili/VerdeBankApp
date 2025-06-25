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

public class RecoverPassword implements Initializable {
    @FXML
    private Label mainLabel;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField pinCodeTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private PasswordField repeatPasswordTextField;
    @FXML
    private Button recoverButton;
    @FXML
    private Button backButton;

    @FXML
    private CheckBox showPassword;
    @FXML
    private TextField visiblePassword;
    @FXML
    private CheckBox showPassword1;
    @FXML
    private TextField visiblePassword1;
    @FXML
    private CheckBox showPinCode;
    @FXML
    private TextField visiblePinCode;

    @FXML
    private void RecoverPasswordToLogIn(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void recoverMethod(ActionEvent e) throws SQLException, IOException {
        String username = usernameTextField.getText();
        String pinCode = pinCodeTextField.getText();
        String newPassword = passwordTextField.getText();
        String newRepeatedPassword = repeatPasswordTextField.getText();

        if(username.isEmpty() || pinCode.isEmpty() || newPassword.isEmpty() || newRepeatedPassword.isEmpty()){
            errorLabel.setText("The information provided is insufficient.\nPlease complete all required fields!");
            return;
        }
        if(!newPassword.equals(newRepeatedPassword)){
            errorLabel.setText("Passwords do not match. Please re-enter them.");
            return;
        }
        if((newPassword.length() < 8 || !newPassword.contains("0") && !newPassword.contains("1") && !newPassword.contains("2") &&
                !newPassword.contains("3") && !newPassword.contains("4") && !newPassword.contains("5") && !newPassword.contains("6")
                && !newPassword.contains("7") && !newPassword.contains("8") && !newPassword.contains("9"))){
            errorLabel.setText("A valid password must be at least 8 characters long\nand contain at least one number.");
            return;
        }

        Connection connection = DriverManager.getConnection(HelloApplication.url, HelloApplication.username,
                HelloApplication.password);
        PreparedStatement preparedStatement = connection.prepareStatement("select * from customers where username = ? and pinCode = ?");
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, pinCode);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            PreparedStatement preparedStatement1 = connection.prepareStatement("update customers set password = ? where username = ? and " +
                    "pinCode = ?");
            preparedStatement1.setString(1, newPassword);
            preparedStatement1.setString(2, username);
            preparedStatement1.setString(3, pinCode);

            int amountOfRows = preparedStatement1.executeUpdate();
            if (amountOfRows > 0) {
                errorLabel.setText("Password reset successfully!");
                preparedStatement1.close();
                resultSet.close();
                preparedStatement.close();
                connection.close();
                RecoverPasswordToLogIn(e);
                return;
            } else errorLabel.setText("No account matches the provided username.");

            preparedStatement1.close();
        } else{
            errorLabel.setText("Invalid username or PIN. Please try again!");
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        visiblePassword.setVisible(false);
        visiblePassword.setManaged(false);
        visiblePassword1.setVisible(false);
        visiblePassword1.setManaged(false);
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
    private void passwordVisibility1(ActionEvent e){
        if(showPassword1.isSelected()){
            visiblePassword1.setText(repeatPasswordTextField.getText());
            visiblePassword1.setVisible(true);
            visiblePassword1.setManaged(true);

            repeatPasswordTextField.setVisible(false);
            repeatPasswordTextField.setManaged(false);
        } else{
            repeatPasswordTextField.setText(visiblePassword1.getText());
            visiblePassword1.setVisible(false);
            visiblePassword1.setManaged(false);

            repeatPasswordTextField.setVisible(true);
            repeatPasswordTextField.setManaged(true);
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

