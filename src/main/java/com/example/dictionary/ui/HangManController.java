package com.example.dictionary.ui;

import com.example.dictionary.core.Words;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.dictionary.Application.dictionary;


class HangManController {

    private static final int MAX_WRONG_GUESSES = 7;

    //    private RandomWordFinder rndWordFinder;
    private String rndWord;

    private List<Character> enteredChars = new ArrayList<>();
    private List<Words> playedWords = new ArrayList<>();
    private int wrongGuesses;

    HangManController() {
        setNewRandomWord();
    }

    /**
     * This method constructs and returns the current state of the word being guessed in a hangman game.
     * It iterates over each character in the random word. If the character has been entered by the user,
     * it adds the character to the current word. Otherwise, it adds an underscore.
     * The returned string represents the word with guessed letters revealed and unguessed letters hidden.
     *
     * @return A string representing the current state of the word being guessed.
     */
    public String getCurrentWord() {
        String currentWord = "";
        for (char c : rndWord.toCharArray()) {
            if (enteredChars.contains(c)) {
                currentWord += c + " ";
            } else {
                currentWord += "_ ";
            }
        }
        return currentWord;
    }

    /**
     * This method constructs and returns the current state of the word being guessed in a hangman game.
     * It iterates over each character in the random word. If the character has been entered by the user,
     * it adds two spaces. Otherwise, it adds the character.
     * The returned string represents the word with guessed letters hidden and unguessed letters revealed.
     * This is used to display the word after the game is over.
     *
     * @return A string representing the current state of the word being guessed.
     */
    public String getMissingChars() {
        String missingChars = "";
        for (char c : rndWord.toCharArray()) {
            if (enteredChars.contains(c)) {
                missingChars += "  ";
            } else {
                missingChars += c + " ";
            }
        }
        return missingChars;
    }

    /**
     * This method returns the word being guessed in a hangman game.
     *
     * @return A string representing the word being guessed.
     */
    public String getWord() {
        String word = "";
        for (char c : rndWord.toCharArray()) {
            word += c + " ";
        }
        return word;
    }

    /**
     * This method returns the meaning of the word being guessed in a hangman game.
     */
    public void reset() {
        wrongGuesses = 0;
        enteredChars.clear();
        setNewRandomWord();
    }

    /**
     * This method returns the meaning of the word being guessed in a hangman game.
     */
    private void setNewRandomWord() {
        Words w = dictionary.getAllWords().get((int) (Math.random() * dictionary.getAllWords().size()));
        rndWord = w.getWord();
        playedWords.add(w);
    }

    /**
     * This method returns the meaning of the word being guessed in a hangman game.
     */
    public int getWrongGuesses() {
        return wrongGuesses;
    }

    /**
     * This method adds a guessed character to the list of entered characters in a hangman game.
     * If the character has not been guessed before and is not in the random word, it increments the count of wrong guesses.
     *
     * @param ch The character to be added to the list of entered characters.
     * @return A boolean indicating whether the guess was wrong (true if the guess was wrong, false otherwise).
     */
    public boolean addChar(char ch) {
        boolean wrongGuess = false;
        if ((!enteredChars.stream().anyMatch(i -> i.equals(ch)))) {
            enteredChars.add(ch);

            if (!rndWord.contains(String.valueOf(ch))) {
                wrongGuess = true;
                wrongGuesses++;
            }
        }

        return wrongGuess;
    }

    /**
     * This method returns the list of entered characters in a hangman game.
     *
     * @return A list of characters representing the characters that have been entered.
     */
    public List<Character> getEnteredChars() {
        return Collections.unmodifiableList(enteredChars);
    }

    /**
     * This method returns the list of played words in a hangman game.
     *
     * @return A list of words representing the words that have been played.
     */
    public boolean isGameOver() {
        return wrongGuesses >= MAX_WRONG_GUESSES;
    }

    /**
     * This method returns the list of played words in a hangman game.
     *
     * @return A list of words representing the words that have been played.
     */
    public boolean isGameWon() {
        for (char c : rndWord.toCharArray()) {
            if (!enteredChars.contains(c)) {
                return false;
            }
        }

        return true;
    }

    /**
     * This method returns the list of played words in a hangman game.
     *
     * @return A list of words representing the words that have been played.
     */
    public String suggestChar() {
        ArrayList<Character> availableChars = new ArrayList<>();
        for (int i = 0; i < rndWord.length(); i++) {
            if (enteredChars.contains(rndWord.charAt(i)) || rndWord.charAt(i) == ' ') {
                continue;
            }
            availableChars.add(rndWord.charAt(i));
        }
        if (availableChars.isEmpty()) {
            return null;
        }
        int randomIndex = (int) (Math.random() * availableChars.size());
        char selectedChar = availableChars.remove(randomIndex);

        return String.valueOf(selectedChar);
    }
}
