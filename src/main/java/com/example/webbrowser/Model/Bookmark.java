package com.example.webbrowser.Model;

import java.util.Objects;

public class Bookmark {
    private int bookmarkId; // Add the bookmarkId field

    private int userId;
    private String url;
    private String title;

    public Bookmark(int bookmarkId, int userId, String url, String title) {
        this.bookmarkId = bookmarkId;
        this.userId = userId;
        this.url = url;
        this.title = title;
    }

    public Bookmark(String currentURL, String title) {
        this.url = currentURL;
        this.title = title;
    }

    public int getBookmarkId() {
        return bookmarkId;
    }

    public void setBookmarkId(int bookmarkId) {
        this.bookmarkId = bookmarkId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bookmark bookmark = (Bookmark) o;
        return bookmarkId == bookmark.bookmarkId &&
                Objects.equals(url, bookmark.url) &&
                Objects.equals(title, bookmark.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookmarkId, url, title);
    }

    @Override
    public String toString() {
        return "Bookmark{" +
                "bookmarkId=" + bookmarkId +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
