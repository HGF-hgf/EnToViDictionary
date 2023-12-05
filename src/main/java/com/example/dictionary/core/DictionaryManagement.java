package com.example.dictionary.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryManagement extends Dictionary {

    private static final ArrayList<Words> words = new ArrayList<>();

    /**
     * Get all words in the dictionary.
     *
     * @return ArrayList of Word
     */
    @Override
    public ArrayList<Words> getAllWords() {
        return words;
    }

    /**
     * Get all English words in the dictionary into an ArrayList of String.
     *
     * @return ArrayList of String of all words
     */
    @Override
    public ArrayList<String> getWords() {
        ArrayList<String> result = new ArrayList<>();
        for (Words w : words) {
            String target = w.getWord();
            result.add(target);
        }
        return result;
    }

    /**
     * Lookup the word `target` and return the corresponding definition.
     *
     * @param word the lookup word
     * @return the definition, if not found "404" is returned as a String.
     */
    @Override
    public String search(final String word) {

        for (Words w : words) {
            if (w.getWord().equals(word)) {
                return w.getMeaning();
            }
        }
        return "Not found";
    }

    /**
     * Insert a new word to dictionary.
     *
     * @param word the word
     * @param meaning the definition
     * @return true if `target` hasn't been added yet, false otherwise
     */
    @Override
    public boolean insert(final String word, final String meaning) {
        for (Words w : words) {
            if (w.getWord().equals(word)) {
                return false;
            }
        }
        Words w = new Words(word, meaning);
        words.add(w);
        Trie.insert(word);
        return true;
    }

    /**
     * Delete the word `target`.
     *
     * @param word the deleted word
     * @return true if successfully delete, false otherwise
     */
    @Override
    public boolean delete(final String word) {
        for (int i = 0; i < words.size(); ++i) {
            if (words.get(i).getWord().equals(word)) {
                words.remove(i);
                Trie.delete(word);
                return true;
            }
        }
        return false;
    }

    /**
     * Update the Vietnamese definition of `target` to `definition`.
     *
     * @param word the word
     * @param meaning the new definition
     * @return true if successfully updated, false otherwise
     */
    @Override
    public boolean update(final String word, final String meaning) {
        for (Words w : words) {
            if (w.getWord().equals(word)) {
                w.setMeaning(meaning);
                return true;
            }
        }
        return false;
    }

    /**
     * Load data from file.
     *
     * @param src the path to the file
     */
    public void insertFromFileSpecial (String src, DictionaryB dict) {
        try {
            File file = new File(src);
            Scanner scnF = new Scanner(file);
            while (scnF.hasNextLine()) {
                String line = scnF.nextLine();

                String[] stack = line.split("\\t");
                if(stack.length == 2) {
                    Words newWord = new Words(stack[0],stack[1]);
                    dict.add(newWord);

                }
                else {
                    System.err.println("Invalid line format: " + line);
                }
            }
            scnF.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
