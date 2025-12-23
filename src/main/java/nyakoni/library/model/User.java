package nyakoni.library.model;

public class User {
    private int libraryCardNumber;
    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;

    //constructors
    public User() {}

    public User(int libraryCardNumber, String fullName, String email, String password, String phoneNumber) {
        this.libraryCardNumber = libraryCardNumber;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    //getters and setters
    public int getLibraryCardNumber() { return libraryCardNumber; }
    public void setLibraryCardNumber(int libraryCardNumber) { this.libraryCardNumber = libraryCardNumber; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    @Override
    public String toString() {
        return "User{id=" + libraryCardNumber + ", name='" + fullName + "', email='" + email + "'}";
    }
}
