package com.example.dictionary;

import com.example.dictionary.core.DatabaseConnection;
import com.example.dictionary.core.Dictionary;
import com.example.dictionary.core.History;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class Application extends javafx.application.Application {
    public static Dictionary dictionary;

    /**
     * This method is the main entry point for all JavaFX applications.
     * It sets the title and icon of the primary stage, makes it non-resizable, and sets an action to close the dictionary and exit the system when the user requests to close the window.
     * It also loads the user interface from the 'searchpage.fxml' file, sets the scene to be displayed in the window, and shows the window.
     * Finally, it loads the dictionary data.
     *
     * @param primaryStage The primary stage for this application, onto which the application scene can be set.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Dictionary"); // Set the title of the window (stage) to "Dictionary".
        primaryStage.setResizable(false); // Make the window (stage) non-resizable.
        primaryStage.getIcons().add(new javafx.scene.image.Image(Objects.requireNonNull(Application.class.getResourceAsStream("icons/app.png"))));

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

    /**
     * This method loads the dictionary data from the database.
     */
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

    /**
     * This method is the entry point of the application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        History.loadHistory();
        launch(args);
    }
}

