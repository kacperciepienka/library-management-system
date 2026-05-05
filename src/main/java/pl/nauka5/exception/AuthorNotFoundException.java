package pl.nauka5.exception;

public class AuthorNotFoundException extends RuntimeException {
    public AuthorNotFoundException(Long id) {
        super("Author with id: " + id + " doesn't exist");
    }
}
