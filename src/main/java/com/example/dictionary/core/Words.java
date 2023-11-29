package com.example.dictionary.core;

public class Words {
    private String word;
    private String meaning;

    public Words(String word, String meaning) {
        this.word = word;
        this.meaning = meaning;
    }


    public void setWord(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getMeaning() {
        return meaning;
    }
}
