package com.example.dictionary.ui;

import javafx.scene.control.Alert;

public class Alerts {

    /**
     * This method creates and displays an information alert with the given title and content.
     * It uses the javafx.scene.control.Alert class to create the alert.
     *
     * @param title   The title of the alert.
     * @param content The content text of the alert.
     */
    public void showAlertInfo(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);

        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText(content);

        alert.showAndWait();
    }

    /**
     * This method creates and displays an error alert with the given title and content.
     * It uses the javafx.scene.control.Alert class to create the alert.
     *
     * @param title   The title of the alert.
     * @param content The content text of the alert.
     */
    public void showAlertWarning(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);

        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText(content);

        alert.showAndWait();
    }

    /**
     * This method creates and displays a confirmation alert with the given title and content.
     * It uses the javafx.scene.control.Alert class to create the alert.
     *
     * @param title   The title of the alert.
     * @param content The content text of the alert.
     */
    public Alert alertConfirmation(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);

        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText(content);

        return alert;
    }

    /**
     * This method creates and displays an error alert with the given title and content.
     * It uses the javafx.scene.control.Alert class to create the alert.
     *
     * @param title   The title of the alert.
     * @param content The content text of the alert.
     */
    public Alert alertWarning(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);

        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText(content);

        return alert;
    }
}
