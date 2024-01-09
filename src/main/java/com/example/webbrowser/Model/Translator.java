package com.example.webbrowser.Model;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Translator {
    private final String apiKey = "737e82a9e8bef7d82e4d";
    @FXML
    private TextArea originalTextArea;
    @FXML
    private TextArea translatedTextArea;

    @FXML
    void translate(ActionEvent event) {
        String originalText = originalTextArea.getText();
        String sourceLanguage = "en"; // Assume the original text is in English
        String targetLanguage = "vi"; // Assume you want to translate to Vietnamese

        String encodedText = URLEncoder.encode(originalText, StandardCharsets.UTF_8);
        String translationBaseUrl = "https://api.mymemory.translated.net/get?q=%s&langpair=%s|%s";
        String requestUrl = String.format(translationBaseUrl, encodedText, sourceLanguage, targetLanguage);

        // Perform the translation (you may want to do this asynchronously)
        String translatedText = performTranslation(requestUrl);

        // Update the UI with the translated text
        Platform.runLater(() -> translatedTextArea.setText(translatedText));

    }

    private String performTranslation(String requestUrl) {
        // Make an HTTP request to the MyMemory Translation API and parse the response
        // You can use libraries like HttpClient or HttpURLConnection for this purpose
        // Here, we'll just simulate a successful translation for demonstration purposes
        return "Xin chào thế giới"; // Replace this with the actual translated text
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
