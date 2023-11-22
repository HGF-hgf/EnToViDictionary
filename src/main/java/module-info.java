module com.example.dictionaryy {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.web;

    requires org.xerial.sqlitejdbc;
    requires jlayer;
    requires org.jsoup;
    opens com.example.dictionaryy to javafx.fxml;
    exports com.example.dictionaryy;
}