package com.example.dictionary.ui;

import com.example.dictionary.Application;
import com.example.dictionary.api.TextToSpeech;
import com.example.dictionary.api.Translator;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class TextToSpeechController {
    @FXML
    private TextArea sourceText;
    @FXML
    private TextArea translatedText;
    @FXML
    private Button translateButton;
    @FXML
    private Button gameButton;
    @FXML
    private Button textToSpeechButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button textToSpeechsource;
    @FXML
    private Button switchLanguageButton;
    @FXML
    private Button searchButton;
    @FXML
    private Button addWordButton;
    @FXML
    private Label sourceLabel;
    @FXML
    private Label translatedLabel;
    @FXML
    private ImageView srcImg;
    @FXML
    private ImageView transImg;

    private boolean isEnToVi = true;

    public TextToSpeechController() {

    }

    @FXML
    private void initialize() {
        translatedText.setEditable(false);
    }


    @FXML
    public void translate() {
        String text = sourceText.getText();
        translatedText.setText(
                isEnToVi ? Translator.translateEnToVi(text) : Translator.translateViToEn(text)
        );
    }

    @FXML
    public void textToSpeech() {
        String text = translatedText.getText();

        if (isEnToVi) {
            TextToSpeech.soundViToEn(text);
        } else {
            TextToSpeech.soundEnToVi(text);
        }
    }

    @FXML
    void textToSpeechsource() {
        String text = sourceText.getText();
        if (isEnToVi) {
            TextToSpeech.soundEnToVi(text);
        } else {
            TextToSpeech.soundViToEn(text);
        }
    }

    @FXML
    public void switchLanguage() {
        isEnToVi = !isEnToVi;
        if (isEnToVi) {
            sourceText.setPromptText("Enter text");
            sourceLabel.setText("English");
            translatedLabel.setText("Tiếng Việt");
            srcImg.setImage(new Image(Objects.requireNonNull(Application.class.getResourceAsStream("icons/icons8_great_britain_48px_1.png"))));
            transImg.setImage(new Image(Objects.requireNonNull(Application.class.getResourceAsStream("icons/icons8_vietnam_48px.png"))));
        } else {
            sourceText.setPromptText("Nhập văn bản");
            sourceLabel.setText("Tiếng Việt");
            translatedLabel.setText("English");
            srcImg.setImage(new Image(Objects.requireNonNull(Application.class.getResourceAsStream("icons/icons8_vietnam_48px.png"))));
            transImg.setImage(new Image(Objects.requireNonNull(Application.class.getResourceAsStream("icons/icons8_great_britain_48px_1.png"))));
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

    @FXML
    public void setAddWordButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Application.class.getResource("fxml/AddWord.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 850, 550);
            stage.setTitle("Add Word");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void setGameButton(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Application.class.getResource("fxml/GamePage.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 616, 397);
            stage.setTitle("Game");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void setExitButton(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }
}
