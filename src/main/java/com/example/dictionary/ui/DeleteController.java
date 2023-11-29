package com.example.dictionary.ui;

import com.example.dictionary.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DeleteController {
    @FXML
    private Button commitButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField textField;

    @FXML
    public void setCommitButton(ActionEvent event){
        String word = textField.getText();
        if (Application.dictionary.delete(word)) {
            System.out.println("Deleted word");
        } else {
            System.out.println("Error deleting word");
        }
        Stage stage = (Stage) commitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void setCancelButton(ActionEvent event){
        System.out.println("Cancel");
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
