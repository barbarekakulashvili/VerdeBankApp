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

public class LogIn implements Initializable{
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button logInButton;
    @FXML
    private Label logInLabel;
    @FXML
    private Label notEnoughInfo;
    public static String currentUser;

    @FXML
    private Label recoverPasswordLabel;
    @FXML
    private Button recoverPasswordButton;
    @FXML
    private Button backButton;

    @FXML
    private CheckBox showPassword;
    @FXML
    private TextField visiblePassword;


    @FXML
    private void LogInButton(ActionEvent e) throws SQLException, IOException {
        String username1 = usernameField.getText();
        String password1;
        if (passwordField.isVisible()) {
            password1 = passwordField.getText();
        } else {
            password1 = visiblePassword.getText();
        }

        if(username1.isEmpty() || password1.isEmpty()){
            notEnoughInfo.setText("The information provided is insufficient.\nPlease complete all required fields!");
            return;
        }

        Connection connection = DriverManager.getConnection(HelloApplication.url, HelloApplication.username,
                HelloApplication.password);
        PreparedStatement preparedStatement = connection.prepareStatement("select * from customers where username = ? and password = ?");
        preparedStatement.setString(1, username1);
        preparedStatement.setString(2, password1);
        ResultSet resultSet = preparedStatement.executeQuery();

        if(username1.equals("admin1") || username1.equals("admin2") || username1.equals("admin3")){
            currentUser = username1;
            LogInToMainMenuAdmins(e);
        }
        if(resultSet.next()){
            currentUser = username1;
            LogInToMainMenu(e);
        } else{
            notEnoughInfo.setText("Invalid username or password. Please try again!");
            usernameField.clear();
            passwordField.clear();
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();
    }


    @FXML
    private void LogInToWelcome(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Welcome.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }


    @FXML
    private void LogInToMainMenu(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void LogInToMainMenuAdmins(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainMenuAdmins.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void LogInToRecoverPassword(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("recoverPassword.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        visiblePassword.setVisible(false);
        visiblePassword.setManaged(false);
    }

    @FXML
    private void passwordVisibility(ActionEvent e){
        if(showPassword.isSelected()){
            visiblePassword.setText(passwordField.getText());
            visiblePassword.setVisible(true);
            visiblePassword.setManaged(true);

            passwordField.setVisible(false);
            passwordField.setManaged(false);
        } else{
            passwordField.setText(visiblePassword.getText());
            visiblePassword.setVisible(false);
            visiblePassword.setManaged(false);

            passwordField.setVisible(true);
            passwordField.setManaged(true);
        }
    }
}
