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

    /**
     * This method is called to initialize a controller after its root element has been completely processed.
     * It sets up the action handlers for the 'returnBtn', 'choice1', 'choice2', 'choice3', 'choice4', and 'nextBtn' buttons.
     * The 'returnBtn' handler loads a new scene from the 'MultipleChoiceHelper.fxml' file and sets it on the current stage.
     * The 'choice' handlers check the answer if the 'checking' flag is false.
     * The 'nextBtn' handler sets the next button text and displays the next question.
     * It also adds the choice buttons to the 'b' list and inserts data from a file into the 'dict' dictionary.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        b.add(choice1);
        b.add(choice2);
        b.add(choice3);
        b.add(choice4);
        dictm.insertFromFileSpecial("src/dictionaries.txt", dict);
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
                if (!checking) {
                    checkAnswer(choice1.getText(), choice1);
                }
            }
        });
        choice2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!checking) {
                    checkAnswer(choice2.getText(), choice2);
                }
            }
        });
        choice3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!checking) {
                    checkAnswer(choice3.getText(), choice3);
                }
            }
        });
        choice4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!checking) {
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

    /**
     * This method generates and returns a random index within the range [0, i).
     * It uses the java.util.Random class to generate the random index.
     *
     * @param i The upper bound (exclusive) for the random index.
     * @return A random integer between 0 (inclusive) and i (exclusive).
     */
    private int getRandomIndex(int i) {
        Random random = new Random();
        return random.nextInt(i);
    }

    /**
     * This method generates a question and displays it on the screen.
     * It also generates the choices for the question and displays them on the screen.
     * It uses the 'getRandomIndex' method to generate a random index within the range [0, dict.size()).
     * It uses the 'previousAnswers' set to make sure that the same word is not displayed twice in a row.
     */
    private void generateQuestion() {
        Words w = dict.get(getRandomIndex(dict.size()));
        while (previousAnswers.contains(w)) {
            w = dict.get(getRandomIndex(dict.size()));
        }
        previousAnswers.add(w);
        defination = w.getMeaning();
        answer = w.getWord();
    }

    /**
     * This method displays the question on the screen.
     * It also generates the choices for the question and displays them on the screen.
     * It uses the 'getRandomIndex' method to generate a random index within the range [0, dict.size()).
     * It uses the 'previousAnswers' set to make sure that the same word is not displayed twice in a row.
     */
    private void displayQuestion() {

        HashSet<Integer> thisQuestionChoices = new HashSet<>();
        if (questionIndex <= numberOfQuestions) {
            generateQuestion();
            questionLabel.setText(defination);
            i = getRandomIndex(4);
            b.get(i).setText(answer);
            for (int j = 0; j < 4; j++) {
                if (j != i) {
                    int randIndex = getRandomIndex(dict.size());
                    while (thisQuestionChoices.contains(randIndex)) {
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

    /**
     * This method checks the answer and increments the score if the answer is correct.
     * It also sets the background color of the button to green if the answer is correct, and red if the answer is wrong.
     * It also sets the background color of the correct answer button to green.
     * It also sets the text of the next button to "Tiếp tục" if the answer is correct, and "Bỏ qua" if the answer is wrong.
     *
     * @param selectedAnswer The answer selected by the user.
     * @param btn            The button that was clicked.
     */
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

    /**
     * This method resets the background color of the buttons to white.
     * It also increments the question index and displays the next question.
     * It also sets the text of the next button to "Tiếp tục" if the answer is correct, and "Bỏ qua" if the answer is wrong.
     */
    private void nextQuestion() {
        checking = false;
        resetColors();
        questionIndex++;
        displayQuestion();
        setNextBtnText();
    }

    /**
     * This method resets the background color of the buttons to white.
     */
    private void resetColors() {
        choice1.setStyle("-fx-background-color: #f8f8f8");
        choice2.setStyle("-fx-background-color: #f8f8f8");
        choice3.setStyle("-fx-background-color: #f8f8f8");
        choice4.setStyle("-fx-background-color: #f8f8f8");
    }

    /**
     * This method disables the buttons.
     */
    private void disableButtons() {
        choice1.setDisable(true);
        choice2.setDisable(true);
        choice3.setDisable(true);
        choice4.setDisable(true);
    }

    /**
     * This method sets the text of the next button to "Tiếp tục" if the answer is correct, and "Bỏ qua" if the answer is wrong.
     */
    private void setNextBtnText() {
        if (checking) {
            nextBtn.setText("Tiếp tục");
        } else {
            nextBtn.setText("Bỏ qua");
        }
        if (questionIndex > numberOfQuestions) {
            nextBtn.setDisable(true);
        }
    }
}
