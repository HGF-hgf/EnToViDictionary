package com.example.dictionary.ui;

import com.example.dictionary.Application;
import com.example.dictionary.core.Dictionary;
import com.example.dictionary.core.Words;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;

import static com.example.dictionary.Application.dictionary;

public class AddWordController extends SwitchPage {
    Alerts alerts = new Alerts();
    @FXML
    private Button browseButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button searchButton;
    @FXML
    private Button gameButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button translateButton;
    @FXML
    private HTMLEditor htmlEditor;
    @FXML
    private TextField wordTextField;
    @FXML
    private Label successAlert;

    /**
     * This method is called to initialize a controller after its root element has been completely processed.
     * It sets the 'successAlert' visibility to false.
     */
    @FXML
    private void initialize() {
        // Set focus on the wordTextField
        successAlert.setVisible(false);
    }

    /**
     * This method is called when the save button is clicked in the GUI.
     * It shows a confirmation alert and, if the user confirms, gets the input data and processes it.
     * If the entered word already exists in the dictionary, it shows another alert asking if the user wants to replace the existing meaning.
     * If the user confirms, it updates the word in the dictionary. If the user cancels, it shows an information alert.
     * If the entered word does not exist in the dictionary, it inserts the word into the dictionary and shows a success alert.
     * After processing the input, it resets the input fields and disables the save button for a short time.
     *
     * @param event The ActionEvent object representing the button click event.
     */
    @FXML
    public void setSaveButton(ActionEvent event) {
        Alert alertConfirmation = alerts.alertConfirmation("Add word",
                "Bạn chắc chắn muốn thêm từ này?");
        Optional<ButtonType> option = alertConfirmation.showAndWait();
        // get data from input
        String target = wordTextField.getText();
        byte[] pText = htmlEditor.getHtmlText().getBytes(StandardCharsets.UTF_8);
        String meaning = new String(pText, StandardCharsets.UTF_8);
        meaning =
                meaning.replace(
                        "<html dir=\"ltr\"><head></head><body contenteditable=\"true\">", "");
        meaning = meaning.replace("</body></html>", "");

        if (option.get() == ButtonType.OK) {
            Words word = new Words(target, meaning);
            String w = dictionary.search(target);
            if (!w.equals("<h1 style=\"text-align: center;\">No word found</h1>")) {
                // find index of word in dictionary

                // show confirmation alert
                Alert selectionAlert = alerts.alertConfirmation("This word already exists",
                        "Từ này đã tồn tại.\n" +
                                "Bạn có muốn thay thế nghĩa cũ bằng nghĩa mới không?");
                // custom button
                selectionAlert.getButtonTypes().clear();
                ButtonType replaceBtn = new ButtonType("Thay thế");
                selectionAlert.getButtonTypes().addAll(replaceBtn, ButtonType.CANCEL);
                Optional<ButtonType> selection = selectionAlert.showAndWait();

                if (selection.get() == replaceBtn) {
                    // replace old meaning, replace this with sqlite later
                    Application.dictionary.update(target, meaning);
                }
                if (selection.get() == ButtonType.CANCEL) {
                    alerts.showAlertInfo("Information", "Thay đổi không được công nhận.");
                }
            } else {
                dictionary.insert(target, meaning);
                // succeed
                showSuccessAlert();
            }
            // reset input
            saveButton.setDisable(true);
            Dictionary.setTimeout(() -> saveButton.setDisable(false), 1500);
            wordTextField.setText("");
            htmlEditor.setHtmlText("");

        } else if (option.get() == ButtonType.CANCEL) {
            alerts.showAlertInfo("Information", "Thay đổi không được công nhận.");
        }
    }


    /**
     * This method shows a success alert in the GUI.
     * It sets the 'successAlert' visibility to true, and then sets it back to false after a short delay.
     */
    private void showSuccessAlert() {
        successAlert.setVisible(true);
        // automatic hide success alert
        Dictionary.setTimeout(() -> successAlert.setVisible(false), 1500);
    }

}
