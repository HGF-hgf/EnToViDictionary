package com.example.dictionaryy;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
public class TranslatorController {
    @FXML
    private TextArea textArea;
    @FXML
    private TextArea textArea1;
    private boolean isEnToVi = true;
    @FXML
    private Button translateButton;

    public TranslatorController() {
    }

    @FXML
    private void Initialize() {
        this.translateButton.setOnAction((event) -> {
            if (this.isEnToVi) {
                this.textArea1.setText(Translator.translateEnToVi(this.textArea.getText()));
            } else {
                this.textArea1.setText(Translator.translateViToEn(this.textArea.getText()));
            }

        });
    }

}
