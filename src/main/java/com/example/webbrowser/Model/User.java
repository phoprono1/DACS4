package com.example.webbrowser.Model;

public class User {
    private int id; // Thêm trường id
    private String username;
    private String email;
    private String password;
    private String status;
    private String ipAddress; // Add this field
    private int portNumber;    // Add this field

    public User(String username, String email, String password, String status, String ipAddress, int portNumber) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.status = status;
        this.ipAddress = ipAddress;
        this.portNumber = portNumber;
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Thêm constructor mới để khởi tạo User với id
    public User(int id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User() {

    }

    public User(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter methods
    public String getUsername() {
        return username;
    }

    // Setter methods (if needed)
    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Thêm phương thức để lấy trạng thái từ cơ sở dữ liệu
    public String getStatus() {
        return status;
    }

    // Thêm phương thức để đặt trạng thái
    public void setStatus(String status) {
        this.status = status;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }
}