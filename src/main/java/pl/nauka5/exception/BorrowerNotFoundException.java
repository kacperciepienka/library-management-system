package pl.nauka5.exception;

public class BorrowerNotFoundException extends RuntimeException {
    public BorrowerNotFoundException(Integer borrowerLibraryCardNumber) {
        super("Borrower with card numer: " + borrowerLibraryCardNumber + " doesn't exist");
    }
}
