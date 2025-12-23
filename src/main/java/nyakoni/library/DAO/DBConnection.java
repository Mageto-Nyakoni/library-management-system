package nyakoni.library.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    
    private static final String URL = "jdbc:postgresql://localhost:5432/library_db";
    private static final String USER = "myuser";
    private static final String PASSWORD = "mysecretpassword";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}