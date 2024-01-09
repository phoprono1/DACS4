package com.example.webbrowser.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class SettingController {

    // ObservableList để lưu trữ dữ liệu cho ListView
    private final ObservableList<String> featureData = FXCollections.observableArrayList();
    @FXML
    private ListView<String> featureList;
    @FXML
    private VBox contentVBox;

    public void initialize() {
        // Gán ObservableList cho ListView
        featureList.setItems(featureData);

        // Thêm các mục mẫu khi khởi tạo
        featureData.add("Bookmark");
        featureData.add("Privacy and Security");
    }

    @FXML
    private void onFeatureSelected() {
        String selectedFeature = featureList.getSelectionModel().getSelectedItem();
        if (selectedFeature != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/webbrowser/setting-content.fxml"));
                VBox content = loader.load();
                SettingContentController contentController = loader.getController();

                // Gọi phương thức hiển thị nội dung tương ứng với tính năng được chọn
                switch (selectedFeature) {
                    case "Bookmark" -> contentController.showAppearanceContent();
                    case "Privacy and Security" -> contentController.showPrivacySecurityContent();
                    default -> {
                    }
                    // Nếu có thêm tính năng mới, bạn có thể xử lý ở đây
                }

                // Thay đổi nội dung trong contentVBox
                contentVBox.getChildren().setAll(content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Phương thức setter cho WebBrowserController
    public void setWebBrowserController(WebBrowserController webBrowserController) {
    }
}
