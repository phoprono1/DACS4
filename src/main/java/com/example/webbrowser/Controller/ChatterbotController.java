package com.example.webbrowser.Controller;

import com.example.webbrowser.Model.SessionManager;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

public class ChatterbotController {
    public TextFlow chatArea;  // Thay thế JFXTextArea bằng TextFlow
    public JFXTextField messageTextField;

    public ScrollPane scrollPane;


    public void initialize() {
        // Thiết lập sự kiện tự động cuộn khi có nội dung mới được thêm vào TextFlow
        chatArea.heightProperty().addListener((obs, oldVal, newVal) -> scrollPane.setVvalue(1.0));
    }

    public void sendMessage() {
        // Nhận nội dung từ TextField
        String userInput = messageTextField.getText();

        // Tạo chuỗi JSON từ dữ liệu người dùng nhập
        String jsonBody = String.format("{\"contents\":[{\"parts\":[{\"text\":\"%s\"}]}]}", userInput);

        // URL của API và key API
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=AIzaSyByxt4AS1KcBN2Yl82JfESbbp_jilRQk_I";

        // Tạo yêu cầu HTTP
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        // Sử dụng HttpClient để thực hiện yêu cầu
        HttpClient client = HttpClient.newHttpClient();

        try {
            // Nhận phản hồi từ API
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Hiển thị mã trạng thái và nội dung phản hồi trong TextArea
            String chatbotResponse = extractTextFromJson(response.body());

            // Hiển thị dòng người dùng nhập và dòng phản hồi của Chatbot trong TextFlow
            Platform.runLater(() -> {
                // Hiển thị dòng người dùng nhập và dòng phản hồi của Chatbot trong TextFlow
                Text userText;
                if (SessionManager.isUserLoggedIn()) {
                    userText = new Text(SessionManager.getLoggedInUser() + ": " + userInput + "\n");
                } else {
                    userText = new Text("User: " + userInput + "\n");
                }
                Text botText = new Text("Chatbot: " + chatbotResponse + "\n");
                chatArea.getChildren().addAll(userText, botText);
                botText.setStyle("-fx-fill: darkblue;"); // Thay màu blue bằng màu bạn muốn
                messageTextField.clear();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String extractTextFromJson(String jsonResponse) {
        try {
            // Chuyển đổi JSON thành đối tượng JSONObject
            JSONObject jsonObject = new JSONObject(jsonResponse);

            // Lấy mảng candidates từ đối tượng JSONObject
            JSONArray candidates = jsonObject.getJSONArray("candidates");

            // Lấy phần text từ đối tượng trong mảng candidates
            return candidates.getJSONObject(0)
                    .getJSONObject("content")
                    .getJSONArray("parts")
                    .getJSONObject(0)
                    .getString("text");
        } catch (Exception e) {
            e.printStackTrace();
            return "Error extracting text from JSON";
        }
    }
}
