package pl.nauka5.exception;

public class BorrowerEmailAlreadyExistException extends RuntimeException {
    public BorrowerEmailAlreadyExistException(String newEmail) {
        super("Borrower with provided new email: " + newEmail + " already exists");
    }
}
