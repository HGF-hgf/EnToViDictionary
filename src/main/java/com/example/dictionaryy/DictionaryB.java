package com.example.dictionaryy;

import java.util.ArrayList;

public class DictionaryB extends Dictionary {
    // ArrayList of Words
    public static final ArrayList<Words> words = new ArrayList<>();

    @Override
    public ArrayList<Words> getAllWords() {
        return words;
    }

    @Override
    public ArrayList<String> getWords() {
        ArrayList<String> res = new ArrayList<>();
        for (Words word : DictionaryB.words) {
            res.add(word.getWord());
        }
        return res;
    }

    @Override
    public String search(final String word) {
        // for each word in the dictionary, if the word is found, return its meaning
        for (Words w : DictionaryB.words) {
            if (w.getWord().equals(word)) {
                return w.getMeaning();
            }
        }
        return "No word found";
    }

    @Override
    public boolean insert(final String word, final String meaning) {
        // for each word in the dictionary, if the word is found, return false
        for (Words w : DictionaryB.words) {
            if (w.getWord().equals(word)) {
                return false;
            }
        }
        // if the word is not found, add it to the dictionary
        DictionaryB.words.add(new Words(word, meaning));
        Trie.insert(word);
        return true;
    }

    @Override
    public boolean delete(final String word) {
        // for each word in the dictionary, if the word is found, remove it
        for (Words w : DictionaryB.words) {
            if (w.getWord().equals(word)) {
                DictionaryB.words.remove(w);
                Trie.delete(word);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean update(final String word, final String meaning) {
        // for each word in the dictionary, if the word is found, update its meaning
        for (Words w : DictionaryB.words) {
            if (w.getWord().equals(word)) {
                w.setMeaning(meaning);
                return true;
            }
        }
        return false;
    }
}
