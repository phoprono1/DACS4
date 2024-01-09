package com.example.webbrowser;

import com.example.webbrowser.Controller.TabsController;
import com.example.webbrowser.Controller.WebBrowserController;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WebBrowserApplication extends Application {
    public static void main(String[] args) {
        // Chạy ứng dụng JavaFX
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(WebBrowserApplication.class.getResource("web-browser.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1100, 600);
        stage.setTitle("PPBrowser");
        stage.setScene(scene);
        stage.setResizable(true);

        stage.maximizedProperty().addListener((observable, oldValue, newValue) -> handleMaximizedChange(newValue, fxmlLoader));

        TabsController tabsController = fxmlLoader.getController();
        tabsController.addNewTab();
        tabsController.removeDefaultTab();

        stage.show();
        stage.setOnCloseRequest(event -> System.exit(0));

    }

    private void handleMaximizedChange(boolean isMaximized, FXMLLoader fxmlLoader) {
        System.out.println("Cửa sổ đã được " + (isMaximized ? "maximize." : "restore từ maximize."));

        TabsController tabsController = fxmlLoader.getController();
        if (tabsController.isChatboxOpen()) {
            tabsController.getSplitPaneFromWebBrowserController().setDividerPositions(0.8);
            tabsController.getVBoxFromWebBrowserController().setVisible(true);
            tabsController.getVBoxFromWebBrowserController().setManaged(true);
        } else {
            tabsController.getSplitPaneFromWebBrowserController().setDividerPositions(1.0);
            tabsController.getVBoxFromWebBrowserController().setVisible(false);
            tabsController.getVBoxFromWebBrowserController().setManaged(false);
        }
    }
}
