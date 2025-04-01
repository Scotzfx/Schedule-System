  package schedsys;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController implements Initializable {

    @FXML
    private TextField fullName, emailAddress, password;
   

    // Database connection details (No username & password)
    private static final String URL = "jdbc:mysql://localhost:3306/schedulesystem";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialization logic if needed
    }

    @FXML
    private void registerUser() {
        String name = fullName.getText();
        String email = emailAddress.getText();
        String pass = password.getText();

        if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

         try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "INSERT INTO users (full_name, email, password) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, pass);
            stmt.executeUpdate();
            showAlert("Success", "Registration successful!");
        } catch (SQLException e) {
            showAlert("Database Error", e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
