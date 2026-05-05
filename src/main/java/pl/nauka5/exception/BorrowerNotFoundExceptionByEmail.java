package pl.nauka5.exception;

public class BorrowerNotFoundExceptionByEmail extends RuntimeException {
    public BorrowerNotFoundExceptionByEmail(String email) {
        super("Borrower with email: " + email + " doesn't exist");
    }
}
