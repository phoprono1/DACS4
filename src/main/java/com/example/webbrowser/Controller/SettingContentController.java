package com.example.webbrowser.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SettingContentController {

    @FXML
    private Label contentLabel;

    @FXML
    private VBox contentVBox;

    @FXML
    private VBox bookmarkPane;

    @FXML
    private VBox privacySecurityPane;

    public void initialize() {
        // Thiết lập hiển thị mặc định khi bắt đầu ứng dụng
        showContent();
    }

    public void showAppearanceContent() {
        showContent(bookmarkPane);
    }

    public void showPrivacySecurityContent() {
        showContent(privacySecurityPane);
    }

    private void showContent(VBox content) {
        // Ẩn tất cả các VBox của các tính năng
        bookmarkPane.setVisible(false);
        privacySecurityPane.setVisible(false);

        // Hiển thị VBox được chọn
        content.setVisible(true);

        // Xóa tất cả các nút con hiện tại của VBox
        contentVBox.getChildren().clear();

        // Thêm VBox hiển thị ở hàng đầu tiên của VBox chính
        contentVBox.getChildren().add(content);
    }

    private void showContent() {
        contentLabel.setText("Select a feature from the list on the left.");
    }
}
