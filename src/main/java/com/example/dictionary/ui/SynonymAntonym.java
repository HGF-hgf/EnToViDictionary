package com.example.dictionary.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

public class SynonymAntonym {
    private static String word;

    @FXML
    private ListView<String> synonymListView;
    @FXML
    private ListView<String> antonymListView;

    public static void setWord(String word) {
        SynonymAntonym.word = word;
    }
    @FXML
    public void initialize() {
        setSynonymListView();
        setAntonymListView();
    }

    public void setSynonymListView() {
       try {
           CloseableHttpClient httpClient = HttpClients.createDefault();
           HttpGet httpGet = new HttpGet("https://api.datamuse.com/words?rel_syn=" + word);
           CloseableHttpResponse response = httpClient.execute(httpGet);

           String json = EntityUtils.toString(response.getEntity());
           JsonArray jsonArray = JsonParser.parseString(json).getAsJsonArray();

           for (int i = 0; i < jsonArray.size(); i++) {
               JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
               String synonym = jsonObject.get("word").getAsString();
                if (synonym.contains(" ")) {
                     synonym = synonym.replace(" ", "-");
                }
               synonymListView.getItems().add(synonym);
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
    }

    public void setAntonymListView() {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet("https://api.datamuse.com/words?rel_ant=" + SynonymAntonym.word);
            CloseableHttpResponse response = httpClient.execute(httpGet);

            String json = EntityUtils.toString(response.getEntity());
            JsonArray jsonArray = JsonParser.parseString(json).getAsJsonArray();

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                String antonym = jsonObject.get("word").getAsString();
                if (antonym.contains(" ")) {
                    antonym = antonym.replace(" ", "-");
                }
                antonymListView.getItems().add(antonym);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
