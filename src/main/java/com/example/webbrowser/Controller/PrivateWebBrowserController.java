package com.example.webbrowser.Controller;

import com.example.webbrowser.DAO.*;
import com.example.webbrowser.Model.*;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class PrivateWebBrowserController implements Initializable {
    @FXML
    public MenuItem historyMenuItem;
    public MenuItem chatboxMenuItem;
    public MenuItem settingMenuItem;
    public ToggleButton bookmarkToggleButton;
    public MenuItem loginMenuItem;
    @FXML
    private WebView webView;
    private WebEngine webEngine;
    @FXML
    private TextField urlTextField;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Khởi tạo WebEngine
        webEngine = webView.getEngine();
        // Load trang web Google khi khởi động ứng dụng
        webEngine.load("https://www.google.com");
        // Theo dõi sự kiện thay đổi URL để cập nhật thanh nhập tìm kiếm
        webEngine.locationProperty().addListener((observableValue, oldValue, newValue) -> urlTextField.setText(newValue));
        // Thêm sự kiện onAction cho TextField
        urlTextField.setOnAction(event -> searchButtonClicked());
        webEngine.locationProperty().addListener((observableValue, oldValue, newValue) -> {
            // Kiểm tra xem URL mới có khác với URL trước đó và không phải là URL không mong muốn không
            if (!Objects.equals(oldValue, newValue) && !newValue.startsWith("https://www.google.com/url?")) {
                Platform.runLater(() -> {
                    urlTextField.setText(newValue);
                });
            }
        });
        webEngine.titleProperty().addListener((observable, oldTitle, newTitle) -> TabPaneManager.setTabTitle(newTitle));
    }

    private boolean isDomain(String input) {
        // Biểu thức chính quy để kiểm tra tên miền
        String domainRegex = "^(?:https?://)?(?:www\\.)?([a-zA-Z0-9-]+\\.)+([a-zA-Z]{2,})$";
        // Kiểm tra xem chuỗi có khớp với biểu thức chính quy không
        return input.matches(domainRegex);
    }

    @FXML
    private void searchButtonClicked() {
        String searchTerm = urlTextField.getText().trim();
        // Kiểm tra xem đây là URL hay không
        if (isValidURL(searchTerm)) {
            webEngine.load(searchTerm);
        } else {
            // Kiểm tra xem có chứa khoảng trắng không
            if (!searchTerm.contains(" ")) {
                // Nếu không có khoảng trắng, kiểm tra xem có phải là tên miền không
                if (!isDomain(searchTerm)) {
                    // Nếu không phải là tên miền, sử dụng một công cụ tìm kiếm (ví dụ: Google)
                    searchTerm = "https://www.google.com/search?q=" + searchTerm;
                } else {
                    // Nếu là tên miền, tự động thêm "http://"
                    searchTerm = "https://" + searchTerm;
                }
            } else {
                // Nếu có khoảng trắng, sử dụng một công cụ tìm kiếm (ví dụ: Google)
                searchTerm = "https://www.google.com/search?q=" + searchTerm;
            }
            // Lấy tiêu đề từ WebEngine và đặt tiêu đề cho tab
            String title = webEngine.getTitle();
            TabPaneManager.setTabTitle(title);

            webEngine.load(searchTerm);
        }
    }

    // Kiểm tra xem chuỗi có phải là URL hay không
    private boolean isValidURL(String url) {
        if (url != null) {
            try {
                new URL(url).toURI();
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    @FXML
    private void backButtonClicked() {
        // Xử lý sự kiện nút back
        if (webEngine.getHistory().getCurrentIndex() > 0) {
            webEngine.getHistory().go(-1);
        }
    }

    @FXML
    private void forwardButtonClicked() {
        // Xử lý sự kiện nút forward
        if (webEngine.getHistory().getCurrentIndex() < webEngine.getHistory().getEntries().size() - 1) {
            webEngine.getHistory().go(1);
        }
    }

    @FXML
    private void reloadPage() {
        webEngine.reload();
    }
}