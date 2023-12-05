package com.example.dictionary.ui;

import com.example.dictionary.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class GamePageController implements Initializable {
    @FXML
    private Button btnHangman;
    @FXML
    private Button btnMultipleChoice;
    @FXML
    private Button btnReturn;

    /**
     * This method is called to initialize a controller after its root element has been completely processed.
     * It sets up the action handlers for the 'btnReturn', 'btnHangman', and 'btnMultipleChoice' buttons.
     * When each button is clicked, it loads a new scene from the corresponding FXML file and sets it on the current stage.
     * If there are any errors during this process, it prints the stack trace.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btnReturn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    FXMLLoader loader = new FXMLLoader(Application.class.getResource("fxml/searchpage.fxml"));
                    Stage stage = (Stage) btnReturn.getScene().getWindow();
                    Scene scene = new Scene(loader.load());
                    stage.setScene(scene);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btnHangman.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    FXMLLoader loader = new FXMLLoader(Application.class.getResource("fxml/HangMan.fxml"));
                    Stage stage = (Stage) btnHangman.getScene().getWindow();
                    Scene scene = new Scene(loader.load());
                    stage.setScene(scene);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnMultipleChoice.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    FXMLLoader loader = new FXMLLoader(Application.class.getResource("fxml/MultipleChoiceHelper.fxml"));
                    Stage stage = (Stage) btnMultipleChoice.getScene().getWindow();
                    Scene scene = new Scene(loader.load());
                    stage.setScene(scene);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
