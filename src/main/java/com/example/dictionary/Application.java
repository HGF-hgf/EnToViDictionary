package com.example.dictionary;

import com.example.dictionary.core.DatabaseConnection;
import com.example.dictionary.core.Dictionary;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Application extends javafx.application.Application {
    public static Dictionary dictionary;

    // This is the main entry point for all JavaFX applications. A Stage is the top-level JavaFX container.
    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Dictionary"); // Set the title of the window (stage) to "Dictionary".
        primaryStage.setResizable(false); // Make the window (stage) non-resizable.

        try {
            // Create a new FXMLLoader. It's a class that loads fxml files to get the user interface components.
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("fxml/searchpage.fxml"));

            // Create a new Scene with the UI loaded from the fxml file, and set the size of the window to 850x550 pixels.
            Scene scene = new Scene(loader.load(), 850, 550);

            // Set an action when the user requests to close the window. This action will close the dictionary, exit the platform and then exit the system.
            primaryStage.setOnCloseRequest(
                    arg0 -> {
                        dictionary.close();
                        Platform.exit();
                        System.exit(0);
                    });

            primaryStage.setScene(scene); // Set the scene to be displayed in the window (stage).
            primaryStage.show(); // Show the window (stage).
        } catch (Exception e) {
            e.printStackTrace(); // Print the stack trace for the exception.
        }

        loadDictionary(); // Load the dictionary data.
    }

    private void loadDictionary() {
        dictionary = new DatabaseConnection();
        try {
            dictionary.initialize();
            System.out.println("Dictionary loaded");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading dictionary");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

