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


public class TextToSpeechController extends SwitchPage {
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

    /**
     * This method is called to initialize a controller after its root element has been completely processed.
     * It sets the 'translatedText' field to be non-editable.
     */
    @FXML
    private void initialize() {
        translatedText.setEditable(false);
    }

    /**
     * This method is called to translate the text from the 'sourceText' field.
     * It splits the text into lines and translates each line.
     * The translation direction is determined by the 'isEnToVi' flag.
     * The translated text is then set on the 'translatedText' field.
     */
    @FXML
    public void translate() {
        String text = sourceText.getText();
        String[] lines = text.split("\n");
        for (int i = 0; i < lines.length; i++) {
            lines[i] = isEnToVi ? Translator.translateEnToVi(lines[i]) : Translator.translateViToEn(lines[i]);
        }
        translatedText.setText(String.join("\n", lines));
    }

    /**
     * This method is called to play the sound of the text from the 'translatedText' field.
     * It gets the text from the 'translatedText' field and plays the sound.
     * The translation direction is determined by the 'isEnToVi' flag.
     */
    @FXML
    public void textToSpeech() {
        String text = translatedText.getText();

        if (isEnToVi) {
            TextToSpeech.soundViToEn(text);
        } else {
            TextToSpeech.soundEnToVi(text);
        }
    }

    /**
     * This method is called to convert the text from the 'sourceText' field to speech.
     * The language of the speech is determined by the 'isEnToVi' flag.
     */
    @FXML
    void textToSpeechsource() {
        String text = sourceText.getText();
        if (isEnToVi) {
            TextToSpeech.soundEnToVi(text);
        } else {
            TextToSpeech.soundViToEn(text);
        }
    }

    /**
     * This method is called to switch the translation direction.
     * It changes the 'isEnToVi' flag and updates the UI accordingly.
     */
    @FXML
    public void switchLanguage() {
        isEnToVi = !isEnToVi;
        if (isEnToVi) {
            sourceText.setPromptText("Enter text");
            sourceLabel.setText("English");
            translatedLabel.setText("Tiếng Việt");
        } else {
            sourceText.setPromptText("Nhập văn bản");
            sourceLabel.setText("Tiếng Việt");
            translatedLabel.setText("English");
        }
    }
}
