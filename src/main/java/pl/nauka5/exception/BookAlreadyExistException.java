package pl.nauka5.exception;

public class BookAlreadyExistException extends RuntimeException {
    public BookAlreadyExistException(String isbnNumber) {
        super("Book with ISBN number: " + isbnNumber + " already exists");
    }
}
