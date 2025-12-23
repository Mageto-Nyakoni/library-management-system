package nyakoni.library.ServiceLayer;

import nyakoni.library.DAO.UserDAO;
import nyakoni.library.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserDAO userDAO;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userDAO = mock(UserDAO.class);
        userService = new UserService(userDAO);
    }

    // ===== Test: Create a User (Registration) =====
    @Test
    void register_validUser_callsDaoAndReturnsTrue() {
        // Arrange
        User newUser = new User(0, "Test User", "test@example.com", "password123", "555-1234");

        when(userDAO.getUserByEmail("test@example.com")).thenReturn(null);
        when(userDAO.registerUser(newUser)).thenReturn(true);

        // Act
        boolean result = userService.register(newUser);

        // Assert
        assertTrue(result);
        verify(userDAO).getUserByEmail("test@example.com");
        verify(userDAO).registerUser(newUser);
    }

    @Test
    void register_existingEmail_failsAndDoesNotCallRegister() {
        // Arrange
        User existingUser = new User(1, "Existing User", "test@example.com", "pass", "555-0000");
        User newUser = new User(0, "New User", "test@example.com", "pass", "555-9999");

        when(userDAO.getUserByEmail("test@example.com")).thenReturn(existingUser);

        // Act
        boolean result = userService.register(newUser);

        // Assert
        assertFalse(result);
        verify(userDAO).getUserByEmail("test@example.com");
        verify(userDAO, never()).registerUser(any());
    }

    @Test
    void register_emptyEmail_fails() {
        // Arrange
        User newUser = new User(0, "Test User", "", "password123", "555-1234");

        // Act
        boolean result = userService.register(newUser);

        // Assert
        assertFalse(result);
        verify(userDAO, never()).getUserByEmail(any());
        verify(userDAO, never()).registerUser(any());
    }

    @Test
    void register_nullEmail_fails() {
        // Arrange
        User newUser = new User(0, "Test User", null, "password123", "555-1234");

        // Act
        boolean result = userService.register(newUser);

        // Assert
        assertFalse(result);
        verify(userDAO, never()).getUserByEmail(any());
        verify(userDAO, never()).registerUser(any());
    }

    @Test
    void register_daoFails_returnsFalse() {
        // Arrange
        User newUser = new User(0, "Test User", "test@example.com", "password123", "555-1234");

        when(userDAO.getUserByEmail("test@example.com")).thenReturn(null);
        when(userDAO.registerUser(newUser)).thenReturn(false);

        // Act
        boolean result = userService.register(newUser);

        // Assert
        assertFalse(result);
        verify(userDAO).registerUser(newUser);
    }

    // ===== Test: Login =====
    @Test
    void login_validCredentials_returnsUser() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";
        User expectedUser = new User(1, "Test User", email, password, "555-1234");

        when(userDAO.getUserByEmail(email.toLowerCase())).thenReturn(expectedUser);

        // Act
        User result = userService.login(email, password);

        // Assert
        assertNotNull(result);
        assertEquals(expectedUser, result);
        verify(userDAO).getUserByEmail(email.toLowerCase());
    }

    @Test
    void login_wrongPassword_returnsNull() {
        // Arrange
        String email = "test@example.com";
        String correctPassword = "password123";
        String wrongPassword = "wrongpass";
        User user = new User(1, "Test User", email, correctPassword, "555-1234");

        when(userDAO.getUserByEmail(email.toLowerCase())).thenReturn(user);

        // Act
        User result = userService.login(email, wrongPassword);

        // Assert
        assertNull(result);
        verify(userDAO).getUserByEmail(email.toLowerCase());
    }

    @Test
    void login_userNotFound_returnsNull() {
        // Arrange
        String email = "nonexistent@example.com";
        when(userDAO.getUserByEmail(email.toLowerCase())).thenReturn(null);

        // Act
        User result = userService.login(email, "password");

        // Assert
        assertNull(result);
        verify(userDAO).getUserByEmail(email.toLowerCase());
    }

    @Test
    void login_emailNormalizedToLowerCase() {
        // Arrange
        String email = "TEST@EXAMPLE.COM";
        String password = "password123";
        User user = new User(1, "Test User", "test@example.com", password, "555-1234");

        when(userDAO.getUserByEmail("test@example.com")).thenReturn(user);

        // Act
        User result = userService.login(email, password);

        // Assert
        assertNotNull(result);
        verify(userDAO).getUserByEmail("test@example.com");
    }

    @Test
    void login_whitespaceTrimmed() {
        // Arrange
        String email = "  test@example.com  ";
        String password = "  password123  ";
        User user = new User(1, "Test User", "test@example.com", "password123", "555-1234");

        when(userDAO.getUserByEmail("test@example.com")).thenReturn(user);

        // Act
        User result = userService.login(email, password);

        // Assert
        assertNotNull(result);
        verify(userDAO).getUserByEmail("test@example.com");
    }

    @Test
    void login_nullInputs_handlesGracefully() {
        // Arrange
        when(userDAO.getUserByEmail("")).thenReturn(null);

        // Act
        User result = userService.login(null, null);

        // Assert
        assertNull(result);
        verify(userDAO).getUserByEmail("");
    }
}
