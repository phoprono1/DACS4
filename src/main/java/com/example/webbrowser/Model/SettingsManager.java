package com.example.webbrowser.Model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class SettingsManager {
    private static final SettingsManager instance = new SettingsManager();
    // Sử dụng BooleanProperty để theo dõi chế độ tối
    private BooleanProperty darkModeProperty = new SimpleBooleanProperty(false);

    private SettingsManager() {
        // Đăng ký sự kiện để theo dõi thay đổi giá trị của darkModeProperty
        darkModeProperty.addListener((observable, oldValue, newValue) -> {
            // Gọi hàm xử lý khi chế độ tối thay đổi
            handleDarkModeChange(newValue);
        });
    }

    public static SettingsManager getInstance() {
        return instance;
    }

    public boolean isDarkMode() {
        return darkModeProperty.get();
    }

    public void setDarkMode(boolean darkMode) {
        darkModeProperty.set(darkMode);
    }

    public BooleanProperty darkModeProperty() {
        return darkModeProperty;
    }

    private void handleDarkModeChange(boolean newDarkMode) {
        // Gọi hàm xử lý khi chế độ tối thay đổi
        System.out.println("Dark Mode changed to: " + newDarkMode);
    }
}
