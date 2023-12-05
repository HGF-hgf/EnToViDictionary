package com.example.dictionary.ui;


import com.example.dictionary.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class HangManGUIController implements Initializable {

    private static final Font fontLarge = Font.font("Droid Sans", FontWeight.BOLD, 35);
    private static final Font fontWord = Font.font("Courier New", FontWeight.BOLD, 20);
    @FXML
    AnchorPane acPane;
    @FXML
    private TextField txtInput;
    @FXML
    private TextField txtEntered;
    @FXML
    private Canvas canvas;
    @FXML
    private Button suggestButton;
    private int buttonPressCount = 0;
    private GraphicsContext gc;
    private HangManController gameController;

    /**
     * This method is called to initialize a controller after its root element has been completely processed.
     * It sets up the game controller, graphics context, and the text input listener for a hangman game.
     * The text input listener checks the entered character, updates the game state, and redraws the hangman image as necessary.
     * It also updates the displayed entered characters and current word, and checks if the game is over.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameController = new HangManController();
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.setLineWidth(5);

        txtInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if ("".equalsIgnoreCase(newValue)) {
                return;
            }

            char ch = newValue.charAt(0);
            if (gameController.addChar(ch)) {
                // Update graphic
                int wrongGuesses = gameController.getWrongGuesses();
                drawHangman(wrongGuesses);

            }

            txtInput.clear();
            updateEnteredChars();
            updateWord();
            checkGameOver();
        });

        updateWord();
    }

    /**
     * This method updates the displayed word in a hangman game.
     * It sets the font, color, alignment, and baseline of the text, and then draws the current state of the word on the canvas.
     */
    private void updateWord() {
        gc.setFont(fontWord);
        gc.setFill(Color.BLACK);
        gc.setTextAlign(TextAlignment.LEFT);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText(gameController.getCurrentWord(), 70, canvas.getHeight() - 60);
    }

    /**
     * This method draws the correct word in a hangman game.
     * It sets the font, color, alignment, and baseline of the text, and then draws the current state of the word on the canvas.
     */
    private void drawCorrectWord() {
        gc.setFont(fontWord);
        gc.setFill(Color.GREEN);
        gc.setTextAlign(TextAlignment.LEFT);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText(gameController.getMissingChars(), 70, canvas.getHeight() - 60);
    }

    /**
     * This method checks if the game is over in a hangman game.
     * If the game is won, it disables the text input and draws the "You Won!" message.
     * If the game is lost, it disables the text input and draws the "Game Over!" message.
     */
    private void checkGameOver() {
        if (gameController.isGameWon()) {
            // Return game won message
            disableGame();
            drawWon();

        } else if (gameController.isGameOver()) {
            // Return game over message
            disableGame();
            drawGameOver();
            drawCorrectWord();
        }
    }

    /**
     * This method resets the hangman game.
     * It resets the game controller, clears the entered characters, clears the canvas, and updates the word.
     */
    @FXML
    private void handleNewGame(final ActionEvent event) {
        resetGame();
    }

    /**
     * This method is called when the return button is clicked in the GUI.
     * It loads a new scene from the 'GamePage.fxml' file and sets it on the current stage.
     * If there are any errors during this process, it prints the stack trace.
     *
     * @param event The ActionEvent object representing the button click event.
     */
    @FXML
    public void setReturnButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Application.class.getResource("fxml/GamePage.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 616, 397);
            stage.setTitle("Game");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called when the suggest button is clicked in the GUI.
     * It sets the text of the text input to the suggested character, and then adds a listener to the text input.
     * The listener checks the entered character, updates the game state, and redraws the hangman image as necessary.
     * It also updates the displayed entered characters and current word, and checks if the game is over.
     *
     * @param event The ActionEvent object representing the button click event.
     */
    @FXML
    public void setSuggestButton(ActionEvent event) {
        buttonPressCount++;
        if (buttonPressCount > 1) {
            ((Node) event.getSource()).setDisable(true);
        }
        txtInput.setText(gameController.suggestChar());
        txtInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if ("".equalsIgnoreCase(newValue)) {
                return;
            }
            char ch = newValue.charAt(0);
            txtInput.clear();
            updateEnteredChars();
            updateWord();
            checkGameOver();
        });
        updateWord();
    }

    private void disableGame() {
        txtInput.setDisable(true);
    }

    private void resetGame() {
        gameController.reset();
        txtEntered.clear();
        txtInput.setDisable(false);
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        suggestButton.setDisable(false);
        buttonPressCount = 0;
        updateWord();
    }

    private void drawWon() {
        gc.setFont(fontLarge);
        gc.setFill(Color.GREEN);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText("You Won!", Math.round(canvas.getWidth() / 2), Math.round(canvas.getHeight()) / 2);
    }

    private void drawGameOver() {
        gc.setFont(fontLarge);
        gc.setFill(Color.RED);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText("Game Over!", Math.round(canvas.getWidth() / 2), Math.round(canvas.getHeight()) / 2);
    }

    private void drawHangman(int wrongGuesses) {
        switch (wrongGuesses) {
            case 1:
                drawVerticalGallows();
                break;
            case 2:
                drawHorizontalGallows();
                break;
            case 3:
                drawHead();
                break;
            case 4:
                drawBody();
                break;
            case 5:
                drawLegs();
                break;
            case 6:
                drawArms();
                break;
            case 7:
                drawFace();
                break;
        }
    }

    private void drawFace() {
        gc.beginPath();
        gc.moveTo(135, 75);
        gc.lineTo(145, 85);
        gc.moveTo(145, 75);
        gc.lineTo(135, 85);
        gc.moveTo(165, 75);
        gc.lineTo(155, 85);
        gc.moveTo(155, 75);
        gc.lineTo(165, 85);
        gc.stroke();
        gc.strokeArc(135, 95, 30, 30, 50, 80, ArcType.OPEN);
    }

    private void drawArms() {
        gc.beginPath();
        gc.moveTo(120, 140);
        gc.lineTo(180, 140);
        gc.stroke();
    }

    private void drawLegs() {
        gc.beginPath();
        gc.moveTo(150, 180);
        gc.lineTo(120, 220);
        gc.moveTo(150, 180);
        gc.lineTo(180, 220);
        gc.stroke();
    }

    private void drawBody() {
        gc.beginPath();
        gc.moveTo(150, 110);
        gc.lineTo(150, 180);
        gc.stroke();
    }

    private void drawHead() {
        gc.strokeOval(125, 60, 50, 50);
    }

    private void drawHorizontalGallows() {
        gc.beginPath();
        gc.moveTo(31, 60);
        gc.lineTo(60, 31);
        gc.moveTo(30, 30);
        gc.lineTo(150, 30);
        gc.lineTo(150, 60);
        gc.stroke();
    }

    private void drawVerticalGallows() {
        gc.beginPath();
        gc.moveTo(30, 30);
        gc.lineTo(30, 300);
        gc.moveTo(0, 300);
        gc.lineTo(60, 300);
        gc.stroke();
    }

    /**
     * This method updates the displayed list of entered characters in a hangman game.
     * It gets the list of entered characters from the game controller, sorts them, and formats them into a string.
     * It then sets this string as the text of the 'txtEntered' text field.
     */
    private void updateEnteredChars() {
        StringBuilder enteredFormatted = new StringBuilder();
        gameController.getEnteredChars().stream().sorted().forEach(i -> enteredFormatted.append(i).append(","));
        txtEntered.setText(enteredFormatted.toString().substring(0, enteredFormatted.length() - 1));
    }
}

