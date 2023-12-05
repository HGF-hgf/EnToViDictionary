package com.example.dictionary.ui;

import com.example.dictionary.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

import java.nio.charset.StandardCharsets;

public class UpdateWordController {
    private static String editWord;
    @FXML
    private HTMLEditor htmlEditor;
    @FXML
    private Label wordLabel;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    public static void setEditWord(String editWord) {
        UpdateWordController.editWord = editWord;
    }

    /**
     * This method is called to initialize a controller after its root element has been completely processed.
     * It sets the 'wordLabel' text to the 'editWord' and the 'htmlEditor' text to the meaning of the 'editWord'.
     */
    @FXML
    private void initialize() {
        wordLabel.setText(editWord);
        String meaning = Application.dictionary.search(editWord);
        htmlEditor.setHtmlText(meaning);
    }

    /**
     * This method is called when the 'Save' button is clicked in the GUI.
     * It gets the HTML text from the 'htmlEditor', removes the HTML tags, and replaces double quotes with single quotes.
     * It then tries to update the word in the dictionary with the new meaning.
     * If the update is successful, it prints a success message; otherwise, it prints an error message.
     * Finally, it closes the window.
     *
     * @param event The ActionEvent object representing the button click event.
     */
    @FXML
    public void setSaveButton(ActionEvent event) {
        // Get the HTML text from the htmlEditor, convert it to bytes using UTF-8 encoding, and store it in the 'meaning' variable.
        byte[] meaning = htmlEditor.getHtmlText().getBytes(StandardCharsets.UTF_8);

        // Convert the bytes in the 'meaning' variable to a string using UTF-8 encoding, and store it in the 'meaningString' variable.
        String meaningString = new String(meaning, StandardCharsets.UTF_8);

        // Replace the HTML tags in the 'meaningString' variable with empty strings. (in the beginning of the string)
        meaningString = meaningString.replace("<html dir=\"ltr\"><head></head><body contenteditable=\"true\">", "");

        // Replace the HTML tags in the 'meaningString' variable with empty strings. (in the end of the string)
        meaningString = meaningString.replace("</body></html>", "");

        // Replace the double quotes in the 'meaningString' variable with single quotes.
        meaningString = meaningString.replace("\"", "'");

        // Try to update the word in the dictionary.
        if (Application.dictionary.update(editWord, meaningString)) {
            System.out.println("Updated word");
        } else {
            System.out.println("Error updating word");
        }

        // Close the window.
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    /**
     * This method is called when the 'Cancel' button is clicked in the GUI.
     * It closes the window.
     *
     * @param event The ActionEvent object representing the button click event.
     */
    @FXML
    public void setCancelButton(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
