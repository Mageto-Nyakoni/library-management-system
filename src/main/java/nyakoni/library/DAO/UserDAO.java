package nyakoni.library.DAO;

import nyakoni.library.model.User;
import java.sql.*;

public class UserDAO {

    // 1. REGISTER NEW USER
    public boolean registerUser(User user) {
        String sql = "INSERT INTO users (LibraryCardNumber, FullName, Email, Password, PhoneNumber) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, user.getLibraryCardNumber());
            pstmt.setString(2, user.getFullName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getPassword()); // In a real app, hash this password first!
            pstmt.setString(5, user.getPhoneNumber());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Returns true if insert was successful

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 2. LOGIN (Get User by Email)
    public User getUserByEmail(String email) {
        User user = null;
        String sql = "SELECT * FROM users WHERE Email = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = new User(
                        rs.getInt("LibraryCardNumber"),
                        rs.getString("FullName"),
                        rs.getString("Email"),
                        rs.getString("Password"),
                        rs.getString("PhoneNumber")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}