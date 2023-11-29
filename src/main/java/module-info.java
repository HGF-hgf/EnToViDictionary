module com.example.dictionary {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.web;

    requires org.xerial.sqlitejdbc;
    requires jlayer;
    requires org.jsoup;
    opens com.example.dictionary to javafx.fxml;
    exports com.example.dictionary;
    exports com.example.dictionary.core;
    opens com.example.dictionary.core to javafx.fxml;
    exports com.example.dictionary.ui;
    opens com.example.dictionary.ui to javafx.fxml;
    exports com.example.dictionary.api;
    opens com.example.dictionary.api to javafx.fxml;
}