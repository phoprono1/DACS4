package com.example.webbrowser.Controller;

import com.example.webbrowser.Model.TabPaneManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.IOException;

public class PrivateTabsController {

    public Tab tab;
    @FXML
    private TabPane tabPane;

    @FXML
    private void initialize() {
        // Đặt TabPane vào TabPaneManager để quản lý
        TabPaneManager.setTabPane(tabPane);

        // Add event handler for tab selection change
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab != null && newTab.getText().equals("+")) {
                addNewTab();
            } else {
                TabPaneManager.setCurrentTab(newTab);
                assert newTab != null;
                newTab.getStyleClass().add("current-tab");
            }
        });

        // Set title for the default tab
        Tab defaultTab = tabPane.getTabs().get(0);
        TabPaneManager.setCurrentTab(defaultTab);
        TabPaneManager.setTabTitle("Default Tab Title");
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB); // Mở chế độ cho phép tắt tất cả các tab
    }

    private void addNewTab() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/webbrowser/private-tab-content.fxml"));
            Node content = loader.load();
            Tab newTab = new Tab("New Tab", content);
            tabPane.getTabs().add(tabPane.getTabs().size() - 1, newTab);

            // Lưu controller vào TabPaneManager
            newTab.getProperties().put("controller", loader.getController());

            tabPane.getSelectionModel().select(newTab); // Chọn tab mới tạo
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}