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
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class SignUp implements Initializable {
    @FXML
    private TextField usernameField1;
    @FXML
    private PasswordField passwordField1;
    @FXML
    private CheckBox femaleBox;
    @FXML
    private CheckBox maleBox;
    @FXML
    private ChoiceBox<String> citiesChoiceBox;
    @FXML
    private Button signUpButton1;
    @FXML
    private Label incorrectPassword1;
    @FXML
    private Label notAllInfo;
    @FXML
    private Label userAlreadyExists;
    @FXML
    private Label pinCodeLabel;
    @FXML
    private PasswordField pinCodeField;
    @FXML
    private PasswordField pinCodeRepeatField;
    @FXML
    private Button backButton;

    @FXML
    private CheckBox showPassword;
    @FXML
    private TextField visiblePassword;
    @FXML
    private CheckBox showPinCode;
    @FXML
    private TextField visiblePinCode;
    @FXML
    private CheckBox showPinCode1;
    @FXML
    private TextField visiblePinCode1;
    @FXML
    private Label signUpLabel;

    public static String currentStatus = "active";


    @FXML
    private void signUpToLogIn(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void signUpToWelcome(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Welcome.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void allInformation(ActionEvent e) throws IOException{
        String username = usernameField1.getText();
        String password;
        String gender = "";
        String pinCode;
        String repeatedPinCode;
        String city = citiesChoiceBox.getValue();

        if (passwordField1.isVisible()) {
            password = passwordField1.getText();
        } else {
            password = visiblePassword.getText();
        }

        if (pinCodeField.isVisible()) {
            pinCode = pinCodeField.getText();
        } else {
            pinCode = visiblePinCode.getText();
        }

        if (pinCodeRepeatField.isVisible()) {
            repeatedPinCode = pinCodeRepeatField.getText();
        } else {
            repeatedPinCode = visiblePinCode1.getText();
        }

        if (username.isEmpty() || password.isEmpty() || gender == null|| city == null || pinCode == null || repeatedPinCode == null){
            notAllInfo.setText("The information provided is insufficient.\nPlease complete all required fields!");
            userAlreadyExists.setText("");
            usernameField1.clear();
            passwordField1.clear();
            return;
        }
        if(!pinCode.equals(repeatedPinCode) || pinCode.isEmpty() || repeatedPinCode.isEmpty() || pinCode.length() != 4){
            notAllInfo.setText("An error occurred. Please reset your PIN.");
            userAlreadyExists.setText("");
            return;
        }

        if(maleBox.isSelected()){
            gender = maleBox.getText();
        }
        else if(femaleBox.isSelected()) {
            gender = femaleBox.getText();
        }


        if((password.length()<8) || (!password.contains("0") && !password.contains("1") && !password.contains("2") &&
                !password.contains("3") && !password.contains("4") && !password.contains("5") && !password.contains("6")
                && !password.contains("7") && !password.contains("8") && !password.contains("9"))){
            incorrectPassword1.setText("A valid password must be at least 8 characters long\nand contain at least one number.");
            return;
        }

        try {
            Connection connection = DriverManager.getConnection(HelloApplication.url, HelloApplication.username,
                    HelloApplication.password);

            PreparedStatement preparedStatement1 = connection.prepareStatement("select * from customers where username = ?");
            preparedStatement1.setString(1, username);
            ResultSet resultSet = preparedStatement1.executeQuery();
            if (resultSet.next()) {
                userAlreadyExists.setText("Username taken. Please choose another.");
                notAllInfo.setText("");
                usernameField1.clear();
                return;
            }

                PreparedStatement preparedStatement = connection.prepareStatement("insert into customers(username, password, " +
                        "gender, city, pinCode) values(?, ?, ?, ?, ?)");
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, gender);
                preparedStatement.setString(4, city);
                preparedStatement.setString(5, pinCode);

                int numberOfRows = preparedStatement.executeUpdate();
                if(numberOfRows > 0){
                    System.out.println("Account successfully created!");
                    signUpToLogIn(e);
                }
        } catch(SQLException sql){
            sql.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] cities = {"Tbilisi", "Rustavi", "Batumi", "Senaki", "Kutaisi", "Zugdidi", "Gori", "Poti", "Mtskheta"};
        citiesChoiceBox.getItems().addAll(cities);

        maleBox.setOnAction(e -> {
            if(maleBox.isSelected()) femaleBox.setSelected(false);
        });
        femaleBox.setOnAction(e -> {
            if(femaleBox.isSelected()) maleBox.setSelected(false);
        });

        visiblePassword.setVisible(false);
        visiblePassword.setManaged(false);
        visiblePinCode.setVisible(false);
        visiblePinCode.setManaged(false);
        visiblePinCode1.setVisible(false);
        visiblePinCode1.setManaged(false);
    }


    @FXML
    private void passwordVisibility(ActionEvent e){
        if(showPassword.isSelected()){
            visiblePassword.setText(passwordField1.getText());
            visiblePassword.setVisible(true);
            visiblePassword.setManaged(true);

            passwordField1.setVisible(false);
            passwordField1.setManaged(false);
        } else{
            passwordField1.setText(visiblePassword.getText());
            visiblePassword.setVisible(false);
            visiblePassword.setManaged(false);

            passwordField1.setVisible(true);
            passwordField1.setManaged(true);
        }
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

    @FXML
    private void pinCodeVisibility1(ActionEvent e){
        if(showPinCode1.isSelected()){
            visiblePinCode1.setText(pinCodeRepeatField.getText());
            visiblePinCode1.setVisible(true);
            visiblePinCode1.setManaged(true);

            pinCodeRepeatField.setVisible(false);
            pinCodeRepeatField.setManaged(false);
        } else{
            pinCodeRepeatField.setText(visiblePinCode1.getText());
            visiblePinCode1.setVisible(false);
            visiblePinCode1.setManaged(false);

            pinCodeRepeatField.setVisible(true);
            pinCodeRepeatField.setManaged(true);
        }
    }
}
