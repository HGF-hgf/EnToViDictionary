package com.example.dictionaryy;

import org.sqlite.SQLiteException;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public abstract class Dictionary {
    public void initialize() throws SQLiteException{}

    public void close() {}

    public abstract ArrayList<Words> getAllWords();

    public abstract ArrayList<String> getWords();

    public abstract String search(final String word);

    public abstract boolean insert(final String word, final String meaning);

    public abstract boolean delete(final String word);

    public abstract boolean update(final String word, final String meaning);


}
