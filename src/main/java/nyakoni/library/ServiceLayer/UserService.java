package nyakoni.library.ServiceLayer;

import nyakoni.library.DAO.UserDAO;
import nyakoni.library.model.User;

public class UserService {

    private UserDAO userDAO = new UserDAO();

    // FEATURE 1: Login Logic
    public User login(String email, String password) {
        // Normalize input to avoid common whitespace/casing errors
        String normalizedEmail = email == null ? "" : email.trim().toLowerCase();
        String normalizedPassword = password == null ? "" : password.trim();

        // 1. Ask DAO for the user with this email
        User user = userDAO.getUserByEmail(normalizedEmail);

        // 2. Validate: Does user exist? Does password match?
        if (user != null && user.getPassword().equals(normalizedPassword)) {
            return user; // Login successful
        }

        return null; // Login failed
    }

    // FEATURE 2: Registration Logic
    public boolean register(User newUser) {
        // 1. Validation: Check if email is empty
        if (newUser.getEmail() == null || newUser.getEmail().isEmpty()) {
            System.out.println("Registration failed: Email is required.");
            return false;
        }

        // 2. Validation: Check if user already exists
        if (userDAO.getUserByEmail(newUser.getEmail()) != null) {
            System.out.println("Registration failed: Email already taken.");
            return false;
        }

        // 3. If valid, ask DAO to save
        return userDAO.registerUser(newUser);
    }
}