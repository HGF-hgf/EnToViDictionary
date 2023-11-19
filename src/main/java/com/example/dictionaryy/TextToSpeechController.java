package com.example.dictionaryy;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static com.example.dictionaryy.SearchController.lightMode;

public class TextToSpeechController {
    @FXML private TextArea sourceText;
    @FXML private TextArea translatedText;
    @FXML private Button translateButton;
    @FXML private Button textToSpeechButton;
    @FXML private Button textToSpeechsource;
    @FXML private Button switchLanguageButton;
    private boolean isEnToVi = true;

    public TextToSpeechController() {}
    @FXML
    private void initialize(){
        setButtonIcon();
    }

    public void setButtonIcon(){
        String mode = "light";
        ImageView translateIcon = new ImageView("icon/translate-icon-" + mode + ".png");
        translateIcon.setFitHeight(18);
        translateIcon.setFitWidth(18);

        ImageView textToSpeechIcon = new ImageView("icon/voice-icon-" + mode + ".png");
        textToSpeechIcon.setFitHeight(18);
        textToSpeechIcon.setFitWidth(18);
        translateButton.setGraphic(translateIcon);
        textToSpeechButton.setGraphic(textToSpeechIcon);
    }

    @FXML
    public void translate(){
        String text = sourceText.getText();
        translatedText.setText(
                isEnToVi ? Translator.translateEnToVi(text) : Translator.translateViToEn(text)
        );
    }
    @FXML
    public void textToSpeech(){
       String text = translatedText.getText();
            if (isEnToVi) {
                TextToSpeech.soundViToEn(text);
            } else {
                TextToSpeech.soundEnToVi(text);
            }
    }

    @FXML void textToSpeechsource(){
        String text = sourceText.getText();
            if (isEnToVi) {
                TextToSpeech.soundEnToVi(text);
            } else {
                TextToSpeech.soundViToEn(text);
            }
    }

    @FXML
    public void switchLanguage(){
        isEnToVi = !isEnToVi;
        if (isEnToVi){
            sourceText.setPromptText("Enter text");
        }
        else {
            sourceText.setPromptText("Nhập văn bản");
        }
    }
}
