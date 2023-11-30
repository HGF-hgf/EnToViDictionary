package com.example.dictionary.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DictionaryManagement {
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
