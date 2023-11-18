package com.example.dictionaryy;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
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
import javafx.util.Callback;

import java.util.ArrayList;

import static com.example.dictionaryy.HelloApplication.dictionary;

public class SearchController {
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

    public SearchController() {
    }

    @FXML
    public void initialize() {
        Platform.runLater(() -> searchField.requestFocus());
        takeSearchList();
    }

    @FXML
    public void changeFocus(KeyEvent key) {
        // if the user presses the down arrow key, change the focus to the list view
        if (key.getCode() == javafx.scene.input.KeyCode.DOWN) {
            listView.requestFocus();
            // if the list view is not empty, select the first item
            if (!listView.getItems().isEmpty()) {
                listView.getSelectionModel().select(0);
            }
        }
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
            listView.getItems().add(w);
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
                                    // setGraphic(null);
                                    setText(null);
                                } else if (item.charAt(0) != '*') {
                                    // setGraphic(null);
                                    setText(item);
                                    // setFont(Font.font("Arial", 16));
                                } else {
                                    ImageView imageView = new ImageView(historyIcon);
                                    imageView.setFitHeight(16);
                                    imageView.setFitWidth(16);
                                    setGraphic(imageView);
                                    setText(" " + item.substring(1));
                                    //setFont(Font.font("Arial", 16));
                                }
                            }
                        };
                    }
                }
        );
    }

    @FXML
    public void searchWord() {
        String word = searchField.getText();
        if (word.startsWith("*")) {
            word = word.substring(1);
        }
        if (!word.isEmpty()) {
            History.addHistory(word);
        }

        String meaning = dictionary.search(word);
        if (meaning.equals("No word found")) {
            webView.getEngine().loadContent("<h1 style=\"text-align: center;\">" + meaning + "</h1>");
        } else {
            latestWord = word;
            webView.getEngine().loadContent(meaning);
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
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            if (event.getClickCount() == 1) {
                String word = listView.getSelectionModel().getSelectedItem();
                if (word.charAt(0) == '*') {
                    searchField.setText(word.substring(1));
                } else {
                    searchField.setText(word);
                }
                searchWord();
            }
        }
    }


}