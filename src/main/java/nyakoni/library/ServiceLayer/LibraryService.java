package nyakoni.library.ServiceLayer;

import nyakoni.library.DAO.BookDAO;
import nyakoni.library.DAO.ReviewDAO;
import nyakoni.library.model.Book;
import nyakoni.library.model.Review;

import java.time.LocalDate;

import java.util.List;

public class LibraryService {

    private BookDAO bookDAO;
    private ReviewDAO reviewDAO;

    // Constructor for dependency injection (testing)
    public LibraryService(BookDAO bookDAO, ReviewDAO reviewDAO) {
        this.bookDAO = bookDAO;
        this.reviewDAO = reviewDAO;
    }

    // Default constructor for production use
    public LibraryService() {
        this.bookDAO = new BookDAO();
        this.reviewDAO = new ReviewDAO();
    }

    // FEATURE 1: Get the full catalog
    public List<Book> getCatalog() {
        return bookDAO.getAllBooks();
    }

    // FEATURE 2: Get a book AND its reviews
    // This is a "Service" job: calling two different DAOs to build one complete
    // view
    public void printBookDetails(int bookId) {
        Book book = bookDAO.getBookById(bookId);

        if (book != null) {
            System.out.println("\n=== BOOK DETAILS ===");
            System.out.println("Title: " + book.getTitle());
            System.out.println("Author: " + book.getAuthor().getAuthorName());
            System.out.println("Genre: " + book.getGenre().getGenreName());
            System.out
                    .println("Summary: " + book.getText().substring(0, Math.min(book.getText().length(), 100)) + "...");

            // Now fetch reviews for this specific book
            List<Review> reviews = reviewDAO.getReviewsByBookId(bookId);
            System.out.println("\n--- Reviews (" + reviews.size() + ") ---");

            for (Review r : reviews) {
                System.out.println(r.getReviewer().getFullName() + ": " + r.getReviewText());
            }
        } else {
            System.out.println("Book not found!");
        }
    }

    // FEATURE 3: Add a new review for a book
    public boolean addReview(int bookId, int libraryCardNumber, String reviewText) {
        Book book = bookDAO.getBookById(bookId);
        if (book == null) {
            System.out.println("Book not found.");
            return false;
        }

        Review review = new Review();
        review.setReviewText(reviewText);
        review.setDate(LocalDate.now());
        review.setReviewedBook(book);

        nyakoni.library.model.User reviewer = new nyakoni.library.model.User();
        reviewer.setLibraryCardNumber(libraryCardNumber);
        review.setReviewer(reviewer);

        boolean success = reviewDAO.addReview(review);
        if (!success) {
            System.out.println("Could not save review.");
        }
        return success;
    }
}
