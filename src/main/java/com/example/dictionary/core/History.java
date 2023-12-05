package com.example.dictionary.core;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class History {
    private static final int Max = 10;
    private static final ArrayList<String> history = new ArrayList<>();

    public static ArrayList<String> getHistory() {
        return history;
    }

    /**
     * This method removes the first element of the history until the size is less than Max
     * */
    public static void refactorHistory() {
        // if the size of the history is less than Max, return
        if (history.size() <= Max) {
            return;
        }
        // remove the first element of the history until the size is less than Max
        while (history.size() > Max) {
            history.remove(0);
        }
    }

    /**
     * This method loads the history of dictionary lookups from a file.
     * If the history folder or file does not exist, it creates them.
     * It reads each line from the history file and adds it to the history list.
     * If there are any errors during this process, it prints an error message and the stack trace.
     */
    public static void loadHistory() {
        // create the history folder and file if they don't exist
        File file = new File("dictionary-history/");
        // if the folder doesn't exist, create it
        if (!file.exists()) {
            // if the folder is not created, print an error message
            if (!file.mkdir()) {
                System.out.println("Error creating history folder");
            }
        }

        // if the file doesn't exist, create it
        File historyFile = new File(file + "/history.txt");
        // if the file is not created, print an error message
        if (!historyFile.exists()) {
            try {
                if (!historyFile.createNewFile()) {
                    System.out.println("Error creating history file");
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error creating history file");
            }
            return;
        }

        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream("dictionary-history/history.txt"),
                            StandardCharsets.UTF_8));

            String line;
            while ((line = reader.readLine()) != null) {
                history.add(line.strip());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error loading history file");
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Error reading history file");
        }
        refactorHistory();
    }

    /**
     * This method adds a word to the history list.
     * If the word is already in the history list, it removes it and adds it to the end of the list.
     * It then calls the refactorHistory method to remove the first element of the history until the size is less than Max.
     * */
    public static void addHistory(String word) {
        history.removeIf(w -> w.equals(word));
        history.add(word);
        refactorHistory();
    }

    /**
     * This method writes the history list to the history file.
     * If there are any errors during this process, it prints an error message and the stack trace.
     * */
    public static void addToFile() {
        try {
            Writer writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream("dictionary-history/history.txt"), StandardCharsets.UTF_8));
            StringBuilder str = new StringBuilder();
            for (String word : history) {
                str.append(word).append("\n");
            }
            writer.write(str.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error writing to history file");
        }
    }
}
