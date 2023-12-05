package com.example.dictionary.ui;

import com.example.dictionary.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MultipleChoiceHelper implements Initializable {
    @FXML
    private Button confirmBtn, returnBtn;

    /**
     * This method is called to initialize a controller after its root element has been completely processed.
     * It sets up the action handlers for the 'returnBtn' and 'confirmBtn' buttons.
     * The 'returnBtn' handler loads a new scene from the 'GamePage.fxml' file and sets it on the current stage.
     * The 'confirmBtn' handler loads a new scene from the 'MultipleChoiceGUI.fxml' file and sets it on the current stage.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        returnBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String path = "fxml/GamePage.fxml";
                try {
                    FXMLLoader loader = new FXMLLoader(Application.class.getResource(path));
                    Stage stage = (Stage) returnBtn.getScene().getWindow();
                    Scene scene = new Scene(loader.load());
                    stage.setScene(scene);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        confirmBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String path = "fxml/MultipleChoiceGUI.fxml";
                try {
                    FXMLLoader loader = new FXMLLoader(Application.class.getResource(path));
                    Stage stage = (Stage) returnBtn.getScene().getWindow();
                    Scene scene = new Scene(loader.load());
                    stage.setScene(scene);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
