package com.example.dictionaryy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FilterReader;
import java.io.IOException;
import java.nio.CharBuffer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

public class Assist {
    // Create a string with num spaces
    public static String createStringSpace(int num) {
        // If num < 0, return a string with 0 spaces
        if (num < 0) {
            num = 0;
        }
        // Create a string with num spaces and replace all spaces with '\0' character
        return CharBuffer.allocate(num).toString().replace('\0', ' ');
    }

    // Create a string with num '-' characters
    public static String createLine(int num) {
        // If num < 0, return a string with 0 spaces
        if (num < 0) {
            num = 0;
        }
        // Create a string with num spaces and replace all spaces with '-' character
        return CharBuffer.allocate(num).toString().replace('\0', '-');
    }

    public static String htmlToText(String html) {
        Document document = Jsoup.parse(html);
        Element body = document.body();

        return buildStringFromNode(body).toString();
    }

    private static StringBuffer buildStringFromNode(Node node) {
        StringBuffer buffer = new StringBuffer();
        if (node instanceof TextNode) {
            buffer.append(((TextNode) node).text().trim());
        }
        for (Node childNode : node.childNodes()) {
            buffer.append(buildStringFromNode(childNode));
        }

        if (node instanceof Element element){
            String tagName = element.tagName();
            // Add a new line if the tag is <p> or <br>
            if (tagName.equals("p") || tagName.equals("br")) {
                buffer.append("\n");
            }
        }
        return buffer;
    }

    public static int countLines(String text) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(text));
            int lines = 0;
            while (reader.readLine() != null) {
                lines++;
            }
            reader.close();
            return lines;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
