package com.example.webbrowser.Controller;

import com.example.webbrowser.DAO.*;
import com.example.webbrowser.Model.*;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class WebBrowserController implements Initializable {
    @FXML
    public MenuItem historyMenuItem;
    public MenuItem chatboxMenuItem;
    public MenuItem settingMenuItem;
    public ToggleButton bookmarkToggleButton;
    public MenuItem loginMenuItem;
    public VBox chatboxVBox;
    public VBox vboxPane;
    @FXML
    public SplitPane splitPane;
    public VBox showbot;
    SearchHistoryController searchHistoryController = new SearchHistoryController();
    @FXML
    private WebView webView;
    private WebEngine webEngine;
    @FXML
    private TextField urlTextField;
    private String currentTabTitle;
    private String cookieSearchTerm = "https://www.google.com";
    private String searchTermUser = "https://www.google.com";

    private boolean isChatboxOpen = false;


    public WebBrowserController() {
        // Khởi tạo CookieManager
        CookieManager cookieManager = new CookieManager();
        // Đặt CookieManager làm CookieHandler mặc định
        CookieHandler.setDefault(cookieManager);
    }

    private boolean isLoggedIn() {
        return SessionManager.isUserLoggedIn();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Khởi tạo WebEngine
        webEngine = webView.getEngine();
        // Load trang web Google khi khởi động ứng dụng
        webEngine.load("https://www.google.com");
        // Theo dõi sự kiện thay đổi URL để cập nhật thanh nhập tìm kiếm
        webEngine.locationProperty().addListener((observableValue, oldValue, newValue) -> urlTextField.setText(newValue));
        // Thêm sự kiện onAction cho TextField
        urlTextField.setOnAction(event -> searchButtonClicked());
        webEngine.locationProperty().addListener((observableValue, oldValue, newValue) -> {
            // Kiểm tra xem URL mới có khác với URL trước đó và không phải là URL không mong muốn không
            if (!Objects.equals(oldValue, newValue) && !newValue.startsWith("https://www.google.com/url?")) {
                Platform.runLater(() -> {
                    urlTextField.setText(newValue);
                    cookieSearchTerm = newValue;
                    searchTermUser = newValue;
                });
            }
        });
        webEngine.titleProperty().addListener((observable, oldTitle, newTitle) -> TabPaneManager.setTabTitle(newTitle));
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                // Trang web đã tải xong, có thể lấy title và thực hiện các công việc khác
                currentTabTitle = webEngine.getTitle();
                // Lưu vào database ở đây
                saveSearchHistory(currentTabTitle);
                // Thêm sự kiện khi trang web được tải để kiểm tra và cập nhật trạng thái của ToggleButton
            }
        });
        // Thêm sự kiện khi nút back/forward được nhấp để kiểm tra và cập nhật trạng thái của ToggleButton
        webEngine.getHistory().currentIndexProperty().addListener((observable, oldValue, newValue) -> updateBookmarkToggleButton());
        // Cập nhật trạng thái bookmark ở đây
        if (SessionManager.isUserLoggedIn()) {
            Platform.runLater(this::updateBookmarkToggleButton);
        }

        chatboxVBox.setPrefHeight(0);
        chatboxVBox.setPrefWidth(0);
    }

    private boolean isDomain(String input) {
        // Biểu thức chính quy để kiểm tra tên miền
        String domainRegex = "^(?:https?://)?(?:www\\.)?([a-zA-Z0-9-]+\\.)+([a-zA-Z]{2,})$";
        // Kiểm tra xem chuỗi có khớp với biểu thức chính quy không
        return input.matches(domainRegex);
    }

    @FXML
    private void searchButtonClicked() {
        String searchTerm = urlTextField.getText().trim(); // Loại bỏ khoảng trắng ở đầu và cuối chuỗi
        // Kiểm tra xem đây là URL hay không
        if (isValidURL(searchTerm)) {
            webEngine.load(searchTerm);
        } else {
            // Kiểm tra xem có chứa khoảng trắng không
            if (!searchTerm.contains(" ")) {
                // Nếu không có khoảng trắng, kiểm tra xem có phải là tên miền không
                if (!isDomain(searchTerm)) {
                    // Nếu không phải là tên miền, sử dụng một công cụ tìm kiếm (ví dụ: Google)
                    searchTerm = "https://www.google.com/search?q=" + searchTerm;
                } else {
                    // Nếu là tên miền, tự động thêm "http://"
                    searchTerm = "https://" + searchTerm;
                }
            } else {
                // Nếu có khoảng trắng, sử dụng một công cụ tìm kiếm (ví dụ: Google)
                searchTerm = "https://www.google.com/search?q=" + searchTerm;
            }
            // Lấy tiêu đề từ WebEngine và đặt tiêu đề cho tab
            String title = webEngine.getTitle();
            TabPaneManager.setTabTitle(title);

            webEngine.load(searchTerm);
        }
    }

    // Kiểm tra xem chuỗi có phải là URL hay không
    private boolean isValidURL(String url) {
        if (url != null) {
            try {
                new URL(url).toURI();
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    @FXML
    private void backButtonClicked() {
        // Xử lý sự kiện nút back
        if (webEngine.getHistory().getCurrentIndex() > 0) {
            webEngine.getHistory().go(-1);
            updateBookmarkToggleButton();
        }
    }

    @FXML
    private void forwardButtonClicked() {
        // Xử lý sự kiện nút forward
        if (webEngine.getHistory().getCurrentIndex() < webEngine.getHistory().getEntries().size() - 1) {
            webEngine.getHistory().go(1);
            updateBookmarkToggleButton();
        }
    }

    @FXML
    private void openLoginDialog() {
        if (SessionManager.isUserLoggedIn()) {
            showUserInfoWindow();
        } else {
            openLoginWindow();
        }
    }

    private void openLoginWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/webbrowser/login.fxml"));
            Parent root = loader.load();
            // Tạo một Stage mới cho màn hình đăng nhập
            Stage loginStage = new Stage();
            loginStage.setTitle("Login");
            loginStage.setScene(new Scene(root));
            // Get the controller for the login dialog
            LogInController logInController = loader.getController();
            // Pass the login stage to the login controller
            logInController.setLoginStage(loginStage);
            // Pass tham chiếu của WebBrowserController đến LogInController
            logInController.setWebBrowserController();
            // Show the login dialog
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Hàm này sẽ hiển thị cửa sổ UserInfoWindow
    private void showUserInfoWindow() {
        try {
            // Tạo một FXMLLoader để tải FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/webbrowser/user-info.fxml"));
            // Tải FXML và tạo một Scene từ nó
            Scene scene = new Scene(loader.load(), 500, 200);
            // Lấy controller của FXML
            UserInfoController userInfoController = loader.getController();
            // Khởi tạo thông tin người dùng trong controller
            userInfoController.initialize();
            // Tạo một Stage mới để hiển thị cửa sổ UserInfoWindow
            Stage userInfoStage = new Stage();
            userInfoStage.setScene(scene);
            userInfoStage.setTitle("User Information");
            userInfoStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showSearchHistory() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/webbrowser/search-history.fxml"));
            Parent root = loader.load();
            Stage searchHistoryStage = new Stage();
            searchHistoryStage.setTitle("Search History");
            searchHistoryStage.setScene(new Scene(root));
            SearchHistoryController searchHistoryController = loader.getController();
            if (isLoggedIn()) {
                // Nếu đã đăng nhập, hiển thị lịch sử từ database
                List<SearchHistoryItem> searchHistoryList = getSearchHistoryFromDatabase();
                searchHistoryController.historyListView.getItems().setAll(searchHistoryList);
            } else {
                // Nếu chưa đăng nhập, hiển thị lịch sử từ cookie
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
                        searchHistoryController.historyListView.getItems().setAll(searchHistoryList); // Sử dụng setAll thay vì addAll
                        searchHistoryController.refreshHistoryListView();
                    }
                }
            }

            searchHistoryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
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

    private void saveSearchHistory(String tabTitle) {
        String searchTerm = searchTermUser;
        LocalDateTime timestamp = LocalDateTime.now();
        SearchHistoryItem searchHistoryItem = new SearchHistoryItem();
        UserDAO userDAO = new UserDAOImpl();
        int userId = userDAO.getUserId(SessionManager.getLoggedInUser());
        searchHistoryItem.setUserId(userId);
        searchHistoryItem.setSearchTerm(searchTerm);
        searchHistoryItem.setTitleTerm(tabTitle);
        searchHistoryItem.setTimestamp(timestamp);
        if (isLoggedIn()) {
            saveSearchHistoryToDatabase(searchHistoryItem);
        } else {
            saveSearchHistoryToCookie();
        }
    }

    private void saveSearchHistoryToDatabase(SearchHistoryItem searchHistoryItem) {
        HistoryDAO historyDAO = new HistoryDAOImpl();
        historyDAO.addSearchHistory(searchHistoryItem.getUserId(), searchHistoryItem.getSearchTerm(), searchHistoryItem.getTitleTerm());
    }


    private void saveSearchHistoryToCookie() {
        String searchTerm = cookieSearchTerm;
        List<HttpCookie> cookies = SharedCookieStore.getCookieStore().getCookies();
        Optional<HttpCookie> searchHistoryCookie = cookies.stream()
                .filter(cookie -> cookie.getName().equals("searchHistory"))
                .findFirst();
        List<SearchHistoryItem> searchHistoryList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (searchHistoryCookie.isPresent()) {
            // Nếu cookie tồn tại, lấy danh sách từ cookie
            String existingValue = searchHistoryCookie.get().getValue();
            String[] searchTerms = existingValue.split(",");
            for (String term : searchTerms) {
                // Phân tích từng mục từ chuỗi
                String[] parts = term.split("\\|");
                if (parts.length == 2) {
                    // Lấy searchTerm và thời gian từ chuỗi phân tích
                    String searchTermFromCookie = parts[0];
                    LocalDateTime timestampFromCookie = LocalDateTime.parse(parts[1], formatter);
                    // Thêm vào danh sách với thời gian từ cookie
                    searchHistoryList.add(new SearchHistoryItem(searchTermFromCookie, timestampFromCookie));
                }
            }
        }
        // Thêm mục mới vào danh sách và cập nhật thời gian
        SearchHistoryItem newItem = new SearchHistoryItem(searchTerm, LocalDateTime.now());
        searchHistoryList.add(newItem);
        // Chuyển đổi danh sách thành chuỗi và lưu vào cookie
        String cookieValue = searchHistoryList.stream()
                .map(item -> item.getSearchTerm() + "|" + item.getTimestamp().format(formatter))
                .collect(Collectors.joining(","));
        HttpCookie newCookie = new HttpCookie("searchHistory", cookieValue);
        SharedCookieStore.getCookieStore().add(null, newCookie);
        // Làm mới dữ liệu trong historyListView
        searchHistoryController.refreshHistoryListView();
    }

    @FXML
    private void reloadPage() {
        webEngine.reload();
        updateBookmarkToggleButton();
    }

    @FXML
    private void openChatterbox() {
        if (isLoggedIn()) {
            // Người dùng đã đăng nhập, mở cửa sổ chatterbox
            openChatterboxWindow();
        } else {
            // Người dùng chưa đăng nhập, hiển thị thông báo cần đăng nhập
            showLoginRequiredMessage();
        }
    }

    private void showLoginRequiredMessage() {
        // Tạo một Alert với kiểu INFORMATION
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText("Bạn cần đăng nhập để sử dụng tính năng này.");
        // Thêm nút OK vào alert
        alert.getButtonTypes().setAll(ButtonType.OK);
        // Thiết lập modal để ngăn chặn tương tác với các cửa sổ khác khi alert đang hiển thị
        alert.initModality(Modality.APPLICATION_MODAL);
        // Hiển thị alert và đợi cho đến khi người dùng đóng nó
        alert.showAndWait();
    }

    private void openChatterboxWindow() {
        // Tạo một đối tượng ChatClientGUI
        ChatClientGUI chatClientGUI = new ChatClientGUI();
        // Mở cửa sổ chatterbox và hiển thị danh sách người dùng
        chatClientGUI.openChatClientGUI();
    }

    @FXML
    private void openSetting() {
        try {
            // Tạo một FXMLLoader để tải FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/webbrowser/setting.fxml"));
            // Tải FXML và tạo một Scene từ nó
            Scene scene = new Scene(loader.load(), 600, 400);
            // Tạo một Stage mới để hiển thị cửa sổ Setting
            Stage settingStage = new Stage();
            settingStage.setScene(scene);
            settingStage.setTitle("Setting");
            // Khởi tạo controller của cửa sổ Setting
            SettingController settingController = loader.getController();
            // Pass tham chiếu của WebBrowserController đến SettingController
            settingController.setWebBrowserController(this);
            // Thiết lập modal để ngăn chặn tương tác với các cửa sổ khác khi cửa sổ Setting đang hiển thị
            settingStage.initModality(Modality.WINDOW_MODAL);
            // Hiển thị cửa sổ Setting và đợi cho đến khi người dùng đóng nó
            settingStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toggleBookmark() {
        // Check if the user is logged in
        if (!isLoggedIn()) {
            showLoginRequiredMessage();
            bookmarkToggleButton.setSelected(false);
            return;
        }
        // Rest of your existing code for bookmark functionality
        WebEngine webEngine = webView.getEngine();
        String currentURL = webEngine.getLocation();
        String currentTitle = webEngine.getTitle();
        // Check if the bookmark already exists in the database
        BookmarkDAO bookmarkDAO = new BookmarkDAOImpl();
        boolean isBookmarked = bookmarkDAO.bookmarkExists(SessionManager.getUserId(), currentURL);
        if (isBookmarked) {
            // Bookmark exists, get its ID and remove it from the database
            int bookmarkId = bookmarkDAO.getBookmarkId(currentURL);
            bookmarkDAO.removeBookmark(bookmarkId);
            System.out.println("Bookmark removed from the database successfully!");
        } else {
            // Bookmark does not exist, add it to the database
            bookmarkDAO.addBookmark(new Bookmark(currentURL, currentTitle));
            System.out.println("Bookmark added to the database successfully!");
        }
        // Cập nhật giao diện người dùng
        updateBookmarkToggleButton();
        // Cập nhật trạng thái bookmark cho tất cả các tab
        updateBookmarkStatusForAllTabs();
    }

    private void updateBookmarkStatusForAllTabs() {
        TabPane tabPane = (TabPane) bookmarkToggleButton.getScene().lookup("#tabPane"); // Chỉnh lại ID nếu cần
        if (tabPane != null) {
            // Cập nhật trạng thái bookmark cho từng tab
            tabPane.getTabs().forEach(this::updateBookmarkToggleButtonForTab);
        }
    }

    private void updateBookmarkToggleButtonForTab(Tab tab) {
        // Lấy nội dung của tab
        Node tabContent = tab.getContent();
        BookmarkDAOImpl bookmarkDAO = new BookmarkDAOImpl();
        // Kiểm tra xem nội dung của tab có phải là WebView không
        if (tabContent instanceof WebView tabWebView) {
            WebEngine tabWebEngine = tabWebView.getEngine();
            String tabURL = tabWebEngine.getLocation();
            // Kiểm tra trạng thái đánh dấu trực tiếp từ cơ sở dữ liệu
            boolean isTabBookmarked = bookmarkDAO.bookmarkExists(SessionManager.getUserId(), tabURL);
            // Cập nhật trạng thái của ToggleButton dựa trên việc trang web trong tab có trong danh sách bookmark hay không
            Node tabGraphic = tab.getGraphic();
            if (tabGraphic instanceof HBox hbox) {
                ToggleButton tabBookmarkToggleButton = (ToggleButton) hbox.getChildren().get(1); // Chỉnh lại nếu cần
                tabBookmarkToggleButton.setSelected(isTabBookmarked);
            }
        }
    }

    private void updateBookmarkToggleButton() {
        // Kiểm tra xem trang web hiện tại có trong cơ sở dữ liệu bookmark không
        BookmarkDAOImpl bookmarkDAO = new BookmarkDAOImpl();
        WebEngine webEngine = webView.getEngine();
        String currentURL = webEngine.getLocation();
        // Kiểm tra trực tiếp từ cơ sở dữ liệu
        boolean isBookmarked = bookmarkDAO.bookmarkExists(SessionManager.getUserId(), currentURL);
        // Cập nhật trạng thái của ToggleButton dựa trên việc trang web hiện tại có trong danh sách bookmark hay không
        bookmarkToggleButton.setSelected(isBookmarked);
    }

    public boolean isChatboxOpen() {
        return isChatboxOpen;
    }

    public void openChatterbot() {
        // Thực hiện các hành động tùy thuộc vào giá trị isChatboxOpen
        if (isChatboxOpen) {
            // Đóng chatbox
            splitPane.setDividerPositions(1.0);
            showbot.setVisible(false);
            chatboxVBox.setManaged(false);
            chatboxVBox.setVisible(false);
        } else {
            // Mở chatbox
            splitPane.setDividerPositions(0.8);
            showbot.setVisible(true);
            chatboxVBox.setManaged(true);
            chatboxVBox.setVisible(true);
        }
        isChatboxOpen = !isChatboxOpen; // Đảo ngược giá trị boolean
    }

    // Thêm phương thức để lấy SplitPane
    public SplitPane getSplitPane() {
        return splitPane;
    }

    public void setTabsController() {
    }

    public VBox getChatboxVBox() {
        return chatboxVBox;
    }
}