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
    private  static String editWord;
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

    @FXML
    private void initialize(){
        wordLabel.setText(editWord);
        String meaning = Application.dictionary.search(editWord);
        htmlEditor.setHtmlText(meaning);
    }

    @FXML
    public void setSaveButton(ActionEvent event){
        byte[] meaning = htmlEditor.getHtmlText().getBytes(StandardCharsets.UTF_8);
        String meaningString = new String(meaning, StandardCharsets.UTF_8);
        meaningString = meaningString.replace("<html dir=\"ltr\"><head></head><body contenteditable=\"true\">", "");
        meaningString = meaningString.replace("</body></html>", "");
        meaningString = meaningString.replace("\"", "'");
        if (Application.dictionary.update(editWord, meaningString)) {
            System.out.println("Updated word");
        } else {
            System.out.println("Error updating word");
        }
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void setCancelButton(ActionEvent event){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
