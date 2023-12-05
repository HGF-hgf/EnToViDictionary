package com.example.dictionary.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Translator {

    /**
     * this function is used to translate from English to Vietnamese
     *
     * @param text the text to be translated
     * @return the translated text
     * */

    public static String translateEnToVi(String text) {
        try {
            String langFrom = "en";
            String langTo = "vi";
            return translate(langFrom, langTo, text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error";
    }

    /**
     * this function is used to translate from Vietnamese to English
     *
     * @param text the text to be translated
     * @return the translated text
     * */
    public static String translateViToEn(String text) {
        try {
            String langFrom = "vi";
            String langTo = "en";
            return translate(langFrom, langTo, text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error";
    }

    /**
     * Translate text from `langFrom` to `langTo`.
     *
     * <p><a
     * href="https://stackoverflow.com/questions/8147284/how-to-use-google-translate-api-in-my-java-application">Reference</a>
     *
     * @param langFrom the input language (2 letters (ex: 'en'))
     * @param langTo the output language (2 letters (ex: 'vi'))
     * @param text the text to be translated
     * @return the translation text in `langTo`
     */

    private static String translate(String langFrom, String langTo, String text) throws IOException {
        // INSERT YOU URL HERE
        String urlStr = "https://script.google.com/macros/s/AKfycbwTL4hLzlwwLphia0nkbuu1TpApoPlmGBumYZrmOZnYQ2fAHNniaM38eFKdHI7fbqGaBg/exec" +
                "?q=" + URLEncoder.encode(text, StandardCharsets.UTF_8) +
                "&target=" + langTo +
                "&source=" + langFrom;
        URL url = new URL(urlStr);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
}
