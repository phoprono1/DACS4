package com.example.webbrowser.Controller;

import com.example.webbrowser.DAO.UserDAO;
import com.example.webbrowser.DAO.UserDAOImpl;
import com.example.webbrowser.Model.SessionManager;
import com.example.webbrowser.Model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class LogInController {

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;

    private Stage loginStage;

    public void setWebBrowserController() {
    }

    public void setLoginStage(Stage loginStage) {
        this.loginStage = loginStage;
    }

    public void loginButtonClicked() throws UnknownHostException {
        String username = usernameTextField.getText().trim();
        String password = passwordField.getText().trim();

        // Check for empty fields and provide clear messages:
        if (username.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter your username.");
            alert.showAndWait();
            return; // Exit the method if username is empty
        }

        if (password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter your password.");
            alert.showAndWait();
            return; // Exit the method if password is empty
        }

        if (isValidLogin(username, password)) {
            // Lưu trạng thái đăng nhập
            UserDAOImpl userDAO = new UserDAOImpl();
            int userId = userDAO.getUserId(username);
            SessionManager.startSession(username, userId);
            System.out.println(userId);
            // Cập nhật trạng thái user sang online
            updateUserStatus(userId);

            // Cập nhật địa chỉ IP và cổng
            updateIpAddressAndPort(userId);

            // Xử lý đăng nhập thành công, có thể mở trang chính ở đây
            System.out.println("Login successful!");

            // Hiển thị Alert khi đăng nhập thành công
            showLoginSuccessAlert();

            // Đóng cửa sổ đăng nhập
            closeLoginStage();
        } else {
            // Display an error dialog instead of printing to console
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Username or password is incorrect.");
            alert.showAndWait();
        }
    }

    private void showLoginSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Login Successful");
        alert.setHeaderText(null);
        alert.setContentText("You have successfully logged in!");

        alert.showAndWait();
    }

    private void updateIpAddressAndPort(int userId) throws UnknownHostException {
        UserDAO userDAO = new UserDAOImpl();

        // Lấy địa chỉ IP và cổng (cố định là 12345)
        InetAddress localhost = InetAddress.getLocalHost();
        String ipAddress = localhost.getHostAddress();
        int portNumber = 8887;

        // Cập nhật địa chỉ IP và cổng cho người dùng
        userDAO.updateIpAddressAndPort(userId, ipAddress, portNumber);
    }

    private void updateUserStatus(int userId) {
        UserDAO userDAO = new UserDAOImpl();
        userDAO.updateUserStatus(userId, "online");
    }

    private boolean isValidLogin(String username, String enteredPassword) {
        // Sử dụng UserDAOImpl để kiểm tra thông tin đăng nhập
        UserDAO userDAO = new UserDAOImpl();
        User user = userDAO.getUserByUsername(username);

        // In ra giá trị của id
        System.out.println("User ID: " + user.getId());

        // Lấy mật khẩu đã mã hóa từ cơ sở dữ liệu
        String hashedPasswordFromDatabase = user.getPassword();

        // Sử dụng BCrypt để so sánh mật khẩu nhập vào với mật khẩu đã mã hóa
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(enteredPassword, hashedPasswordFromDatabase);
    }

    @FXML
    private void openSignUpDialog() {
        // Load the sign-up dialog FXML
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/webbrowser/signup.fxml"));
            Parent root = loader.load();

            // Create a new stage for the sign-up dialog
            Stage signUpStage = new Stage();
            signUpStage.setTitle("Sign Up");
            signUpStage.setScene(new Scene(root));

            // Get the controller for the sign-up dialog
            SignUpController signUpController = loader.getController();

            // Pass the login stage to the sign-up controller
            signUpController.setLoginStage(loginStage);
            signUpController.setLoginController(this);

            // Show the sign-up dialog
            signUpStage.show();

            // Close the login dialog
            closeLoginStage();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeLoginStage() {
        if (loginStage != null) {
            loginStage.hide();
        }
    }
}
