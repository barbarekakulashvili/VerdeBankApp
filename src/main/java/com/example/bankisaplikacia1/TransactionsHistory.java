package com.example.bankisaplikacia1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class TransactionsHistory implements Initializable {
    @FXML
    private ScrollPane transactionsScrollPane;
    @FXML
    private VBox transactions;
    @FXML
    private Button backButton;


    @FXML
    private void HistoryToMainMenu(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Connection connection = DriverManager.getConnection(HelloApplication.url,HelloApplication.username
                    , HelloApplication.password);
            PreparedStatement preparedStatement = connection.prepareStatement("select * from Transactions where sender = ?");
            preparedStatement.setString(1, LogIn.currentUser);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String sender = resultSet.getString("sender");
                String recipient = resultSet.getString("recipient");
                double amount = resultSet.getDouble("amount");
                String type = resultSet.getString("type");

                Label label1 = new Label("From: " + sender + " | To: " + recipient + " | Amount: " + amount + " | Description: " + type);
                transactions.getChildren().add(label1);

            }
            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException sql) {
            sql.printStackTrace();
        }
    }
}
