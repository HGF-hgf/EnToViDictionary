package com.example.dictionary.ui;

import com.example.dictionary.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SwitchPage {

    @FXML
    public void setSearchButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Application.class.getResource("fxml/searchpage.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 850, 550);
            stage.setTitle("Dictionary");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void setTranslateButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Application.class.getResource("fxml/GGTranslate.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 850, 550);
            stage.setTitle("Google Translate");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void setAddWordButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Application.class.getResource("fxml/AddWord.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 850, 550);
            stage.setTitle("Add Word");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void setGameButton(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Application.class.getResource("fxml/GamePage.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 616, 397);
            stage.setTitle("Game");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void setExitButton(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }
}

