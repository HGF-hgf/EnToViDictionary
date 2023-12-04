package com.example.dictionary.api;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javazoom.jl.player.Player;

public class TextToSpeech {
    // A static method that takes a string as input and plays its pronunciation.
    public static void soundEnToVi(String text){
        try {
            // Construct the URL for Google's text-to-speech service. The text is URL-encoded and the language is set to English.
            String link = "https://translate.google.com/translate_tts?ie=UTF-8&tl="
                    + "en"
                    + "&client=tw-ob&q="
                    + URLEncoder.encode(text, StandardCharsets.UTF_8);

            URL url = new URL(link); // Create a URL object with the link.

            // Open a connection to the URL.
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            // Get an InputStream from the connection.
            InputStream is = con.getInputStream();

            // Create a new Player object (from the JLayer library) with the InputStream and play the sound.
            new Player(is).play();

            con.disconnect(); // Disconnect the connection.
        } catch (Exception e) {
            e.printStackTrace(); // Print the stack trace for the exception.
            System.out.println("Error"); // Print an error message.
        }
    }

    public static void soundViToEn(String text){
        try {
            String link = "https://translate.google.com/translate_tts?ie=UTF-8&tl="
                    + "vi"
                    + "&client=tw-ob&q="
                    + URLEncoder.encode(text, StandardCharsets.UTF_8);
            URL url = new URL(link);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            InputStream is = con.getInputStream();
            new Player(is).play();
            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error");
        }
    }
}
