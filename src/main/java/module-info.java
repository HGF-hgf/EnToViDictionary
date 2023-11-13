module com.example.dictionaryy {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.web;
    requires google.cloud.translate;

    requires google.cloud.core;
    requires com.google.auth;
    requires com.google.auth.oauth2;
    opens com.example.dictionaryy to javafx.fxml;
    exports com.example.dictionaryy;
}