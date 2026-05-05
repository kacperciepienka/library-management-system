package pl.nauka5.exception;

public class BorrowerAlreadyExistException extends RuntimeException {
    public BorrowerAlreadyExistException(Integer borrowerLibraryCardNumber) {
        super("Borrower with car number: " + borrowerLibraryCardNumber + " already exists");
    }
}
