package com.example.webbrowser.Controller;

import com.example.webbrowser.DAO.HistoryDAOImpl;
import com.example.webbrowser.Model.SessionManager;
import com.example.webbrowser.Model.SharedCookieStore;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.webbrowser.Model.SessionManager.isUserLoggedIn;

public class PrivacyAndSecurityController {
    public void handleClearBrowsingData() {
        if (!isUserLoggedIn()) {
            // Nếu người dùng chưa đăng nhập, xóa cookie
            SharedCookieStore.getCookieStore().removeAll();
            System.out.println("Cookies cleared.");
        } else {
            // Nếu người dùng đã đăng nhập, xóa từ database
            HistoryDAOImpl historyDAO = new HistoryDAOImpl();
            historyDAO.clearHistoryByUserId(SessionManager.getUserId());
            System.out.println("Browsing data cleared from database.");
        }
    }

    public void handleEnablePrivateMode() {
        try {
            // Tạo một FXMLLoader để tải FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/webbrowser/private-web-browser.fxml"));
            // Tải FXML và tạo một Scene từ nó
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Tạo một Stage mới để hiển thị cửa sổ chế độ riêng tư
            Stage privateModeStage = new Stage();
            privateModeStage.setScene(scene);
            privateModeStage.setTitle("Private Mode");

            // Hiển thị cửa sổ chế độ riêng tư
            privateModeStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
