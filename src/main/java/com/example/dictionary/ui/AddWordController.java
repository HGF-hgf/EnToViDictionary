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
import java.util.Objects;
import java.util.Optional;

import static com.example.dictionary.Application.dictionary;

public class AddWordController {
    Alerts alerts = new Alerts();
    @FXML private Button browseButton;
    @FXML private Button saveButton;
    @FXML private Button searchButton;
    @FXML private Button exitButton;
    @FXML private Button translateButton;
    @FXML private HTMLEditor htmlEditor;
    @FXML private TextField wordTextField;
    @FXML private Label successAlert;

    @FXML
    private void initialize(){
        // Set focus on the wordTextField
        successAlert.setVisible(false);
    }

    @FXML
    public void setSaveButton(ActionEvent event){
//        String word = wordTextField.getText();
//        byte[] meaning = htmlEditor.getHtmlText().getBytes(StandardCharsets.ISO_8859_1);
//        String meaningString = new String(meaning, StandardCharsets.UTF_8);
//        meaningString = meaningString.replace("<html dir=\"ltr\"><head></head><body contenteditable=\"true\">", "");
//        meaningString = meaningString.replace("</body></html>", "");
//        if (dictionary.insert(word, meaningString)) {
//            showSuccessAlert();
//            wordTextField.setText("");
//            htmlEditor.setHtmlText("");
//        } else {
//            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//            alert.setTitle("Error");
//            alert.setHeaderText("Word already exists");
//            alert.setResizable(false);
//            alert.setContentText("Do you want continue adding word?");
//            Optional<ButtonType> result = alert.showAndWait();
//            ButtonType buttonType = result.orElse(ButtonType.CANCEL);
//            if (buttonType == ButtonType.OK){
//                wordTextField.setText("");
//                htmlEditor.setHtmlText("");
//            }
//        }
        Alert alertConfirmation = alerts.alertConfirmation("Add word" ,
                "Bạn chắc chắn muốn thêm từ này?");
        Optional<ButtonType> option = alertConfirmation.showAndWait();
        // get data from input
        String target = wordTextField.getText().trim();
        String meaning = htmlEditor.getHtmlText().trim();

        if (option.get() == ButtonType.OK) {
            Words word = new Words(target, meaning);
            String w = dictionary.search("target");
            if (w.equals(target)) {
                // find index of word in dictionary

                // show confirmation alert
                Alert selectionAlert = alerts.alertConfirmation("This word already exists" ,
                        "Từ này đã tồn tại.\n" +
                                "Thay thế hoặc bổ sung nghĩa vừa nhập cho nghĩa cũ.");
                // custom button
                selectionAlert.getButtonTypes().clear();
                ButtonType replaceBtn = new ButtonType("Thay thế");
                selectionAlert.getButtonTypes().addAll(replaceBtn, ButtonType.CANCEL);
                Optional<ButtonType> selection = selectionAlert.showAndWait();

                if(selection.get() == replaceBtn) {
                    // replace old meaning, replace this with sqlite later
                    word.setMeaning(meaning);
                }
                if(selection.get() == ButtonType.CANCEL){
                    alerts.showAlertInfo("Information" , "Thay đổi không được công nhận.");
                }
            } else {
                dictionary.insert(target, meaning);
                // succeed
                showSuccessAlert();
            }
            // reset input
            saveButton.setDisable(true);
            wordTextField.setText("");
            htmlEditor.setHtmlText("");

        } else if (option.get() == ButtonType.CANCEL) {
            alerts.showAlertInfo("Information" , "Thay đổi không được công nhận.");
        }
    }

    @FXML
    public void setExitButton(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

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

    private void showSuccessAlert(){
        successAlert.setVisible(true);
        // automatic hide success alert
        Dictionary.setTimeout(() -> successAlert.setVisible(false) , 1500);
    }

}
