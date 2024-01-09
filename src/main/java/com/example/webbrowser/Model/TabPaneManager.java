package com.example.webbrowser.Model;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class TabPaneManager {

    private static TabPane tabPane;
    private static Tab currentTab;

    public static TabPane getTabPane() {
        return tabPane;
    }

    public static void setTabPane(TabPane pane) {
        tabPane = pane;
    }

    public static void setTabTitle(String title) {
        if (currentTab != null) {
            currentTab.setText(title);
        }
    }

    public static String getTabTitle(Tab tab) {
        return tab.getText();
    }

    public static Tab getCurrentTab() {
        return currentTab;
    }

    public static void setCurrentTab(Tab tab) {
        currentTab = tab;
    }
}
