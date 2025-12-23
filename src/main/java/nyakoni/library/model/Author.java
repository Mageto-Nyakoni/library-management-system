package nyakoni.library.model;

public class Author {
    private int authorID;
    private String authorName;

    // constructors
    public Author() {}

    public Author(int authorID, String authorName) {
        this.authorID = authorID;
        this.authorName = authorName;
    }

    // getters and setters
    public int getAuthorID() { return authorID; }
    public void setAuthorID(int authorID) { this.authorID = authorID; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    @Override
    public String toString() {
        return "Author{id=" + authorID + ", name='" + authorName + "'}";
    }
}
