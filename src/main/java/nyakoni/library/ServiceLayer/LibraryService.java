package nyakoni.library.ServiceLayer;

import nyakoni.library.DAO.BookDAO;
import nyakoni.library.DAO.ReviewDAO;
import nyakoni.library.model.Book;
import nyakoni.library.model.Review;

import java.util.List;

public class LibraryService {

    private BookDAO bookDAO = new BookDAO();
    private ReviewDAO reviewDAO = new ReviewDAO();

    // FEATURE 1: Get the full catalog
    public List<Book> getCatalog() {
        return bookDAO.getAllBooks();
    }

    // FEATURE 2: Get a book AND its reviews
    // This is a "Service" job: calling two different DAOs to build one complete view
    public void printBookDetails(int bookId) {
        Book book = bookDAO.getBookById(bookId);
        
        if (book != null) {
            System.out.println("\n=== BOOK DETAILS ===");
            System.out.println("Title: " + book.getTitle());
            System.out.println("Author: " + book.getAuthor().getAuthorName());
            System.out.println("Genre: " + book.getGenre().getGenreName());
            System.out.println("Summary: " + book.getText().substring(0, Math.min(book.getText().length(), 100)) + "...");
            
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
}
