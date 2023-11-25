package com.example.dictionaryy;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import javafx.scene.Node;
import org.w3c.dom.Document;

import static com.example.dictionaryy.HelloApplication.dictionary;


public class SearchController {
    public static boolean lightMode = true;
    private String latestWord = "";
    @FXML
    private TextField searchField;
    @FXML
    private WebView webView;
    @FXML
    private ListView<String> listView;

    // the last selected index of the list view
    private int lastSelectedIndex = 0;
    private Image historyIcon;
    @FXML
    private Button addWordButton;
    @FXML
    private Button deleteWordButton;
    @FXML
    private Button editWordButton;
    @FXML
    private Button translateButton;
    @FXML
    private Button toggleModeButton;
    @FXML
    private Button textToSpeechButton;

    public SearchController() {
    }
    public static boolean isLightMode() {
        return lightMode;
    }

    public static void toggleMode() {
        lightMode = !lightMode;
    }

    @FXML
    public void initialize() {
        Platform.runLater(() -> searchField.requestFocus());
        setButton();
        takeHistoryIcon(isLightMode());
        takeSearchList();
    }

    public void setButton() {
        textToSpeechButton.setVisible(true);
        if (searchField.getText().isEmpty()) {
            textToSpeechButton.setVisible(false);
        } else {
            textToSpeechButton.setVisible(true);
        }
        Image voiceImage = new Image(getClass().getResourceAsStream("icon/voice-icon-" + "light" + ".png"));
        ImageView voiceIcon = new ImageView(voiceImage);
        voiceIcon.setFitHeight(25);
        voiceIcon.setFitWidth(25);
        textToSpeechButton.setGraphic(voiceIcon);
    }

    @FXML
    public void changeFocus(KeyEvent key) {
        // if the user presses the down arrow key, change the focus to the list view
        if (key.getCode() == KeyCode.DOWN) {
            listView.requestFocus();
            // if the list view is not empty, select the first item
            if (!listView.getItems().isEmpty()) {
                listView.getSelectionModel().select(0);
            }
        }
    }

    private void takeHistoryIcon(boolean mode) {
        try {
            historyIcon = new Image(new FileInputStream("/home/hoang/java/dictionaryy/src/main/resources/com/example/dictionaryy/icon/history-icon-" + (mode ? "light" : "dark") + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void takeSearchList() {
        listView.setVisible(true);
        listView.getItems().clear();
        if (searchField.getText().isEmpty()) {
            listView.setVisible(false);
        }
        String word = searchField.getText();
        ArrayList<String> searchedWords = Trie.search(word);
        ArrayList<String> history = History.getHistory();
        for (int i = history.size() - 1; i >= 0; --i) {
            if (word.isEmpty() || history.get(i).startsWith(word)) {
                listView.getItems().add("*" + history.get(i));
            }
        }
        for (String w : searchedWords) {
            if (!listView.getItems().contains("*" + w)) {
                listView.getItems().add(w);
            }

        }
        if (listView.getItems().isEmpty()) {
            listView.setVisible(false);
        }
        if (!searchField.getText().equals(latestWord) || searchField.getText().isEmpty()) {
            webView.getEngine().loadContent("");
            return;
        }
        //  listView.setPrefHeight(listView.getItems().size() * listView.getHeight());
        listView.setCellFactory(
                new Callback<>() {
                    @Override
                    public ListCell<String> call(ListView<String> param) {
                        return new ListCell<>() {
                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (item == null || empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else if (item.charAt(0) != '*') {
                                    setGraphic(null);
                                    setText(item);
                                    setFont(Font.font("Arial", 15));
                                } else {
                                    ImageView imageView = new ImageView(historyIcon);
                                    imageView.setFitHeight(15);
                                    imageView.setFitWidth(15);
                                    setGraphic(imageView);
                                    System.out.println("image set");
                                    setText(" " + item.substring(1));
                                    setFont(Font.font("Arial", 15));
                                }
                            }
                        };
                    }
                }
        );
    }

    @FXML
    public void searchWord() {
        searchField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.BACK_SPACE) {
                textToSpeechButton.setVisible(false);
            }
            if (event.getCode() == KeyCode.DOWN) {
                listView.requestFocus();
                if (!listView.getItems().isEmpty()) {
                    listView.getSelectionModel().select(0);
                }
            }
        });
        searchField.setOnMouseClicked(event -> {
            if (listView.getItems() != null && !listView.getItems().isEmpty()) {
                listView.setVisible(true);
            }
        });
        String word = searchField.getText();
        if (word.startsWith("*")) {
            word = word.substring(1);
        }
        if (!word.isEmpty()) {
            History.addHistory(word);
        }

        String meaning = dictionary.search(word);

        takeSearchList();

        if (meaning.equals("No word found")) {
            listView.setVisible(false);
            webView.getEngine().loadContent("<h1 style=\"text-align: center;\">" + meaning + "</h1>");
            textToSpeechButton.setVisible(false);
        } else {
            latestWord = word;
            webView.getEngine().loadContent(meaning);
            textToSpeechButton.setVisible(true);
            listView.setVisible(false);
        }
        if (searchField.getText().isEmpty()) {
            textToSpeechButton.setVisible(false);
        }
    }

    @FXML
    public void selectEnter(KeyEvent key) {
        // if the list view is empty, return
        if (listView.getSelectionModel().getSelectedIndices().isEmpty()) {
            return;
        }
        if (key.getCode() == KeyCode.ENTER) {
            String word = listView.getSelectionModel().getSelectedItem();
            if (word.charAt(0) == '*') {
                searchField.setText(word.substring(1));
                listView.setVisible(false);
            } else {
                searchField.setText(word);
                listView.setVisible(false);
            }
            searchWord();
        } else if (key.getCode() == KeyCode.UP) {
            // if the selected index is 0 and the last selected index is 0, change the focus to the search field
            if (listView.getSelectionModel().getSelectedIndex() == 0 && lastSelectedIndex == 0) {
                searchField.requestFocus();
            }
        }
        // update the last selected index
        lastSelectedIndex = listView.getSelectionModel().getSelectedIndex();

    }

    @FXML
    public void selectClick(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1) {
            String word = listView.getSelectionModel().getSelectedItem();
            if (word.startsWith("*")) {
                searchField.setText(word.substring(1));
            } else {
                searchField.setText(word);
            }
            searchWord();
        }
    }

    @FXML
    public void setTextToSpeechButton() {
        if (searchField.getText().isEmpty()) {
            TextToSpeech.soundEnToVi("Please enter a word");
        } else {
            if (latestWord != null) {
                TextToSpeech.soundEnToVi(latestWord);
            } else {
                TextToSpeech.soundEnToVi("No word found");
            }
        }
    }

    @FXML
    public void setTranslateButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/GGTranslate.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1405, 850);
            stage.setTitle("Google Translate");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void setAddWordButton(ActionEvent event) {
        try {
            Stage popup = new Stage();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/AddWord.fxml")));
            Scene scene = new Scene(root, 681, 600);
            popup.setTitle("Add Word");
            popup.setScene(scene);
            popup.show();
            popup.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void setDeleteWordButton(ActionEvent event) {
        try{
            Stage popup = new Stage();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/DeleteWord.fxml")));
            Scene scene = new Scene(root, 337, 150);
            popup.setTitle("Delete Word");
            popup.setScene(scene);
            popup.show();
            popup.setResizable(false);
            latestWord = "";
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void setEditWordButton(ActionEvent event) {
        try {
            UpdateWordController.setEditWord(latestWord);
            Stage popup = new Stage();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/EditWord.fxml")));
            Scene scene = new Scene(root, 840, 590);
            popup.setTitle("Edit Word Meaning");
            popup.setScene(scene);
            popup.show();
            popup.setResizable(false);
            latestWord = "";
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
