package com.example.dictionaryy;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Comparator;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HelloController implements Initializable {

    @FXML
    private TextField filterField = new TextField();
    @FXML
    private ListView<String> listView = new ListView<>();
    @FXML
    private TextArea words = new TextArea();

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToScene1(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("homepage.fxml")));
        stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToScene2(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("searchpage.fxml")));
        stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToScene3(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("gamepage.fxml")));
        stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    private final ObservableList<String> dataList = FXCollections.observableArrayList();
    private final ObservableList<Words> meaningList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connection connection;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
            Statement statement = connection.createStatement();
            //statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("drop table if exists person");
            statement.executeUpdate("create table person (word string, meaning string)");
            statement.executeUpdate("insert into person values(1, 'leo')");
            statement.executeUpdate("insert into person values(2, 'yui')");
            statement.executeUpdate("insert into person values(3, 'cac')");
            statement.executeUpdate("insert into person values(41, 'leo1')");
            statement.executeUpdate("insert into person values(31, 'leo2')");
            statement.executeUpdate("insert into person values(21, 'leo3')");

            ResultSet resultSet = statement.executeQuery("select * from person");
            while (resultSet.next()) {
                // read the result set
                String word = resultSet.getString("word");
                String meaning = resultSet.getString("meaning");
                dataList.add(word);
                meaningList.add(new Words(word, meaning));
            }
            listView.setVisible(false);

            FilteredList<String> filteredData = new FilteredList<>(dataList, b -> true);
            filterField.setOnKeyTyped(keyEvent -> {
                filteredData.setPredicate(words -> {
                    if (filterField.getText() == null || filterField.getText().isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = filterField.getText().toLowerCase();
                    return words.toLowerCase().contains(lowerCaseFilter);
                });
                listView.setVisible(!filterField.getText().isEmpty());
            });
            SortedList<String> sortedData = new SortedList<>(filteredData, Comparator.naturalOrder());
            listView.setItems(sortedData);
            listView.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
                meaningList.forEach(word -> {
                    if (word.getWord().equals(t1)) {
                        words.setText(word.getMeaning());
                    }
                });
                filterField.setText(t1);
                listView.setVisible(false);
            });

        } catch (SQLException e) {
            Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, e);
            e.getCause();
        }
    }
}
