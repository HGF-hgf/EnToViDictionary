package com.example.dictionaryy;

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

import static com.example.dictionaryy.SearchController.lightMode;

public class TextToSpeechController {
    @FXML
    private TextArea sourceText;
    @FXML
    private TextArea translatedText;
    @FXML
    private Button translateButton;
    @FXML
    private Button textToSpeechButton;
    @FXML
    private Button textToSpeechsource;
    @FXML
    private Button switchLanguageButton;
    @FXML
    private Button searchButton;
    @FXML
    private Label sourceLabel;
    @FXML
    private Label translatedLabel;
    private boolean isEnToVi = true;

    public TextToSpeechController() {

    }

    @FXML
    private void initialize() {
        setButton();
    }

    public void setButton() {
        String mode = "light";

        // translate icon
        Image translateImage = new Image(getClass().getResourceAsStream("icon/translate-icon-" + mode + ".png"));
        ImageView translateIcon = new ImageView(translateImage);
        translateIcon.setFitHeight(25);
        translateIcon.setFitWidth(25);
        // voice icon
        Image voiceImage = new Image(getClass().getResourceAsStream("icon/voice-icon-" + mode + ".png"));
        ImageView voiceIcon = new ImageView(voiceImage);
        voiceIcon.setFitHeight(25);
        voiceIcon.setFitWidth(25);
        ImageView voiceIcon1 = new ImageView(voiceImage);
        voiceIcon1.setFitHeight(25);
        voiceIcon1.setFitWidth(25);
        // switch language icon
        Image switchLanguageImage = new Image(getClass().getResourceAsStream("icon/alter-icon-" + mode + ".png"));
        ImageView switchLanguageIcon = new ImageView(switchLanguageImage);
        switchLanguageIcon.setFitHeight(25);
        switchLanguageIcon.setFitWidth(25);

        // set icon for buttons
        translateButton.setGraphic(translateIcon);
        textToSpeechsource.setGraphic(voiceIcon1);
        textToSpeechButton.setGraphic(voiceIcon);
        switchLanguageButton.setGraphic(switchLanguageIcon);
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
        } else {
            sourceText.setPromptText("Nhập văn bản");
            sourceLabel.setText("Tiếng Việt");
            translatedLabel.setText("English");
        }
    }
    @FXML
    public void setSearchButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/searchpage.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1405, 850);
            stage.setTitle("Dictionary");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
