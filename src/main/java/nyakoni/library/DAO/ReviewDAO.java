package nyakoni.library.DAO;

import nyakoni.library.model.Review;
import nyakoni.library.model.Book;
import nyakoni.library.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {

    // GET REVIEWS FOR A SPECIFIC BOOK
    // We need to JOIN with Users to show WHO wrote the review
    public List<Review> getReviewsByBookId(int bookId) {
        List<Review> reviews = new ArrayList<>();
        
        String sql = """
            SELECT r.ReviewID, r.ReviewText, r.Date, 
                   u.LibraryCardNumber, u.FullName 
            FROM reviews r
            JOIN users u ON r.ReviewLibraryCardNumber = u.LibraryCardNumber
            WHERE r.ReviewBookID = ?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, bookId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // Create a "Skeleton" User (we only need their name for the review display)
                    User reviewer = new User();
                    reviewer.setLibraryCardNumber(rs.getInt("LibraryCardNumber"));
                    reviewer.setFullName(rs.getString("FullName"));

                    // Create Review
                    // Note: We aren't fetching the full Book object here to save performance, 
                    // since we already know which book we asked for.
                    Review review = new Review();
                    review.setReviewID(rs.getInt("ReviewID"));
                    review.setReviewText(rs.getString("ReviewText"));
                    review.setDate(rs.getDate("Date").toLocalDate());
                    review.setReviewer(reviewer);
                    
                    reviews.add(review);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    // ADD A NEW REVIEW
    public boolean addReview(Review review) {
        String sql = "INSERT INTO reviews (ReviewText, Date, ReviewBookID, ReviewLibraryCardNumber) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, review.getReviewText());
            pstmt.setDate(2, Date.valueOf(review.getDate()));
            pstmt.setInt(3, review.getReviewedBook().getBookID());
            pstmt.setInt(4, review.getReviewer().getLibraryCardNumber());

            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}