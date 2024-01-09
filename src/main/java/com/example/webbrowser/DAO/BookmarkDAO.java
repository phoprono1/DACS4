package com.example.webbrowser.DAO;

import com.example.webbrowser.Model.Bookmark;

import java.util.List;

public interface BookmarkDAO {

    List<Bookmark> getAllBookmarks();

    List<Bookmark> getBookmarksForUser(int userId);

    void addBookmark(Bookmark bookmark);

    void removeBookmark(int bookmarkId);

    void updateBookmark(Bookmark bookmark);

    boolean bookmarkExists(int userId, String url);

    // Add this method to get the bookmark ID based on the URL
    int getBookmarkId(String url);
}
