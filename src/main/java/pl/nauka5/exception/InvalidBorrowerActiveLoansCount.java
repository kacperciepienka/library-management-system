package pl.nauka5.exception;

public class InvalidBorrowerActiveLoansCount extends RuntimeException {
    public InvalidBorrowerActiveLoansCount(Integer borrowerLibraryCardNumber, Integer activeLoansCount) {
        super("Provided new active loans for borrower: " + borrowerLibraryCardNumber + " is invalid! (new loans count can't be more than previously and lesser than 0)");
    }
}
