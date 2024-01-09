package com.example.webbrowser.Model;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ChatClientGUI extends Application {
    private static final Logger logger = LoggerFactory.getLogger(ChatClientGUI.class);
    private final ObservableList<String> userNames = FXCollections.observableArrayList();
    private ChatServer chatServer;
    private ChatClient client;
    private FlowPane chatArea;
    private JFXTextField messageField;
    private ScrollPane scrollPane;
    private static final Set<String> openedUsernames = new HashSet<>();

    public ChatClientGUI() {
        try {
            chatServer = (ChatServer) Naming.lookup("rmi://localhost/ChatServer");
        } catch (Exception e) {
            logger.error("Error occurred: {}", e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void openChatClientGUI() {
        String loggedInUser = SessionManager.getLoggedInUser();

        if (openedUsernames.contains(loggedInUser)) {
            // Username has already opened the GUI, show a dialog
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("Dialog already opened for user: " + loggedInUser);
                alert.showAndWait();
            });
            return;
        }

        Platform.runLater(() -> {
            Stage chatClientStage = new Stage();
            start(chatClientStage);
            openedUsernames.add(loggedInUser);
        });
    }

    @Override
    public void start(Stage primaryStage) {
        // Khởi tạo và đăng ký client ở đây
        createClientWithUsername(SessionManager.getLoggedInUser());

        primaryStage.setTitle("Chat Client -" + SessionManager.getLoggedInUser());

        JFXListView<String> userList = new JFXListView<>();
        chatArea = new FlowPane();
        messageField = new JFXTextField();

        userList.setItems(userNames);

        // Handle sending messages to the group
        messageField.setOnAction(e -> {
            String message = messageField.getText();
            sendMessageToGroup(message);
            messageField.clear();
        });

        userList.setPrefWidth(150);  // Adjust the preferred width
        chatArea.setPrefWidth(400);  // Adjust the preferred width
        messageField.setPrefWidth(150);  // Adjust the preferred width

        userList.setMaxWidth(Double.MAX_VALUE);
        userList.setMaxHeight(Double.MAX_VALUE);

        chatArea.setMaxWidth(Double.MAX_VALUE);
        chatArea.setMaxHeight(Double.MAX_VALUE);

        scrollPane = new ScrollPane(chatArea);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        // Thêm ChangeListener để theo dõi sự thay đổi trong chiều dọc
        scrollPane.vvalueProperty().addListener((observable, oldValue, newValue) -> {
            // Tự động cuộn xuống khi có thêm nội dung
            if (!newValue.equals(oldValue)) {
                scrollPane.setVvalue(1.0);
            }
        });

        BorderPane root = new BorderPane();
        root.setLeft(userList);
        root.setCenter(scrollPane);  // Use the ScrollPane instead of chatArea directly
        root.setBottom(messageField);

        HBox.setHgrow(userList, Priority.NEVER);  // Don't allow narrowing
        HBox.setHgrow(chatArea, Priority.ALWAYS);  // Allow to expand
        HBox.setHgrow(messageField, Priority.NEVER);  // Don't allow narrowing

        Scene scene = new Scene(root, 600, 400);

        primaryStage.setScene(scene);

        primaryStage.setOnCloseRequest(e -> closeClient());

        primaryStage.show();

        refreshUserList();
    }

    public void updateUserList(List<String> userList) {
        Platform.runLater(() -> userNames.setAll(userList));
    }

    public void updateChatArea(String sender, String message) {
        Platform.runLater(() -> {
            String formattedMessage;
            boolean isSystemMessage = sender.equalsIgnoreCase("System");

            if (isSystemMessage) {
                formattedMessage = message;
            } else {
                formattedMessage = (sender.equals(SessionManager.getLoggedInUser()))
                        ? message
                        : sender + ": " + message;
            }

            TextFlow textFlow = new TextFlow(new Text(formattedMessage));
            textFlow.setTextAlignment(TextAlignment.JUSTIFY);

            FlowPane messagePane = new FlowPane(textFlow);
            if (isSystemMessage) {
                // Tin nhắn từ hệ thống (System)
                messagePane.setAlignment(Pos.CENTER); // Căn giữa
                textFlow.setStyle("-fx-font-style: italic;"); // In nghiêng
            } else if (sender.equals(SessionManager.getLoggedInUser())) {
                // Tin nhắn từ user hiện tại
                textFlow.setStyle("-fx-background-color: lightblue; -fx-padding: 5px; -fx-border-radius: 5px;");
                messagePane.setAlignment(Pos.BOTTOM_RIGHT);
            } else {
                // Tin nhắn từ user khác
                textFlow.setStyle("-fx-background-color: lightblue; -fx-padding: 5px; -fx-border-radius: 5px;");
                messagePane.setAlignment(Pos.BOTTOM_LEFT);
            }

            FlowPane.setMargin(textFlow, new Insets(5, 5, 5, 5));
            chatArea.getChildren().add(messagePane);

            // Scroll to the bottom whenever a new message is added
            scrollPane.setVvalue(1.0);
        });
    }

    // Method to send messages to the group
    private void sendMessageToGroup(String message) {
        if (client != null) {
            try {
                chatServer.sendGroupMessage(client, message);
                updateChatArea(SessionManager.getLoggedInUser(), message);
            } catch (RemoteException e) {
                logger.error("Error occurred: {}", e.getMessage(), e);
            }
        } else {
            logger.error("Client is null.");
        }
    }

    private void refreshUserList() {
        try {
            List<String> userList = chatServer.getConnectedClients();
            updateUserList(userList);
        } catch (Exception e) {
            logger.error("Error occurred: {}", e.getMessage(), e);
        }
    }

    private void closeClient() {
        try {
            if (client != null) {
                chatServer.unregister(client);
                String closedUsername = SessionManager.getLoggedInUser();
                openedUsernames.remove(closedUsername); // Remove username when client closes
            }
        } catch (Exception e) {
            logger.error("Error occurred during client unregister: {}", e.getMessage(), e);
        } finally {
            // Close only the specific stage associated with the JavaFX application
            Stage stage = (Stage) messageField.getScene().getWindow();
            stage.close();
        }
    }


    public void createClientWithUsername(String username) {
        try {
            client = new ChatClientImpl(username, this);
            chatServer.register(client, username);
        } catch (Exception e) {
            logger.error("Error occurred: {}", e.getMessage(), e);
        }
    }
}
