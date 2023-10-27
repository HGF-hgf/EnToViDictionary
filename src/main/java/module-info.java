module com.example.dictionaryy {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.web;


    opens com.example.dictionaryy to javafx.fxml;
    exports com.example.dictionaryy;
}