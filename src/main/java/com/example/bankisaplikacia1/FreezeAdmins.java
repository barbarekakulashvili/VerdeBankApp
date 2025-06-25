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

public class FreezeAdmins implements Initializable {

    @FXML
    private Label mainLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private PasswordField pinCodeTextField;
    @FXML
    private Button freezeButton;
    @FXML
    private Button backButton;
    @FXML
    private TextField adminsUsernameField;
    @FXML
    private Label errorLabel;

    @FXML
    private CheckBox showPassword;
    @FXML
    private CheckBox showPinCode;
    @FXML
    private TextField visiblePassword;
    @FXML
    private TextField visiblePinCode;


    @FXML
    private void CardFreezeToMainMenuAdmins(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainMenuAdmins.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }


    @FXML
    private void freezeAdminsMethod(ActionEvent e){
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        String pinCode = pinCodeTextField.getText();
        String adminsUsername = adminsUsernameField.getText();

        if(username.isEmpty() || password.isEmpty() || pinCode.isEmpty() || adminsUsername.isEmpty()){
            errorLabel.setText("The information provided is insufficient.\nPlease complete all required fields!");
            return;
        }
        try {
            Connection connection = DriverManager.getConnection(HelloApplication.url, HelloApplication.username,
                    HelloApplication.password);
            PreparedStatement preparedStatement1 = connection.prepareStatement("select * from admins where username = ? and password = ? and code = ?");
            preparedStatement1.setString(1, adminsUsername);
            preparedStatement1.setString(2, password);
            preparedStatement1.setString(3, pinCode);
            ResultSet resultSet1 = preparedStatement1.executeQuery();

            if (!resultSet1.next()) {
                errorLabel.setText("Admin's information is incorrect. Please try again.");
                resultSet1.close();
                preparedStatement1.close();
                return;
            }

            PreparedStatement preparedStatement = connection.prepareStatement("select * from customers where username = ?");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String status = resultSet.getString("status");
                SignUp.currentStatus = status;
                if (status.equals("frozen")) {
                    errorLabel.setText("Card is already locked.");
                    resultSet.close();
                    preparedStatement.close();
                    resultSet1.close();
                    preparedStatement1.close();
                    connection.close();
                    return;
                } else {
                    LogIn.currentUser = username;
                    PreparedStatement preparedStatement2 = connection.prepareStatement(
                            "update customers set status = ? where username = ?"
                    );
                    preparedStatement2.setString(1, "frozen");
                    preparedStatement2.setString(2, username);
                    int rows = preparedStatement2.executeUpdate();

                    if (rows > 0) {
                        LogIn.currentUser = username;
                        errorLabel.setText("Card has been successfully locked!");
                    } else {
                        errorLabel.setText("An error has occurred.");
                    }

                    preparedStatement2.close();

                    errorLabel.setText("Card has been successfully locked!");
                }

            } else{
                errorLabel.setText("No account matches the provided username.");
                usernameTextField.clear();
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch(SQLException sql){
            sql.printStackTrace();
        }

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
