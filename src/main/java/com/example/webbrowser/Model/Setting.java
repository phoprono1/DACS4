package com.example.webbrowser.Model;

public class Setting {
    private boolean darkMode;

    public Setting() {
        this.darkMode = darkMode;
    }

    public boolean isDarkMode() {
        return darkMode;
    }

    public void setDarkMode(boolean darkMode) {
        this.darkMode = darkMode;
    }

    // Thêm getter và setter cho enableTracking
    public boolean isEnableTracking() {
        return darkMode; // Thay đổi logic nếu cần thiết
    }

    public void setEnableTracking(boolean enableTracking) {
        this.darkMode = enableTracking; // Thay đổi logic nếu cần thiết
    }
}
