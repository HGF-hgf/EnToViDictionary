//package com.example.dictionaryy;
//
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.collections.transformation.FilteredList;
//import javafx.collections.transformation.SortedList;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.TextField;
//import javafx.scene.control.cell.PropertyValueFactory;
//
//import java.net.URL;
//import java.util.ResourceBundle;
//
//public class WordController implements Initializable {
//    @FXML
//    private TextField filterField;
//    @FXML
//    private TableView<Words> tableView;
//    @FXML
//    private TableColumn<Words, String> wordName;
//    @FXML
//    private TableColumn<Words, String> wordMeaning;
//
//    private final ObservableList<Words> dataList = FXCollections.observableArrayList();
//
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        wordName.setCellValueFactory(new PropertyValueFactory<>("word"));
//        wordMeaning.setCellValueFactory(new PropertyValueFactory<>("meaning"));
//
//        Words word1 = new Words("Hello", "Chao");
//        Words word2 = new Words("Goodbye", "Tam biet");
//        Words word3 = new Words("Good morning", "Chao buoi sang");
//        Words word4 = new Words("Good afternoon", "Chao buoi chieu");
//        Words word5 = new Words("Good evening", "Chao buoi toi");
//        Words word6 = new Words("Good night", "Chuc ngu ngon");
//        Words word7 = new Words("How are you?", "Ban khoe khong?");
//        Words word8 = new Words("I'm fine, thank you", "Toi khoe, cam on ban");
//        Words word9 = new Words("What is your name?", "Ban ten gi?");
//        Words word10 = new Words("My name is...", "Toi ten la...");
//
//        dataList.addAll(word1, word2, word3, word4, word5, word6, word7, word8, word9, word10);
//
//        FilteredList<Words> filteredData = new FilteredList<>(dataList, b -> true);
//
//        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
//            filteredData.setPredicate(words -> {
//                if (newValue == null || newValue.isEmpty()) {
//                    return true;
//                }
//                String lowerCaseFilter = newValue.toLowerCase();
//                if (words.getWord().toLowerCase().contains(lowerCaseFilter)) {
//                    return true;
//                } else return words.getMeaning().toLowerCase().contains(lowerCaseFilter);
//            });
//        });
//
//        SortedList<Words> sortedData = new SortedList<>(filteredData);
//        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
//        tableView.setItems(sortedData);
//    }
//}
