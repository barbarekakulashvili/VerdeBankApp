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

public class Unfreeze implements Initializable {

    @FXML
    private Label mainLabel;
    @FXML
    private Label questionLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private PasswordField pinCodeTextField;
    @FXML
    private Button unfreezeButton;
    @FXML
    private Button backButton;
    @FXML
    private CheckBox sureCheckBox;
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
    private void CardUnfreezeToMainMenu(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void unfreezeMethod(ActionEvent e){
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        String pinCode = pinCodeTextField.getText();

        if(username.isEmpty() || password.isEmpty() || pinCode.isEmpty()){
            errorLabel.setText("The information provided is insufficient.\nPlease complete all required fields!");
            return;
        }
        try{
            Connection connection = DriverManager.getConnection(HelloApplication.url, HelloApplication.username,
                    HelloApplication.password);
            PreparedStatement preparedStatement = connection.prepareStatement("select * from customers where username = ? and password = ? and pinCode = ?");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, pinCode);
            ResultSet resultSet = preparedStatement.executeQuery();


            if(resultSet.next()){
                String status = resultSet.getString("status");
                SignUp.currentStatus = status;
                if(status.equals("active")) {
                    errorLabel.setText("Your card is already active!");
                    return;
                } else {
                    LogIn.currentUser = username;
                    questionLabel.setVisible(true);
                    questionLabel.setManaged(true);
                    sureCheckBox.setVisible(true);
                    sureCheckBox.setManaged(true);
                }

            } else{
                errorLabel.setText("Username, password, or PIN is incorrect.");
                usernameTextField.clear();
                passwordTextField.clear();
                pinCodeTextField.clear();
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch(SQLException sql){
            sql.printStackTrace();
        }

    }

    @FXML
    private void sureCheckBoxMethod(ActionEvent e) throws SQLException, IOException {
        String username = usernameTextField.getText();
        Connection connection = DriverManager.getConnection(HelloApplication.url, HelloApplication.username,
                HelloApplication.password);
        if(sureCheckBox.isSelected()){

            if(SignUp.currentStatus.equals("active")){
                errorLabel.setText("Your card is already active!");
            }
            else{
                PreparedStatement preparedStatement1 = connection.prepareStatement("update customers set status = ? where username = ?");
                preparedStatement1.setString(1, "active");
                preparedStatement1.setString(2, username);
                SignUp.currentStatus = "active";
                int rows = preparedStatement1.executeUpdate();
                if(rows > 0){
                    CardUnfreezeToMainMenu(e);
                }
                preparedStatement1.close();
            }
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        questionLabel.setVisible(false);
        questionLabel.setManaged(false);
        sureCheckBox.setVisible(false);
        sureCheckBox.setManaged(false);

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
