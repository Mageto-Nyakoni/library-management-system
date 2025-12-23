package nyakoni.library.model;

public class Genre {
    private int genreID;
    private String genreName;

    //constructors
    public Genre() {}

    public Genre(int genreID, String genreName) {
        this.genreID = genreID;
        this.genreName = genreName;
    }

    //getters and setters
    public int getGenreID() { return genreID; }
    public void setGenreID(int genreID) { this.genreID = genreID; }

    public String getGenreName() { return genreName; }
    public void setGenreName(String genreName) { this.genreName = genreName; }

    @Override
    public String toString() {
        return "Genre{id=" + genreID + ", name='" + genreName + "'}";
    }
}