package com.example.dictionaryy;


import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HelloController implements Initializable {

    @FXML
    private TextField filterField = new TextField();
    @FXML
    private ListView<String> listView = new ListView<>();
    @FXML
    private TextArea words = new TextArea();
    @FXML
    private WebView webView = new WebView();

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

// Use prepared statement to insert data into the person table
            String insertQuery = "insert into person (word, meaning) values (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, "hello");
            preparedStatement.setString(2, "<h1>accessary</h1><h3><i>/æk'sesəri/</i></h3><h2>danh từ,  (thường) số nhiều</h2><ul><li>đồ phụ tùng; vật phụ thuộc; đồ thêm vào</li><li>(pháp lý) kẻ tòng phạm, kẻ a tòng, kẻ đồng loã</li></ul><h2>tính từ</h2><ul><li>phụ, phụ vào, thêm vào</li><li>(pháp lý) a tòng, đồng loã</li></ul>");
            preparedStatement.executeUpdate();

//

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
            SortedList<String> historyList = new SortedList<>(filteredData, Comparator.naturalOrder());
            filterField.setOnKeyTyped(keyEvent -> {
                filteredData.setPredicate(words -> {
                    if (filterField.getText() == null || filterField.getText().isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = filterField.getText().toLowerCase();
                    if (words.toLowerCase().contains(lowerCaseFilter) && !filterField.getText().isEmpty()) {
                        listView.setVisible(true);
                    }

                    return words.toLowerCase().contains(lowerCaseFilter);
                });

                if (filterField.getText().isEmpty() || filterField.getText() == null) {
                    listView.setVisible(false);
                }

//                String currentText = filterField.getText();
//                if (!historyList.contains(currentText)) {
//                    historyList.add(currentText);
//                }

            });

            SortedList<String> sortedData = new SortedList<>(filteredData, Comparator.naturalOrder());
            listView.setPrefHeight(24 * sortedData.size());
            sortedData.addListener((ListChangeListener<String>) c -> {
                if (sortedData.isEmpty()) {
                    listView.setVisible(false);
                } else {
                    listView.setPrefHeight(24 * sortedData.size());
                }
            });
            listView.setItems(sortedData);
//            filterField.setOnMouseClicked(mouseEvent -> {
//                if (filterField.getText() == null || filterField.getText().isEmpty()) {
//                    if (!historyList.isEmpty()) {
//                        listView.setItems(historyList);
//                    } else {
//                        listView.setItems(sortedData);
//                    }
//                }
//            });
            WebEngine webEngine = webView.getEngine();
            webView.setVisible(false);
            StackPane root = new StackPane();
            root.getChildren().add(webView);

            listView.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
                meaningList.forEach(word -> {
                    if (word.getWord().equals(t1)) {
                        webEngine.loadContent(word.getMeaning());
                        webView.setVisible(true);
                     //   words.getContentBias(null);
                        words.setEditable(false);
                    }
                });
                filterField.setText(t1);
                listView.setVisible(false);
                if (filterField.getText() == null || filterField.getText().isEmpty()) {
                    filterField.clear();
                    words.setEditable(false);
                    words.clear();
                }
            });


        } catch (SQLException e) {
            Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, e);
            e.getCause();
        }
    }
}
