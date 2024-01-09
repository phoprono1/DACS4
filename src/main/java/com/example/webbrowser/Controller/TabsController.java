package com.example.webbrowser.Controller;

import com.example.webbrowser.Model.TabPaneManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class TabsController {

    public Tab tab;
    private WebBrowserController webBrowserController; // Thêm thuộc tính này

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
        // Ẩn và vô hiệu hóa default tab
        defaultTab.getContent().setVisible(false);
        defaultTab.setDisable(true);
    }

    public void addNewTab() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/webbrowser/tab-content.fxml"));
            Node content = loader.load();
            Tab newTab = new Tab("New Tab", content);
            tabPane.getTabs().add(tabPane.getTabs().size() - 1, newTab);

            // Lưu controller vào TabPaneManager
            newTab.getProperties().put("controller", loader.getController());

            // Gọi phương thức để gán giá trị cho WebBrowserController
            setWebBrowserController(loader.getController());
            webBrowserController.setTabsController(); // Đặt TabsController trong WebBrowserController


            tabPane.getSelectionModel().select(newTab); // Chọn tab mới tạo
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Phương thức để gán giá trị cho WebBrowserController
    public void setWebBrowserController(WebBrowserController webBrowserController) {
        this.webBrowserController = webBrowserController;
    }

    // Phương thức để lấy SplitPane từ WebBrowserController
    public SplitPane getSplitPaneFromWebBrowserController() {
        return webBrowserController.getSplitPane();
    }

    public VBox getVBoxFromWebBrowserController() {
        return webBrowserController.getChatboxVBox();
    }

    public boolean isChatboxOpen(){
        return webBrowserController.isChatboxOpen();
    }

    public Tab getDefaultTab() {
        return tabPane.getTabs().get(0);
    }

    public void removeDefaultTab() {
        tabPane.getTabs().remove(getDefaultTab());
    }
}
