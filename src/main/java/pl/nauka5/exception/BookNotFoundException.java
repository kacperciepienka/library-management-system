package pl.nauka5.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String isbnNumber) {
        super("Book with ISBN number: " + isbnNumber + " doesn't exist");
    }
}
