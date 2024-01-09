package com.example.webbrowser.Model;

public class SessionManager {
    private static String loggedInUser;

    private static int userID;

    public static void startSession(String username, int id_user) {
        loggedInUser = username;
        userID = id_user; // Thêm dòng này để lưu thông tin id_user khi đăng nhập
    }

    public static void endSession() {
        loggedInUser = null;
    }

    public static boolean isUserLoggedIn() {
        return loggedInUser != null;
    }

    public static String getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(String username) {
        loggedInUser = username;
    }

    public static int getUserId() {
        return userID;
    }

    // Thêm phương thức để đặt giá trị cho userID
    public static void setUserId(int id) {
        userID = id;
    }
}

