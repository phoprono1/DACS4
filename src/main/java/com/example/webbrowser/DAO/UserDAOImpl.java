package com.example.webbrowser.DAO;

import com.example.webbrowser.Model.User;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    @Override
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection connection = DatabaseConnectionDAO.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return extractUserFromResultSet(resultSet);
            }

        } catch (SQLException | UnknownHostException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        // Tương tự như getUserByUsername, chỉ cần thay đổi truy vấn SQL
        return null;
    }

    @Override
    public int getUserId(String usernameOrEmail) {
        String sql = "SELECT id_user FROM users WHERE username = ? OR email = ?";
        try (Connection connection = DatabaseConnectionDAO.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, usernameOrEmail);
            preparedStatement.setString(2, usernameOrEmail);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("id_user");
            } else {
                return -1; // Trả về giá trị không hợp lệ khi không tìm thấy người dùng
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; // Trả về giá trị không hợp lệ nếu có lỗi
    }

    @Override
    public void addUser(User user) {
        String sql = "INSERT INTO users (username, email, password, ip_address, port_number) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnectionDAO.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getIpAddress());
            preparedStatement.setInt(5, user.getPortNumber());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUser(User user) {
        // Tương tự như addUser, chỉ cần thay đổi truy vấn SQL
        String query = "UPDATE users SET ip_address = ?, port_number = ? WHERE id_user = ?";

        try (Connection connection = DatabaseConnectionDAO.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user.getIpAddress());
            statement.setInt(2, user.getPortNumber());
            statement.setInt(3, user.getId());

            int rowsUpdated = statement.executeUpdate();
            System.out.println("Rows updated: " + rowsUpdated);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void deleteUser(User user) {
        // Tương tự như addUser, chỉ cần thay đổi truy vấn SQL
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        List<User> userList = new ArrayList<>();

        try (Connection connection = DatabaseConnectionDAO.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                User user = extractUserFromResultSet(resultSet);
                userList.add(user);
            }

        } catch (SQLException | UnknownHostException e) {
            e.printStackTrace();
        }

        return userList;
    }

    private User extractUserFromResultSet(ResultSet resultSet) throws SQLException, UnknownHostException {
        User user = new User();
        user.setId(resultSet.getInt("id_user"));
        user.setUsername(resultSet.getString("username"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setStatus(resultSet.getString("status"));

        // Lấy địa chỉ IP của máy
        InetAddress localhost = InetAddress.getLocalHost();
        user.setIpAddress(localhost.getHostAddress());
        System.out.println("IP Address: " + localhost.getHostAddress());


        // Set giá trị cổng cố định
        user.setPortNumber(8887);
        System.out.println("Port Number: " + user.getPortNumber());
        return user;
    }


    @Override
    public void updateUserStatus(int userId, String status) {
        String query = "UPDATE users SET status = ? WHERE id_user = ?";

        try (Connection connection = DatabaseConnectionDAO.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, status);
            statement.setInt(2, userId);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateIpAddressAndPort(int userId, String ipAddress, int portNumber) {
        String query = "UPDATE users SET ip_address = ?, port_number = ? WHERE id_user = ?";

        try (Connection connection = DatabaseConnectionDAO.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, ipAddress);
            statement.setInt(2, portNumber);
            statement.setInt(3, userId);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
