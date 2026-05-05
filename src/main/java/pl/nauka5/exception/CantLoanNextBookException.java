package pl.nauka5.exception;

public class CantLoanNextBookException extends RuntimeException {
    public CantLoanNextBookException(Integer libraryCardNumber) {
        super("Client with card number: " + libraryCardNumber + " already has 5 active loans!");
    }
}
