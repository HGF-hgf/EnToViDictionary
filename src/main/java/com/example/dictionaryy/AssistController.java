package com.example.dictionaryy;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class AssistController {
    static final FileChooser fileChooser = new FileChooser();
    static final DirectoryChooser directoryChooser = new DirectoryChooser();

    public static String chooseFile(Stage stage){
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            return file.getAbsolutePath();
        }
        return null;
    }

    public static String chooseDirectory(Stage stage){
        File file = directoryChooser.showDialog(stage);
        if (file != null) {
            return file.getAbsolutePath();
        }
        return null;
    }
}
