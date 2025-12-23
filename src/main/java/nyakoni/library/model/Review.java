package nyakoni.library.model;
import java.time.LocalDate;

public class Review {
    private int reviewID;
    private String reviewText;
    private LocalDate date;

    // dependencies
    private Book reviewedBook;
    private User reviewer;

    //constructors
    public Review() {}

    public Review(int reviewID, String reviewText, LocalDate date, Book reviewedBook, User reviewer) {
        this.reviewID = reviewID;
        this.reviewText = reviewText;
        this.date = date;
        this.reviewedBook = reviewedBook;
        this.reviewer = reviewer;
    }
    //getters and setters
    public int getReviewID() { return reviewID; }
    public void setReviewID(int reviewID) { this.reviewID = reviewID; }

    public String getReviewText() { return reviewText; }
    public void setReviewText(String reviewText) { this.reviewText = reviewText; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public Book getReviewedBook() { return reviewedBook; }
    public void setReviewedBook(Book reviewedBook) { this.reviewedBook = reviewedBook; }

    public User getReviewer() { return reviewer; }
    public void setReviewer(User reviewer) { this.reviewer = reviewer; }

    @Override
    public String toString() {
        return "Review{id=" + reviewID + ", date=" + date + ", book='" + (reviewedBook != null ? reviewedBook.getTitle() : "null") + "'}";
    }
}