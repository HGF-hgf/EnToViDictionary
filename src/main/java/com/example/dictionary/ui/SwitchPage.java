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

    /**
     * This method is called when the 'Search' button is clicked in the GUI.
     * It loads a new scene from the 'searchpage.fxml' file and sets it on the current stage.
     *
     * @param event The ActionEvent object representing the button click event.
     */
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

    /**
     * This method is called when the 'Translate' button is clicked in the GUI.
     * It loads a new scene from the 'GGTranslate.fxml' file and sets it on the current stage.
     *
     * @param event The ActionEvent object representing the button click event.
     */
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

    /**
     * This method is called when the 'Add Word' button is clicked in the GUI.
     * It loads a new scene from the 'AddWord.fxml' file and sets it on the current stage.
     *
     * @param event The ActionEvent object representing the button click event.
     */
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

    /**
     * This method is called when the 'Game' button is clicked in the GUI.
     * It loads a new scene from the 'GamePage.fxml' file and sets it on the current stage.
     *
     * @param event The ActionEvent object representing the button click event.
     */
    @FXML
    public void setGameButton(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Application.class.getResource("fxml/GamePage.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 850, 550);
            stage.setTitle("Game");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called when the 'Exit' button is clicked in the GUI.
     * It exits the platform and then exits the system.
     *
     * @param event The ActionEvent object representing the button click event.
     */
    @FXML
    public void setExitButton(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }
}

