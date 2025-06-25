module com.example.bankisaplikacia1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.example.bankisaplikacia1 to javafx.fxml;
    exports com.example.bankisaplikacia1;
}