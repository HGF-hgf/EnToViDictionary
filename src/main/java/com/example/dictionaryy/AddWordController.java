package com.example.dictionaryy;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static com.example.dictionaryy.HelloApplication.dictionary;

public class AddWordController {
    @FXML private Button browseButton;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    @FXML private HTMLEditor htmlEditor;
    @FXML private TextField wordTextField;

    private AddWordService addWordService;
    @FXML
    private void initialize(){
        // Set focus on the wordTextField
    }

    @FXML
    public void setSaveButton(ActionEvent event){
        String word = wordTextField.getText();
        byte[] meaning = htmlEditor.getHtmlText().getBytes(StandardCharsets.ISO_8859_1);
        String meaningString = new String(meaning, StandardCharsets.UTF_8);
        meaningString = meaningString.replace("<html dir=\"ltr\"><head></head><body contenteditable=\"true\">", "");
        meaningString = meaningString.replace("</body></html>", "");
        if (dictionary.insert(word, meaningString)) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Word already exists");
            alert.setResizable(false);
            alert.setContentText("Do you want continue adding word?");
            Optional<ButtonType> result = alert.showAndWait();
            ButtonType buttonType = result.orElse(ButtonType.CANCEL);
            if (buttonType == ButtonType.OK){
                wordTextField.setText("");
                htmlEditor.setHtmlText("");
            } else {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();
            }
        }
    }

    @FXML
    public void chooseFile(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        String file = AssistController.chooseFile(stage);
    }

    @FXML
    public void setCancelButton(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
