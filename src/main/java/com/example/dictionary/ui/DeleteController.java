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

    /**
     * This method is called when the commit button is clicked in the GUI.
     * It gets the text from a text field and attempts to delete the corresponding word from the dictionary.
     * If the word is successfully deleted, it prints a confirmation message.
     * If the word cannot be deleted (for example, if it does not exist in the dictionary), it prints an error message.
     * After attempting to delete the word, it closes the window.
     *
     * @param event The ActionEvent object representing the button click event.
     */
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

    /**
     * This method is called when the cancel button is clicked in the GUI.
     * It closes the window.
     *
     * @param event The ActionEvent object representing the button click event.
     */
    @FXML
    public void setCancelButton(ActionEvent event){
        System.out.println("Cancel");
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
