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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        returnBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle( ActionEvent event ) {
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
            public void handle( ActionEvent event ) {
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
