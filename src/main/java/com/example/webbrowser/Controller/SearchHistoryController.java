package com.example.webbrowser.Controller;

import com.example.webbrowser.DAO.HistoryDAO;
import com.example.webbrowser.DAO.HistoryDAOImpl;
import com.example.webbrowser.DAO.UserDAO;
import com.example.webbrowser.DAO.UserDAOImpl;
import com.example.webbrowser.Model.SearchHistoryItem;
import com.example.webbrowser.Model.SessionManager;
import com.example.webbrowser.Model.SharedCookieStore;
import com.example.webbrowser.Model.TabPaneManager;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.HttpCookie;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchHistoryController {
    public ListView<SearchHistoryItem> historyListView;

    public void initialize() {
        historyListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(SearchHistoryItem item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getSearchTerm() + " - " + item.getTimestamp());
                }
            }
        });

        historyListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                // Xử lý sự kiện double-click
                SearchHistoryItem selectedItem = historyListView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    String selectedUrl = selectedItem.getSearchTerm();
                    loadUrlInCurrentTab(selectedUrl);

                    // Làm mới dữ liệu trong historyListView
                    refreshHistoryListView();
                }
            }
        });
    }

    private void loadUrlInCurrentTab(String url) {
        Tab currentTab = TabPaneManager.getCurrentTab();

        if (currentTab != null) {
            WebView webView = (WebView) currentTab.getContent().lookup("#webView");
            if (webView != null) {
                WebEngine webEngine = webView.getEngine();
                webEngine.load(url);

                // Làm mới dữ liệu trong historyListView
                refreshHistoryListView();
            }
        }
    }

    public void refreshHistoryListView() {
        if (historyListView != null) {
            // Xóa tất cả các mục hiện tại
            historyListView.getItems().clear();

            if (SessionManager.isUserLoggedIn()) {
                // Nếu đã đăng nhập, lấy lại dữ liệu từ database
                List<SearchHistoryItem> searchHistoryList = getSearchHistoryFromDatabase();
                historyListView.getItems().setAll(searchHistoryList);
            } else {
                // Nếu chưa đăng nhập, lấy lại dữ liệu từ cookie
                List<HttpCookie> cookies = SharedCookieStore.getCookieStore().getCookies();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                for (HttpCookie cookie : cookies) {
                    if (cookie.getName().equals("searchHistory")) {
                        String cookieValue = cookie.getValue();
                        List<SearchHistoryItem> searchHistoryList = new ArrayList<>();

                        Arrays.stream(cookieValue.split(","))
                                .forEach(term -> {
                                    // Phân tích từng mục từ chuỗi
                                    String[] parts = term.split("\\|");

                                    if (parts.length == 2) {
                                        // Lấy searchTerm và thời gian từ chuỗi phân tích
                                        String searchTermFromCookie = parts[0];
                                        LocalDateTime timestampFromCookie = LocalDateTime.parse(parts[1], formatter);

                                        // Tạo và thêm vào danh sách một SearchHistoryItem với thời gian từ cookie
                                        searchHistoryList.add(new SearchHistoryItem(searchTermFromCookie, timestampFromCookie));
                                    }
                                });

                        // Sắp xếp danh sách từ mới nhất đến cũ nhất
                        searchHistoryList.sort((item1, item2) -> item2.getTimestamp().compareTo(item1.getTimestamp()));

                        historyListView.getItems().setAll(searchHistoryList);
                    }
                }
            }
        }
    }

    private List<SearchHistoryItem> getSearchHistoryFromDatabase() {
        // Thực hiện truy vấn để lấy lịch sử tìm kiếm từ database
        // Đây chỉ là một ví dụ, bạn cần điều chỉnh nó phù hợp với cấu trúc cơ sở dữ liệu và logic của bạn
        UserDAO userDAO = new UserDAOImpl();
        int userId = userDAO.getUserId(SessionManager.getLoggedInUser());
        HistoryDAO historyDAO = new HistoryDAOImpl();
        // Giả sử có một phương thức getSearchHistoryByUserId để lấy lịch sử theo user ID
        return historyDAO.getSearchHistoryByUserId(userId);
    }
}

