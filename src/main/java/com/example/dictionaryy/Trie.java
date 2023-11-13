package com.example.dictionaryy;

public interface Trie {
    Boolean add(String word);
    Boolean isPrefix(String prefix);
    Boolean contains(String word);
    int count(String prefix);
    int size();
}
