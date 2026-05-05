package pl.nauka5.exception;

public class LoanNotFoundException extends RuntimeException {
    public LoanNotFoundException(Long loanId) {
        super("Loan with id: " + loanId + " doesn't exists");
    }
}
