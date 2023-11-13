package com.example.dictionaryy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class SimpleTrie implements Trie {
    private class Node {
        private Character symbol;
        private Node[] children;
        private boolean word;

        public Node(Character symbol) {
            // each node contains a symbol
            this.symbol = symbol;
            // each node contains an array of 26 children (26 letters in the alphabet)
            this.children = new Node[26];
            // Default value for word is false
            this.word = false;
        }

        // method to return a list of the existing children
        private ArrayList<Node> getChildren() {
            ArrayList<Node> children = new ArrayList<>();
            for (Node child : this.children) {
                if (child != null) {
                    children.add(child);
                }
            }
            return children;
        }
    }

    private Node root;
    private int size;
    private ArrayList<String> words;
    private LinkedList<Character> traversal;

    public SimpleTrie() {
        //Initialize the root node with a non-character value
        root = new Node('-');
        size = 0;
    }

    /**
     * method that takes a character and returns the index of the character in the array
     * a->0, b->1, c->2, etc.
     * uses unicode method unicode bounds: a = 0010, z = 0035.
     */
    private int getIndex(Character c) {
        int i = Character.getNumericValue(c);
        return i - 10;
    }

    /**
     * adds the specified word to the trie
     */

    public Boolean add(String word){
        String lowerCaseWord = word.toLowerCase();
        Node current = root;

        for (int i = 0; i < lowerCaseWord.length(); ++i){
            int index = getIndex(lowerCaseWord.charAt(i));
            if (index < 0 || index > 25){
                return false;
            }
            if (current.children[index] != null){
                // if the current node has a child at the index, move to that child
                current = current.children[index];
            } else if (current.children[index] == null && i < lowerCaseWord.length()- 1){
                current.children[index] = new Node(lowerCaseWord.charAt(i));
                current = current.children[index];
            } else {
                current.children[index] = new Node(lowerCaseWord.charAt(i));
                current.children[index].word = true;
                size++;
                return true;
            }
        }
        return false;
    }

    public Boolean isPrefix(String prefix){
        String lowerCasePrefix = prefix.toLowerCase();
        Node current = root;

        for (int i = 0; i < lowerCasePrefix.length(); ++i){
            int index = getIndex(lowerCasePrefix.charAt(i));
            if (index < 0 || index > 25){
                return false;
            }
            if (current.children[index] != null){
                current = current.children[index];
            } else {
                return false;
            }
        }
        return true;
    }

    public Boolean contains(String word){
        String lowerCaseWord = word.toLowerCase();
        Node current = root;

        for (int i = 0; i < lowerCaseWord.length(); ++i){
            int index = getIndex(lowerCaseWord.charAt(i));
            if (index < 0 || index > 25){
                return false;
            }
            if (current.children[index] == null){
                return false;
            } else if (i == lowerCaseWord.length() - 1){
                return current.children[index].word;
            } else {
                current = current.children[index];
            }
        }
        return false;
    }

    public int count (String prefix){
        int count = 0;
        String lowerCasePrefix = prefix.toLowerCase();
        Node current = root;

        for (int i =0; i < lowerCasePrefix.length(); ++i){
            int index = getIndex(lowerCasePrefix.charAt(i));
            if (current.children[index] != null){
                current = current.children[index];
            }
        }

        if (current.symbol != lowerCasePrefix.charAt(lowerCasePrefix.length() - 1)){
            return count;
        }

        Stack<Node> children = new Stack<>();
        children.add(current);
        while(children.size() > 0){
            Node child = children.pop();

            for (int i = 0; i < 26; ++i){
                Node n = child.children[i];

                if (n!=null){
                    children.add(n);
                    if (n.word){
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public int size(){
        return size;
    }

    public ArrayList<String> words(String prefix){
        String lowerCasePrefix = prefix.toLowerCase();
        Node current = root;
        for (int i =0;i < lowerCasePrefix.length(); ++i){
            int index = getIndex(lowerCasePrefix.charAt(i));
            if (current.children[index] != null){
                current = current.children[index];
            }
        }

        String pre = lowerCasePrefix.substring(0, lowerCasePrefix.length() - 1);
        words = new ArrayList<>(10);
        traversal = new LinkedList<>();
        recursiveWords(current, pre);
        return words;
    }

    private String compress(String prefix, LinkedList<Character> traversal){
        StringBuilder sb = new StringBuilder(prefix);
        for (Character c : traversal){
            sb.append(c);
        }
        return sb.toString();
    }
    private void recursiveWords(Node current, String pre) {
        traversal.add(current.symbol);
        ArrayList<Node> children = current.getChildren();
        if(children.isEmpty()){
            if (current.word && words.size() < 10){
                words.add(compress(pre, traversal));
            }
        } else {
            if (current.word && words.size() < 10){
                words.add(compress(pre, traversal));
            }
            for (Node child : children){
                recursiveWords(child, pre);
                traversal.removeLast();
            }
        }
    }
}
