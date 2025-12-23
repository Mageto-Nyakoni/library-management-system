package nyakoni.library.ControllerLayer;

import java.util.Scanner;

import nyakoni.library.ServiceLayer.UserService;
import nyakoni.library.ServiceLayer.LibraryService;
import nyakoni.library.model.Book;
import nyakoni.library.model.User;

public class Menu {
    private final Scanner scanner;
    private final UserService userService;
    private final LibraryService libraryService;
    private User currentUser;

    public Menu(UserService userService) {
        this.scanner = new Scanner(System.in);
        this.userService = userService;
        this.libraryService = new LibraryService();
    }

    public void start() {
        boolean running = true;

        while (running) {
            printMainMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> handleLogin();
                case "2" -> handleRegister();
                case "0" -> {
                    System.out.println("Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void printMainMenu() {
        System.out.println("\n=== Library Management System ===");
        System.out.println("1) Login");
        System.out.println("2) Register");
        System.out.println("0) Exit");
        System.out.print("Choose an option: ");
    }

    private void handleLogin() {
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        User user = userService.login(email, password);

        if (user != null) {
            currentUser = user;
            System.out.println("Login successful! Welcome, " + user.getFullName());
            showLoggedInMenu();
        } else {
            System.out.println("Login failed.");
        }
    }

    private void handleRegister() {
        System.out.print("Library Card Number: ");
        int cardNumber = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        System.out.print("Phone (optional): ");
        String phone = scanner.nextLine().trim();

        var newUser = new nyakoni.library.model.User(
                cardNumber,
                name,
                email,
                password,
                phone);

        boolean success = userService.register(newUser);

        if (success) {
            System.out.println("Account created!");
        } else {
            System.out.println("Registration failed.");
        }
    }

    // ===== Logged-in experience =====

    private void printLoggedInMenu() {
        System.out.println("\n=== Library Menu (Logged in as " + currentUser.getFullName() + ") ===");
        System.out.println("1) View catalog");
        System.out.println("2) View book details & reviews");
        System.out.println("3) Write a review");
        // Future: add options like "My profile", etc.
        System.out.println("0) Logout");
        System.out.print("Choose an option: ");
    }

    private void showLoggedInMenu() {
        boolean loggedIn = true;

        while (loggedIn && currentUser != null) {
            printLoggedInMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> showCatalog();
                case "2" -> showBookDetails();
                case "3" -> handleWriteReview();
                case "0" -> {
                    System.out.println("Logging out...");
                    loggedIn = false;
                    currentUser = null;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void showCatalog() {
        var books = libraryService.getCatalog();

        if (books == null || books.isEmpty()) {
            System.out.println("No books found in the catalog.");
            return;
        }

        System.out.println("\n=== Catalog ===");
        for (Book b : books) {
            System.out.println(b.getBookID() + ": " + b.getTitle());
        }
    }

    private void showBookDetails() {
        System.out.print("Enter Book ID: ");
        String input = scanner.nextLine().trim();

        try {
            int bookId = Integer.parseInt(input);
            libraryService.printBookDetails(bookId);
        } catch (NumberFormatException e) {
            System.out.println("Invalid book ID. Please enter a number.");
        }
    }

    private void handleWriteReview() {
        if (currentUser == null) {
            System.out.println("You must be logged in to write a review.");
            return;
        }

        System.out.print("Enter Book ID to review: ");
        String idInput = scanner.nextLine().trim();

        int bookId;
        try {
            bookId = Integer.parseInt(idInput);
        } catch (NumberFormatException e) {
            System.out.println("Invalid book ID. Please enter a number.");
            return;
        }

        System.out.print("Write your review: ");
        String text = scanner.nextLine().trim();

        if (text.isEmpty()) {
            System.out.println("Review text cannot be empty.");
            return;
        }

        boolean success = libraryService.addReview(bookId, currentUser.getLibraryCardNumber(), text);
        if (success) {
            System.out.println("Thank you! Your review has been added.");
        }
    }
}