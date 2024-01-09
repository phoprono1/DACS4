package com.example.webbrowser.DAO;

import com.example.webbrowser.Model.SearchHistoryItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HistoryDAOImpl implements HistoryDAO {

    @Override
    public List<SearchHistoryItem> getSearchHistoryByUserId(int userId) {
        String sql = "SELECT * FROM search_history WHERE id_user = ? ORDER BY timestamp DESC;\n";
        List<SearchHistoryItem> searchHistoryList = new ArrayList<>();

        try (Connection connection = DatabaseConnectionDAO.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                SearchHistoryItem historyItem = extractHistoryItemFromResultSet(resultSet);
                searchHistoryList.add(historyItem);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return searchHistoryList;
    }

    @Override
    public void addSearchHistory(int userId, String searchTerm, String titleTerm) {
        String sql = "INSERT INTO search_history (id_user, searchTerm, titleTerm, timestamp) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnectionDAO.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, searchTerm);
            preparedStatement.setString(3, titleTerm);

            // Đặt timestamp cho thời điểm hiện tại
            LocalDateTime timestamp = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            preparedStatement.setString(4, timestamp.format(formatter));

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private SearchHistoryItem extractHistoryItemFromResultSet(ResultSet resultSet) throws SQLException {
        SearchHistoryItem historyItem = new SearchHistoryItem();
        historyItem.setId(resultSet.getInt("id_history"));
        historyItem.setUserId(resultSet.getInt("id_user"));
        historyItem.setSearchTerm(resultSet.getString("searchTerm"));
        historyItem.setTitleTerm(resultSet.getString("titleTerm"));

        // Chuyển đổi timestamp từ chuỗi sang LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime timestamp = LocalDateTime.parse(resultSet.getString("timestamp"), formatter);
        historyItem.setTimestamp(timestamp);

        return historyItem;
    }

    @Override
    public void clearHistoryByUserId(int userId) {
        String sql = "DELETE FROM search_history WHERE id_user = ?";

        try (Connection connection = DatabaseConnectionDAO.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
