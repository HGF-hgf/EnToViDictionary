package com.example.dictionary.ui;

import com.example.dictionary.Application;
import com.example.dictionary.core.History;
import com.example.dictionary.api.TextToSpeech;
import com.example.dictionary.core.Trie;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import javafx.scene.Node;

import static com.example.dictionary.Application.dictionary;


public class SearchController {
    Alerts alerts = new Alerts();
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
    @FXML
    private Button exitButton;

    public SearchController() {
    }


    @FXML
    public void initialize() {
        Platform.runLater(() -> searchField.requestFocus());

        takeHistoryIcon();
        takeSearchList();
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

    private void takeHistoryIcon() {
        historyIcon = new Image(Objects.requireNonNull(Application.class.getResourceAsStream("icon/history-icon-light.png")));
    }

    public void takeSearchList() {
        listView.getItems().clear();

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

        if (!searchField.getText().equals(latestWord) || searchField.getText().isEmpty()) {
            webView.getEngine().loadContent("");
            return;
        }
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
            if (event.getCode() == KeyCode.DOWN) {
                listView.requestFocus();
                if (!listView.getItems().isEmpty()) {
                    listView.getSelectionModel().select(0);
                }
            }
            if (event.getCode() == KeyCode.ENTER) {
                String word = searchField.getText();
                if (word.charAt(0) == '*') {
                    searchField.setText(word.substring(1));
                } else {
                    searchField.setText(word);
                }
                searchWord();
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

        latestWord = word;
        webView.getEngine().loadContent(meaning);
        textToSpeechButton.setVisible(true);

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
            } else {
                searchField.setText(word);
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
        String meaning = dictionary.search(searchField.getText());
        if (searchField.getText().isEmpty()) {
            TextToSpeech.soundEnToVi("Please enter a word");
        } else {
            if (!meaning.equals("No word found")) {
                TextToSpeech.soundEnToVi(searchField.getText());
            } else {
                TextToSpeech.soundEnToVi("No word found");
            }
        }
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
    public void setDeleteWordButton(ActionEvent event) {
        Alert alertWarning = alerts.alertWarning("Delete", "Bạn chắc chắn muốn xóa từ này?");
        // option != null.
        alertWarning.getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> option = alertWarning.showAndWait();
        if (option.get() == ButtonType.OK) {
            // delete selected word from dictionary
            dictionary.delete(latestWord);
            // succeed
            alerts.showAlertInfo("Information", "Xóa thành công");
        } else {
            alerts.showAlertInfo("Information", "Thay đổi không được công nhận");
        }
    }

    @FXML
    public void setEditWordButton(ActionEvent event) {
        try {
            UpdateWordController.setEditWord(latestWord);
            Stage popup = new Stage();
            Parent root = FXMLLoader.load(Objects.requireNonNull(Application.class.getResource("fxml/EditWord.fxml")));
            Scene scene = new Scene(root, 779, 413);
            popup.setTitle("Edit Word Meaning");
            popup.setScene(scene);
            popup.show();
            popup.setResizable(false);
            latestWord = "";
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
