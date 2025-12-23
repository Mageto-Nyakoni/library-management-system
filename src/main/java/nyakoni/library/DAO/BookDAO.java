package nyakoni.library.DAO;

import nyakoni.library.model.Author;
import nyakoni.library.model.Book;
import nyakoni.library.model.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    // GET ALL BOOKS (with Authors and Genres joined)
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        
        String sql = """
            SELECT b.BookID, b.Title, b.Text, 
                   a.AuthorID, a.AuthorName, 
                   g.GenreID, g.GenreName
            FROM books b
            JOIN authors a ON b.AuthorID = a.AuthorID
            JOIN genres g ON b.GenreID = g.GenreID
        """;

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // 1. Map Author
                Author author = new Author(
                    rs.getInt("AuthorID"),
                    rs.getString("AuthorName")
                );

                // 2. Map Genre
                Genre genre = new Genre(
                    rs.getInt("GenreID"),
                    rs.getString("GenreName")
                );

                // 3. Map Book (and inject Author/Genre)
                Book book = new Book(
                    rs.getInt("BookID"),
                    rs.getString("Title"),
                    rs.getString("Text"),
                    author,
                    genre
                );

                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
    
    // Example: GET BOOK BY ID
    public Book getBookById(int id) {
        Book book = null;
        String sql = """
            SELECT b.BookID, b.Title, b.Text, 
                   a.AuthorID, a.AuthorName, 
                   g.GenreID, g.GenreName
            FROM books b
            JOIN authors a ON b.AuthorID = a.AuthorID
            JOIN genres g ON b.GenreID = g.GenreID
            WHERE b.BookID = ?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Author author = new Author(rs.getInt("AuthorID"), rs.getString("AuthorName"));
                    Genre genre = new Genre(rs.getInt("GenreID"), rs.getString("GenreName"));
                    
                    book = new Book(
                        rs.getInt("BookID"),
                        rs.getString("Title"),
                        rs.getString("Text"),
                        author,
                        genre
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }
}
