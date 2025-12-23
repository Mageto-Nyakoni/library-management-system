package nyakoni.library;

import nyakoni.library.model.*;
import nyakoni.library.ServiceLayer.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        
        // Initialize Services
        UserService userService = new UserService();
        LibraryService libraryService = new LibraryService();

        System.out.println("--- 1. TESTING LOGIN ---");
        // Try to login with one of the users from your seed data
        User currentUser = userService.login("mageto.nya@gmail.com", "password");
        
        if (currentUser != null) {
            System.out.println("Login Successful! Welcome, " + currentUser.getFullName());
        } else {
            System.out.println("Login Failed.");
            return; // Stop if login fails
        }

        System.out.println("\n--- 2. VIEWING CATALOG ---");
        List<Book> books = libraryService.getCatalog();
        for (Book b : books) {
            System.out.println(b.getBookID() + ": " + b.getTitle());
        }

        System.out.println("\n--- 3. VIEWING DETAILS & REVIEWS ---");
        // Look at "The Will of the Many" (ID 1008) which has reviews in your seed data
        libraryService.printBookDetails(1008);
    }
}