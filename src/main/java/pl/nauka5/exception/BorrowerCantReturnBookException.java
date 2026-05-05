package pl.nauka5.exception;

public class BorrowerCantReturnBookException extends RuntimeException {
    public BorrowerCantReturnBookException(Integer borrowerLibraryCardNumber) {
        super("Borrower with card number: " + borrowerLibraryCardNumber  + "can't return any book because doesn't have any active loan");
    }
}
