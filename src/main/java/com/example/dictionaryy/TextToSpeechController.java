package com.example.dictionaryy;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    private boolean isEnToVi = true;

    public TextToSpeechController() {
    }

    @FXML
    private void initialize() {
        setButtonIcon();
    }

    public void setButtonIcon() {
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
        // switch language icon
        Image switchLanguageImage = new Image(getClass().getResourceAsStream("icon/alter-icon-" + mode + ".png"));
        ImageView switchLanguageIcon = new ImageView(switchLanguageImage);
        switchLanguageIcon.setFitHeight(25);
        switchLanguageIcon.setFitWidth(25);

        // set icon for buttons
        translateButton.setGraphic(translateIcon);
        textToSpeechButton.setGraphic(voiceIcon);
        switchLanguageButton.setGraphic(switchLanguageIcon);
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
        } else {
            sourceText.setPromptText("Nhập văn bản");
        }
    }
}
