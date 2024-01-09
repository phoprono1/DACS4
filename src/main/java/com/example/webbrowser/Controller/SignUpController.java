package com.example.webbrowser.Controller;

import com.example.webbrowser.DAO.UserDAOImpl;
import com.example.webbrowser.Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class SignUpController {

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    private Stage loginStage;

    private LogInController loginController;

    public void setLoginController(LogInController loginController) {
        this.loginController = loginController;
    }

    public void setLoginStage(Stage loginStage) {
        this.loginStage = loginStage;
    }

    @FXML
    private void signUpButtonClicked() {
        String username = usernameTextField.getText();
        String email = emailTextField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("All fields must be filled.");
            return;
        }

        if (!isValidEmail(email)) {
            showAlert("Invalid email format. Please use the format example@example.com");
            return;
        }

        if (password.length() < 8) {
            showAlert("Password must be at least 8 characters long.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert("Passwords do not match.");
            return;
        }

        // Mã hóa mật khẩu trước khi lưu vào cơ sở dữ liệu
        String hashedPassword = hashPassword(password);

        // Create a User object
        User newUser = new User(username, email, hashedPassword);

        // Use UserDAOImpl to add the user
        UserDAOImpl userDAO = new UserDAOImpl();
        userDAO.addUser(newUser);

        // Show a success message
        showAlert("Sign up successful!");

        // Close the sign-up dialog
        closeSignUpStage();

        // Open the login dialog using the LogInController instance
        if (loginController != null) {
            loginStage.show();
        }
    }

    private boolean isValidEmail(String email) {
        // Biểu thức chính quy kiểm tra định dạng email
        String emailRegex = "^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,4}$";
        return email.matches(emailRegex);
    }

    private String hashPassword(String password) {
        // Sử dụng BCrypt để mã hóa mật khẩu
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }


    @FXML
    private void openLoginDialog() {
        // Close the sign-up dialog
        closeSignUpStage();

        // Open the login dialog
        if (loginStage != null) {
            loginStage.show();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeSignUpStage() {
        Stage stage = (Stage) usernameTextField.getScene().getWindow();
        stage.close();
    }
}
