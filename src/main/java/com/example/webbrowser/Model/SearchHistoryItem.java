package com.example.webbrowser.Model;

import java.time.LocalDateTime;

public class SearchHistoryItem {
    private int id;
    private int userId;
    private String searchTerm;
    private String titleTerm;
    private LocalDateTime timestamp;

    public SearchHistoryItem(int id, int userId, String searchTerm, String titleTerm, LocalDateTime timestamp) {
        this.id = id;
        this.userId = userId;
        this.searchTerm = searchTerm;
        this.titleTerm = titleTerm;
        this.timestamp = timestamp;
    }

    public SearchHistoryItem(String searchTerm, LocalDateTime timestamp) {
        this.searchTerm = searchTerm;
        this.timestamp = timestamp;
    }

    public SearchHistoryItem() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public String getTitleTerm() {
        return titleTerm;
    }

    public void setTitleTerm(String titleTerm) {
        this.titleTerm = titleTerm;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
