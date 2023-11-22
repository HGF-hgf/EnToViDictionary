package com.example.dictionaryy;

import javafx.concurrent.Task;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class AddWordTask extends Task<Void> {
    private final String file;
    private int wordAddedCount = 0;
    private int wordCount;

    public AddWordTask(String file) {
        this.file = file;
    }

    @Override
    protected Void call() {
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(file), StandardCharsets.UTF_8));
            String line;
            wordCount = Assist.countLines(file);
            int count = 0;
            while ((line = in.readLine()) != null) {
                if (isCancelled()) {
                    return null;
                }
                int pos = line.indexOf("\t");
                if (pos == -1) {
                    continue;
                }
                String word = line.substring(0, pos).strip();
                String meaning = line.substring(pos + 1).strip();
                if (HelloApplication.dictionary.insert(word, meaning)) {
                    wordAddedCount++;
                }
                if (count % 5 == 0) {
                    updateProgress(count, wordCount);
                }
            }
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
        return null;
    }

    @Override
    protected void succeeded() {
        System.out.println("Added " + wordAddedCount + " words");
    }

    @Override
    protected void cancelled() {
        System.out.println("Cancelled");
    }

    @Override
    protected void failed() {
        System.out.println("Failed");
    }
}
