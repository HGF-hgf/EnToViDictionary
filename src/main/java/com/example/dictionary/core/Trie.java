package com.example.dictionary.core;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Trie {
    private static final ArrayList<String> searchedWords = new ArrayList<>();
    private static final TrieNode root = new TrieNode();
    public static ArrayList<String> getSearchedWords() {
        return searchedWords;
    }

// insert a word into the trie
    public static void insert(String word) {
        // current node
        TrieNode current = root;
        for (int i = 0; i < word.length(); ++i) {
            char ch = word.charAt(i);
            // if the character is not in the trie, add it
            if (current.children.get(ch) == null) {
                current.children.put(ch, new TrieNode());
            }
            // move to the next node
            current = current.children.get(ch);
        }
        // mark the current node as the end of the word
        current.isEndOfWord = true;
    }

    private static void dfs(TrieNode current, String word) {
        // if the current node is the end of the word, add it to the list
        if (current.isEndOfWord) {
            searchedWords.add(word);
        }
        // for each character in the trie, recursively call dfs
        for (char ch : current.children.keySet()) {
            if (current.children.get(ch) != null) {
                dfs(current.children.get(ch), word + ch);
            }

        }
    }

    // search for a word that starts with the prefix
    public static ArrayList<String> search(String  prefix){
        // if the prefix is null, return an empty list
        if (prefix.isEmpty()){
            return new ArrayList<>();
        }
        searchedWords.clear();
        TrieNode current = root;
        for (int i = 0; i < prefix.length(); ++i){
            char ch = prefix.charAt(i);
            // if the character is not in the trie, return an empty list
            if (current.children.get(ch) == null){
                return getSearchedWords();
            }
            current = current.children.get(ch);
        }
        dfs(current, prefix);
        return getSearchedWords();
    }

    public static void delete(String word){
        TrieNode current = root;

        for (int i = 0; i < word.length();++i){
            char ch = word.charAt(i);
            // if the character is not in the trie, return
            if (current.children.get(ch) == null){
                System.out.println("Word not found");
                return;
            }
        }
        if (!current.isEndOfWord){
            System.out.println("Word not found");
        }
        current.isEndOfWord = false;
    }

    public static class TrieNode {
        Map<Character, TrieNode> children = new TreeMap<>();

        boolean isEndOfWord;

        public TrieNode() {
            isEndOfWord = false;
        }
    }
}
