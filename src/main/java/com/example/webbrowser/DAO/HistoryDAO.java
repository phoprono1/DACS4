package com.example.webbrowser.DAO;

import com.example.webbrowser.Model.SearchHistoryItem;

import java.util.List;

public interface HistoryDAO {
    // Lấy lịch sử tìm kiếm của một người dùng
    List<SearchHistoryItem> getSearchHistoryByUserId(int userId);

    // Thêm một mục lịch sử tìm kiếm mới
    void addSearchHistory(int userId, String searchTerm, String titleTerm);

    void clearHistoryByUserId(int userId);
}

