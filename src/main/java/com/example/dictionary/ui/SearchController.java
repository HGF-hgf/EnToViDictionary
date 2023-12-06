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

import static com.example.dictionary.Application.dictionary;


public class SearchController extends SwitchPage {
    Alerts alerts = new Alerts();
    private String latestWord = "";
    @FXML
    private TextField searchField;
    @FXML
    private WebView webView;
    @FXML
    private ListView<String> listView;
    private int lastSelectedIndex = 0;
    private Image historyIcon;
    @FXML
    private Button addWordButton;
    @FXML
    private Button gameButton;
    @FXML
    private Button deleteWordButton;
    @FXML
    private Button editWordButton;
    @FXML
    private Button translateButton;
    @FXML
    private Button textToSpeechButton;
    @FXML
    private Button exitButton;


    public SearchController() {}

    /**
     * This method is called to initialize a controller after its root element has been completely processed.
     * It sets the focus to the 'searchField' and calls the 'takeSearchList' method.
     */
    @FXML
    public void initialize() {
        // set the focus to the search field
        Platform.runLater(() -> searchField.requestFocus());
        takeHistoryIcon();
        takeSearchList();
    }

    /**
     * This method is called when a key is pressed in the GUI.
     * If the pressed key is the down arrow key, it changes the focus to the 'listView'.
     * If the 'listView' is not empty, it selects the first item.
     *
     * @param key The KeyEvent object representing the key press event.
     */
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

    public void takeHistoryIcon() {
        historyIcon = new Image(Objects.requireNonNull(Application.class.getResourceAsStream("icon/history-icon-light.png")));

    }

    /**
     * This method is called when the user types in the search field.
     * It clears the 'listView' and adds the searched words to it.
     * It also sets the 'latestWord' variable to the searched word.
     */
    public void takeSearchList() {
        listView.getItems().clear();
        listView.setVisible(true);
        String word = searchField.getText();
        ArrayList<String> searchedWords = Trie.search(word);
        ArrayList<String> history = History.getHistory();

        listView.setCellFactory(
                new Callback<>() {
                    @Override
                    public ListCell<String> call(ListView<String> param) {
                        return new ListCell<>() {
                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty || item == null) {
                                    setGraphic(null);
                                    setText(null);
                                } else if (item.charAt(0) != '*') {
                                    setGraphic(null);
                                    setText(item);
                                    setFont(Font.font("Arial", 15));
                                } else if (item.charAt(0) == '*') {
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

    }

    /**
     * This method is called when the user searches for a word.
     * It searches the word in the dictionary and displays the meaning in the 'webView'.
     * It also adds the searched word to the history.
     */
    @FXML
    public void searchWord() {
        String word = searchField.getText();
        if (word.startsWith("*")) {
            word = word.substring(1);
        }
        if (!word.isEmpty()) {
            History.addHistory(word);
            History.addToFile();
            takeHistoryIcon();
        }

        String meaning = dictionary.search(word);

        takeSearchList();

        latestWord = word;
        webView.getEngine().loadContent(meaning);
    }

    /**
     * This method is called when the user presses a key in the 'listView'.
     * If the pressed key is the enter key, it searches the selected word.
     * If the pressed key is the up arrow key and the selected index is 0, it changes the focus to the 'searchField'.
     * It also updates the 'lastSelectedIndex' variable.
     *
     * @param key The KeyEvent object representing the key press event.
     */
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

    /**
     * This method is called when the user clicks on an item in the 'listView'.
     * If the clicked item starts with a '*', it sets the 'searchField' to the word without the '*' and searches it.
     * If the clicked item does not start with a '*', it sets the 'searchField' to the word and searches it.
     *
     * @param event The MouseEvent object representing the mouse click event.
     */
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
    public void setClickOnTextField(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1) {
            listView.setVisible(true);
            takeHistoryIcon();
        }
    }
    /**
     * This method is called when the user clicks on the 'textToSpeechButton'.
     * It reads the searched word.
     */
    @FXML
    public void setTextToSpeechButton() {
        String meaning = dictionary.search(searchField.getText());
        if (searchField.getText().isEmpty()) {
            TextToSpeech.soundEnToVi("Please enter a word");
        } else {
            if (!meaning.equals("<h1 style=\"text-align: center;\">No word found</h1>")) {
                TextToSpeech.soundEnToVi(searchField.getText());
            } else {
                TextToSpeech.soundEnToVi("No word found");
            }
        }
    }


    /**
     * This method is called when the 'Delete Word' button is clicked in the GUI.
     * If no word is selected or the selected word does not exist in the dictionary, it shows an information alert.
     * If a word is selected, and it exists in the dictionary, it shows a warning alert asking for confirmation to delete the word.
     * If the user confirms, it deletes the word from the dictionary and shows a success alert.
     * If the user cancels, it shows an information alert.
     *
     * @param event The ActionEvent object representing the button click event.
     */
    @FXML
    public void setDeleteWordButton(ActionEvent event) {
        if (latestWord.isEmpty()) {
            alerts.showAlertInfo("Information", "Vui lòng chọn từ cần xóa");
        } else if (dictionary.search(latestWord).equals("<h1 style=\"text-align: center;\">No word found</h1>")) {
            alerts.showAlertInfo("Information", "Từ này không tồn tại");
        } else {
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
    }

    /**
     * This method is called when the 'Edit Word' button is clicked in the GUI.
     * If no word is selected or the selected word does not exist in the dictionary, it shows an information alert.
     * If a word is selected, and it exists in the dictionary, it loads a new scene from the 'EditWord.fxml' file and sets it on a new stage.
     *
     * @param event The ActionEvent object representing the button click event.
     */
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
    public void setSynonymButton(ActionEvent event){
        try {
            SynonymAntonym.setWord(latestWord);
            Stage popup = new Stage();
            Parent root = FXMLLoader.load(Objects.requireNonNull(
                    Application.class.getResource("fxml/SynonymAntonym.fxml")));
            Scene scene = new Scene(root, 464, 373);
            popup.getIcons().add(new Image(Objects.requireNonNull(Application.class.getResourceAsStream("icons/app.png"))));
            popup.setTitle("Synonym");
            popup.setScene(scene);
            popup.show();
            popup.setResizable(false);
            latestWord = "";
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
