package com.example.dictionaryy;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javazoom.jl.player.Player;

public class TextToSpeech {
    public static void soundEnToVi(String text){
        try {
            String link = "https://translate.google.com/translate_tts?ie=UTF-8&tl="
                    + "en"
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
