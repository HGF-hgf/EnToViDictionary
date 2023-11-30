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

    String getCurrentWord() {
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

    String getMissingChars() {
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

    String getWord() {
        String word = "";
        for (char c : rndWord.toCharArray()) {
            word += c + " ";
        }
        return word;
    }

    void reset() {
        wrongGuesses = 0;
        enteredChars.clear();
        setNewRandomWord();
    }

    private void setNewRandomWord() {
        Words w = dictionary.getAllWords().get((int) (Math.random() * dictionary.getAllWords().size()));
        rndWord = w.getWord();
        playedWords.add(w);
    }

    int getWrongGuesses() {
        return wrongGuesses;
    }

    boolean addChar(char ch) {
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

    List<Character> getEnteredChars() {
        return Collections.unmodifiableList(enteredChars);
    }

    boolean isGameOver() {
        return wrongGuesses >= MAX_WRONG_GUESSES;
    }

    boolean isGameWon() {
        for (char c : rndWord.toCharArray()) {
            if (!enteredChars.contains(c)) {
                return false;
            }
        }

        return true;
    }

    public String suggestChar() {
        ArrayList<Character> availableChars = new ArrayList<>();
        for (int i = 0; i < rndWord.length(); i++) {
            if (enteredChars.contains(rndWord.charAt(i))) {
                continue;
            }
            availableChars.add(rndWord.charAt(i));
        }
        if (availableChars.isEmpty()) {
            return null;
        }
        int randomIndex = (int) (Math.random() * availableChars.size());
        char selectedChar = availableChars.remove(randomIndex);

        System.out.println(selectedChar);
        return String.valueOf(selectedChar);
    }

}
