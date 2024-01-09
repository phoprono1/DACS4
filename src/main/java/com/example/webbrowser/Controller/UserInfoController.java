package com.example.webbrowser.Controller;

import com.example.webbrowser.DAO.UserDAO;
import com.example.webbrowser.DAO.UserDAOImpl;
import com.example.webbrowser.Model.SessionManager;
import com.example.webbrowser.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class UserInfoController {

    private final UserDAO userDAO;
    @FXML
    public Button showPasswordButton;
    public Button logoutButton;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label passwordField;
    private boolean isPasswordVisible = false;

    public UserInfoController() {
        // Khởi tạo UserDAO (hoặc UserDAOImpl) khi tạo đối tượng UserInfoController
        this.userDAO = new UserDAOImpl();
    }

    // Hàm này sẽ được gọi khi FXML được tải
    public void initialize() {
        // Lấy thông tin người dùng từ cơ sở dữ liệu
        String username = SessionManager.getLoggedInUser();
        User user = userDAO.getUserByUsername(username);

        // Kiểm tra xem user có tồn tại không
        if (user != null) {
            // Thiết lập giá trị cho các Label
            usernameLabel.setText(user.getUsername());
            emailLabel.setText(user.getEmail());
            passwordField.setText(user.getPassword());

            // Ẩn PasswordField và hiển thị Button khi load FXML
            passwordField.setVisible(false);
            showPasswordButton.setVisible(true);
        }
    }

    // Hàm này được gọi khi nhấn vào nút hiển thị mật khẩu
    @FXML
    private void showPasswordButtonClicked() {
        // Hiển thị hoặc ẩn mật khẩu và cập nhật nút
        isPasswordVisible = !isPasswordVisible;
        if (isPasswordVisible) {
            passwordField.setVisible(true);
            showPasswordButton.setText("Hide Password");
        } else {
            passwordField.setVisible(false);
            showPasswordButton.setText("Show Password");
        }
    }

    public void logoutButtonClicked(ActionEvent actionEvent) {
        // Xóa thông tin đăng nhập khi đăng xuất
        SessionManager.endSession();

        // Lấy cửa sổ hiện tại từ bất kỳ nút hoặc điều khiển nào thuộc cửa sổ đó
        Node source = (Node) actionEvent.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();

        // Đóng cửa sổ hiện tại (UserInfoWindow)
        currentStage.close();

        // Cập nhật trạng thái người dùng về "offline" trong cơ sở dữ liệu
        updateUserStatusToOffline();

        // Mở màn hình đăng nhập trong một cửa sổ mới
        openLoginWindow();
    }

    private void updateUserStatusToOffline() {
        // Lấy ID người dùng từ SessionManager
        int userId = SessionManager.getUserId();

        // Gọi phương thức để cập nhật trạng thái người dùng về "offline"
        UserDAO userDAO = new UserDAOImpl();
        userDAO.updateUserStatus(userId, "offline");
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

            // Hiển thị màn hình đăng nhập
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
