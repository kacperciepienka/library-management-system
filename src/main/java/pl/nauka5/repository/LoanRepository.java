package pl.nauka5.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.nauka5.model.BookGenreType;
import pl.nauka5.model.BorrowerAccType;
import pl.nauka5.model.Loan;
import pl.nauka5.model.LoanStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    // tu w domyśle jest szukanie po ID.

    // By Borrower
        // name
        Page<Loan> findAllByBorrower_FirstNameEqualsIgnoreCase(String borrowerFirstName, Pageable pageable);
        Page<Loan> findAllByBorrower_LastNameEqualsIgnoreCase(String borrowerLastName, Pageable pageable);
        Page<Loan> findAllByBorrower_FirstNameEqualsIgnoreCaseAndBorrower_LastNameEqualsIgnoreCase(String borrowerFirstName, String borrowerLastName, Pageable pageable);

        // email
        Page<Loan> findAllByBorrower_EmailEqualsIgnoreCase(String borrowerEmail, Pageable pageable);

        // library card number
        Page<Loan> findAllByBorrower_LibraryCardNumber(Integer borrowerLibraryCardNumber, Pageable pageable);

        // acc type
        Page<Loan> findAllByBorrower_BorrowerAccType(BorrowerAccType borrowerBorrowerAccType, Pageable pageable);

    // By Book
        // ISBN
        Page<Loan> findAllByBook_IsbnEqualsIgnoreCase(String isbn, Pageable pageable);

        // to search for an author's popularity
        Page<Loan> findAllByBook_Author_FirstNameEqualsIgnoreCaseAndBook_Author_LastNameEqualsIgnoreCase(String bookAuthorFirstName, String bookAuthorLastName, Pageable pageable);
        Page<Loan> findAllByBook_Author_Id(Long bookAuthorId, Pageable pageable);

        // to find out which books are popular (paid or free)
        Page<Loan> findAllByBook_PricePerDayIsLessThanEqual(BigDecimal bookPricePerDay, Pageable pageable);
        Page<Loan> findAllByBook_PricePerDayIsGreaterThanEqual(BigDecimal bookPricePerDay, Pageable pageable);
        Page<Loan> findAllByBook_PricePerDayIsBetween(BigDecimal bookPricePerDayAfter, BigDecimal bookPricePerDayBefore, Pageable pageable);

        // to find out which type is the most popular
        Page<Loan> findAllByBook_BookGenreType(BookGenreType bookBookGenreType, Pageable pageable);

        // to find out which years' books are the most popular
        Page<Loan> findAllByBook_ReleaseYearIsLessThanEqual(Integer releaseYear, Pageable pageable);
        Page<Loan> findAllByBook_ReleaseYearIsGreaterThanEqual(Integer releaseYear, Pageable pageable);
        Page<Loan> findAllByBook_ReleaseYearIsBetween(Integer releaseYearAfter, Integer releaseYearBefore, Pageable pageable);

    // By Loan date
    Page<Loan> findAllByLoanDate(LocalDate loanDate, Pageable pageable); // <- for exact date
    Page<Loan> findAllByLoanDateIsAfter(LocalDate loanDateAfter, Pageable pageable);
    Page<Loan> findAllByLoanDateIsBefore(LocalDate loanDateBefore, Pageable pageable);
    Page<Loan> findAllByLoanDateIsBetween(LocalDate loanDateAfter, LocalDate loanDateBefore, Pageable pageable);

    // By penalty fee
    Page<Loan> findAllByPenaltyFeeIsLessThanEqual(BigDecimal penaltyFeeIsLessThan, Pageable pageable);
    Page<Loan> findAllByPenaltyFeeIsGreaterThanEqual(BigDecimal penaltyFeeIsGreaterThan, Pageable pageable);
    Page<Loan> findAllByPenaltyFeeIsBetween(BigDecimal penaltyFeeAfter, BigDecimal penaltyFeeBefore, Pageable pageable);

    // By final price
    Page<Loan> findAllByFinalPriceIsLessThanEqual(BigDecimal finalPriceIsLessThan, Pageable pageable);
    Page<Loan> findAllByFinalPriceIsGreaterThanEqual(BigDecimal finalPriceIsGreaterThan, Pageable pageable);
    Page<Loan> findAllByFinalPriceIsBetween(BigDecimal finalPriceAfter, BigDecimal finalPriceBefore, Pageable pageable);

    // By loan status (ACTIVE, DONE)
    Page<Loan> findAllByLoanStatus(LoanStatus loanStatus, Pageable pageable);
}
