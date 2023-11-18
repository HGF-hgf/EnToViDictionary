package com.example.dictionaryy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Translator {
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
