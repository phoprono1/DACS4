package com.example.webbrowser.Model;

import java.util.ArrayList;
import java.util.List;

public class BookmarkManager {
    private static BookmarkManager instance;
    private List<Bookmark> bookmarkList;

    private BookmarkManager() {
        bookmarkList = new ArrayList<>();
    }

    public static BookmarkManager getInstance() {
        if (instance == null) {
            instance = new BookmarkManager();
        }
        return instance;
    }

    public List<Bookmark> getBookmarkList() {
        return bookmarkList;
    }

    // Các phương thức khác để thêm, xoá, kiểm tra bookmark, tùy thuộc vào nhu cầu của bạn.
    public void addBookmark(Bookmark bookmark) {
        bookmarkList.add(bookmark);
    }

    public void removeBookmark(Bookmark bookmark) {
        bookmarkList.remove(bookmark);
    }

    public boolean containsBookmark(String url) {
        return url != null && bookmarkList.stream().anyMatch(bookmark -> url.equals(bookmark.getUrl()));
    }

    public void printAllBookmarks() {
        System.out.println("All Bookmarks:");
        bookmarkList.forEach(bookmark -> System.out.println(bookmark.getUrl()));
    }
}

