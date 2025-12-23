package nyakoni.library.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Read connection info from environment to work with Docker easily; fall back to defaults.
    private static final String HOST = getenvOrDefault("DB_HOST", "localhost");
    private static final String PORT = getenvOrDefault("DB_PORT", "5432");
    private static final String NAME = getenvOrDefault("DB_NAME", "library_db");
    private static final String USER = getenvOrDefault("DB_USER", "myuser");
    private static final String PASSWORD = getenvOrDefault("DB_PASSWORD", "mysecretpassword");

    private static final String URL = "jdbc:postgresql://" + HOST + ":" + PORT + "/" + NAME;

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private static String getenvOrDefault(String key, String defaultValue) {
        String value = System.getenv(key);
        return (value == null || value.isEmpty()) ? defaultValue : value;
    }
}