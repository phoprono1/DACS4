package com.example.webbrowser.Controller;

import com.example.webbrowser.DAO.BookmarkDAO;
import com.example.webbrowser.DAO.BookmarkDAOImpl;
import com.example.webbrowser.Model.Bookmark;
import com.example.webbrowser.Model.SessionManager;
import com.example.webbrowser.Model.TabPaneManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.util.List;

public class BookmarkController {
    private final ObservableList<String> bookmarkItems = FXCollections.observableArrayList();
    private final BookmarkDAO bookmarkDAO = new BookmarkDAOImpl();
    public Button refreshBookmark;
    @FXML
    private ListView<String> bookmarkListView;

    public void initialize() {
        bookmarkListView.setItems(bookmarkItems);
        updateBookmarkList();
    }

    private void updateBookmarkList() {
        bookmarkItems.clear();

        // Lấy danh sách bookmark từ cơ sở dữ liệu cho người dùng hiện tại
        List<Bookmark> bookmarks = bookmarkDAO.getBookmarksForUser(SessionManager.getUserId());

        // Thêm dữ liệu mới từ danh sách bookmark
        bookmarks.forEach(bookmark -> {
            String displayText = bookmark.getTitle() != null && !bookmark.getTitle().isEmpty()
                    ? bookmark.getTitle() : bookmark.getUrl();
            bookmarkItems.add(displayText);
        });
    }


    public void refreshBookmark() {
        updateBookmarkList();
    }

    @FXML
    private void handleBookmarkClick(MouseEvent event) {
        if (event.getClickCount() == 2) { // Kiểm tra xem đúp click không
            int selectedIndex = bookmarkListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
                // Lấy bookmark tương ứng với mục đã chọn
                Bookmark selectedBookmark = bookmarkDAO.getBookmarksForUser(SessionManager.getUserId()).get(selectedIndex);

                // Load trang web của bookmark vào WebView hiện tại
                loadUrlInCurrentTab(selectedBookmark.getUrl());
            }
        }
    }

    private void loadUrlInCurrentTab(String url) {
        Tab currentTab = TabPaneManager.getCurrentTab();

        if (currentTab != null) {
            WebView webView = (WebView) currentTab.getContent().lookup("#webView");
            if (webView != null) {
                WebEngine webEngine = webView.getEngine();
                webEngine.load(url);
            }
        }
    }
}
