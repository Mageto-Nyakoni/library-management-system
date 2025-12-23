package nyakoni.library.model;

public class Book {
    private int bookID;
    private String title;
    private String text; // The large text content
    
    // dependencies
    private Author author;
    private Genre genre;

    //constructors
    public Book() {}

    public Book(int bookID, String title, String text, Author author, Genre genre) {
        this.bookID = bookID;
        this.title = title;
        this.text = text;
        this.author = author;
        this.genre = genre;
    }

    public int getBookID() { return bookID; }
    public void setBookID(int bookID) { this.bookID = bookID; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public Author getAuthor() { return author; }
    public void setAuthor(Author author) { this.author = author; }

    public Genre getGenre() { return genre; }
    public void setGenre(Genre genre) { this.genre = genre; }

    @Override
    public String toString() {
        return "Book{id=" + bookID + ", title='" + title + "', author=" + (author != null ? author.getAuthorName() : "null") + "}";
    }
}