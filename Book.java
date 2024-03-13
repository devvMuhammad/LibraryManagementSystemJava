package LibrarySystem;

public class Book {
    int bookID;
    String title;
    String author;
    String genre;
    // boolean availability_status;
    boolean availabilityStatus;

    Book() {
        bookID = 0;
        title = null;
        author = null;
        genre = null;
        // availability_status = false;
        availabilityStatus = false;
    }
}