package com.example.webbrowser.DAO;

import com.example.webbrowser.Model.User;

import java.util.List;

public interface UserDAO {
    // Lấy thông tin người dùng theo tên đăng nhập
    User getUserByUsername(String username);

    // Lấy thông tin người dùng theo email
    User getUserByEmail(String email);

    // Lấy id_user theo tên đăng nhập hoặc email
    int getUserId(String usernameOrEmail);

    // Thêm một người dùng mới
    void addUser(User user);

    // Cập nhật thông tin người dùng
    void updateUser(User user);

    // Xóa một người dùng
    void deleteUser(User user);

    // Lấy tất cả người dùng
    List<User> getAllUsers();

    void updateUserStatus(int userId, String status);

    void updateIpAddressAndPort(int userId, String ipAddress, int portNumber);
}
