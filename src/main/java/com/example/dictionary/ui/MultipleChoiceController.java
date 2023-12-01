package com.example.dictionary.ui;

import com.example.dictionary.Application;
import com.example.dictionary.core.DictionaryB;
import com.example.dictionary.core.DictionaryManagement;
import com.example.dictionary.core.Words;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MultipleChoiceController implements Initializable {
    private static final int numberOfQuestions = 5;
    private int questionIndex = 1, score = 0, i;
    private String defination, answer;
    @FXML
    private Label questionLabel, scoreLabel;
    @FXML
    private Button choice1, choice2, choice3, choice4, returnBtn, nextBtn;
    private boolean checking = false, correctAns;
    private ArrayList<Button> b = new ArrayList<>();
    private DictionaryB dict = new DictionaryB();
    private DictionaryManagement dictm = new DictionaryManagement();


    private HashSet<Words> previousAnswers = new HashSet<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        b.add(choice1);
        b.add(choice2);
        b.add(choice3);
        b.add(choice4);
        dictm.insertFromFileSpecial("src/dictionaries.txt",dict);
        returnBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String path = "fxml/MultipleChoiceHelper.fxml";
                try {
                    FXMLLoader loader = new FXMLLoader(Application.class.getResource(path));
                    Stage stage = (Stage) returnBtn.getScene().getWindow();
                    Scene scene = new Scene(loader.load());
                    stage.setScene(scene);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        choice1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(!checking) {
                    checkAnswer(choice1.getText(), choice1);
                }
            }
        });
        choice2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(!checking) {
                    checkAnswer(choice2.getText(), choice2);
                }
            }
        });
        choice3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(!checking) {
                    checkAnswer(choice3.getText(), choice3);
                }
            }
        });
        choice4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(!checking) {
                    checkAnswer(choice4.getText(), choice4);
                }
            }
        });
//        choice2.setOnAction(e -> checkAnswer(choice2.getText(), choice2));
//        choice3.setOnAction(e -> checkAnswer(choice3.getText(), choice3));
//        choice4.setOnAction(e -> checkAnswer(choice4.getText(), choice4));
        nextBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {
                setNextBtnText();
                nextQuestion();


            }
        });
        setNextBtnText();
        displayQuestion();
    }

    private int getRandomIndex(int i) {
        Random random = new Random();
        return random.nextInt(i);
    }

    private void generateQuestion() {
        Words w = dict.get(getRandomIndex(dict.size()));
        while(previousAnswers.contains(w)) {
            w = dict.get(getRandomIndex(dict.size()));
        }
        previousAnswers.add(w);
        defination = w.getMeaning();
        answer = w.getWord();
    }

    private void displayQuestion() {

        HashSet<Integer> thisQuestionChoices = new HashSet<>();
        if (questionIndex <= numberOfQuestions) {
            generateQuestion();
            questionLabel.setText(defination);

//            ArrayList<Button> b = new ArrayList<>();

            i = getRandomIndex(4);
            b.get(i).setText(answer);
            for (int j=0;j<4;j++) {
                String temp;
                if (j!=i) {
                    // temp = dictionary.get(getRandomIndex(dictionary.size())).getWord_Target();
                    int randIndex = getRandomIndex(dict.size());
                    while (thisQuestionChoices.contains(randIndex)) {
                        // temp = dictionary.get(getRandomIndex(dictionary.size())).getWord_Target();
                        randIndex = getRandomIndex(dict.size());
                    }
                    thisQuestionChoices.add(randIndex);
                    b.get(j).setText(dict.get(randIndex).getWord());
                }
            }


        } else {
            questionLabel.setText("Game Over. Your score: " + score);
            scoreLabel.setVisible(false);
            if (score == 5) {
                questionLabel.setText("Perfect! Your score: " + score);
            }
            disableButtons();
        }

    }



    private void checkAnswer(String selectedAnswer, Button btn) {

        checking = true;
        if (selectedAnswer.equals(answer)) {
            score++; // Correct answer, increment score
            scoreLabel.setText("Score: " + score);
            btn.setStyle("-fx-background-color: #1e90ff");
        } else {
            btn.setStyle("-fx-background-color: #ff0000");
            b.get(i).setStyle("-fx-background-color: #1e90ff");
        }
        setNextBtnText();

        // Move to the next question
        //questionIndex++;
    }

    private void nextQuestion() {
        checking = false;
        resetColors();
        questionIndex++;
        displayQuestion();
        setNextBtnText();
    }

    private void resetColors() {
        choice1.setStyle("-fx-background-color: #f8f8f8");
        choice2.setStyle("-fx-background-color: #f8f8f8");
        choice3.setStyle("-fx-background-color: #f8f8f8");
        choice4.setStyle("-fx-background-color: #f8f8f8");
    }

    private void disableButtons() {
        choice1.setDisable(true);
        choice2.setDisable(true);
        choice3.setDisable(true);
        choice4.setDisable(true);
    }
    private void setNextBtnText() {
        if(checking) {
            nextBtn.setText("Tiếp tục");
        }
        else {
            nextBtn.setText("Bỏ qua");
        }
        if(questionIndex > numberOfQuestions) {
            nextBtn.setDisable(true);
        }
    }
}
