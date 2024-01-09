package com.example.webbrowser.DAO;

import com.example.webbrowser.Model.Bookmark;
import com.example.webbrowser.Model.SessionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookmarkDAOImpl implements BookmarkDAO {

    private static final String SELECT_ALL_BOOKMARKS = "SELECT * FROM bookmarks";
    private static final String SELECT_BOOKMARKS_FOR_USER = "SELECT * FROM bookmarks WHERE id_user = ?";
    private static final String INSERT_BOOKMARK = "INSERT INTO bookmarks (id_user, url, title) VALUES (?, ?, ?)";
    private static final String DELETE_BOOKMARK = "DELETE FROM bookmarks WHERE id_bookmarks = ?";
    private static final String UPDATE_BOOKMARK = "UPDATE bookmarks SET url = ?, title = ? WHERE id_bookmarks = ?";

    @Override
    public List<Bookmark> getAllBookmarks() {
        List<Bookmark> bookmarks = new ArrayList<>();

        try (Connection connection = DatabaseConnectionDAO.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_BOOKMARKS)) {

            while (resultSet.next()) {
                Bookmark bookmark = createBookmarkFromResultSet(resultSet);
                bookmarks.add(bookmark);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookmarks;
    }

    @Override
    public List<Bookmark> getBookmarksForUser(int userId) {
        List<Bookmark> bookmarks = new ArrayList<>();

        try (Connection connection = DatabaseConnectionDAO.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOKMARKS_FOR_USER)) {

            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Bookmark bookmark = createBookmarkFromResultSet(resultSet);
                    bookmarks.add(bookmark);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookmarks;
    }

    @Override
    public void addBookmark(Bookmark bookmark) {
        try (Connection connection = DatabaseConnectionDAO.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BOOKMARK)) {

            preparedStatement.setInt(1, SessionManager.getUserId());
            preparedStatement.setString(2, bookmark.getUrl());
            preparedStatement.setString(3, bookmark.getTitle());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeBookmark(int bookmarkId) {
        try (Connection connection = DatabaseConnectionDAO.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BOOKMARK)) {

            preparedStatement.setInt(1, bookmarkId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateBookmark(Bookmark bookmark) {
        try (Connection connection = DatabaseConnectionDAO.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BOOKMARK)) {

            preparedStatement.setString(1, bookmark.getUrl());
            preparedStatement.setString(2, bookmark.getTitle());
            preparedStatement.setInt(3, bookmark.getBookmarkId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Bookmark createBookmarkFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id_bookmarks");
        int userId = resultSet.getInt("id_user");
        String url = resultSet.getString("url");
        String title = resultSet.getString("title");

        return new Bookmark(id, userId, url, title);
    }

    @Override
    public boolean bookmarkExists(int userId, String url) {
        String sql = "SELECT COUNT(*) FROM bookmarks WHERE id_user = ? AND url = ?";
        try (Connection connection = DatabaseConnectionDAO.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId); // Sử dụng setInt cho userId
            preparedStatement.setString(2, url);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately in a production environment
        }
        return false;
    }

    @Override
    public int getBookmarkId(String url) {
        String sql = "SELECT id_bookmarks FROM bookmarks WHERE url = ?";
        try (Connection connection = DatabaseConnectionDAO.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, url);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id_bookmarks");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately in a production environment
        }

        // Return -1 if the bookmark with the given URL is not found
        return -1;
    }

}

