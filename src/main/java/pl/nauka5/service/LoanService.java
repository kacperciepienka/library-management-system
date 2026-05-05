package pl.nauka5.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.nauka5.exception.*;
import pl.nauka5.model.*;
import pl.nauka5.repository.BookRepository;
import pl.nauka5.repository.BorrowerRepository;
import pl.nauka5.repository.LoanRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Transactional
public class LoanService {
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final BorrowerRepository borrowerRepository;

    // DELETE - we can't delete loan it can be usefully for statistic security ect.
    // We can only overwrite it after for example 10 years

    // POST
    public Loan addLoan(Loan loan, String bookIsbnNumber, Integer borrowerLibraryCardNumber){
        Book book = bookRepository.findBookByIsbnEqualsIgnoreCase(bookIsbnNumber)
                .orElseThrow(() -> new BookNotFoundException(bookIsbnNumber));

        if (book.getIsAvailable() == false){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Borrower borrower = borrowerRepository.findByLibraryCardNumber(borrowerLibraryCardNumber)
                .orElseThrow(() -> new BorrowerNotFoundException(borrowerLibraryCardNumber));

        if (borrower.getActiveLoansCount() > 5){
            throw new CantLoanNextBookException(borrowerLibraryCardNumber);
        }

        loan.setBook(book);
        loan.setBorrower(borrower);
        loan.setLoanDate(LocalDate.now());
        loan.setDueDate(loan.getLoanDate().plusDays(14));
        loan.setPricePerDayWhenBookWasLoaned(book.getPricePerDay());
        loan.setLoanStatus(LoanStatus.ACTIVE);

        book.setIsAvailable(false);
        borrower.setActiveLoansCount(borrower.getActiveLoansCount() + 1);
        borrower.setLoanCount(borrower.getLoanCount() + 1);

        if (borrower.getLoanCount() >= 50){
            borrower.setBorrowerAccType(BorrowerAccType.VIP);
            borrower.setDiscount(new BigDecimal("0.70"));
        } else if (borrower.getLoanCount() >= 10) {
            borrower.setBorrowerAccType(BorrowerAccType.NORMAL);
            borrower.setDiscount(new BigDecimal("0.85"));
        }

        return loanRepository.save(loan);
    }

    // PUT
    public Loan returnBook(Long loanId){
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException(loanId));

        if (loan.getBorrower().getActiveLoansCount() == 0){
            throw new BorrowerCantReturnBookException(loan.getBorrower().getLibraryCardNumber());
        }

        loan.setReturnDate(LocalDate.now());
        loan.setLoanStatus(LoanStatus.RETURNED);
        loan.getBook().setIsAvailable(true);
        loan.getBorrower().setActiveLoansCount(loan.getBorrower().getActiveLoansCount() - 1);

        if (loan.getReturnDate().isAfter(loan.getDueDate())){
            long daysLate = ChronoUnit.DAYS.between(loan.getDueDate(), loan.getReturnDate());
            BigDecimal multiply = new BigDecimal("14.00"); // max days of free loan
            BigDecimal feePerDay = new BigDecimal("2.00"); // fee per day static fee for every book
            BigDecimal daysOfFee = new BigDecimal(daysLate);

            loan.setPenaltyFee(feePerDay.multiply(daysOfFee));
            BigDecimal calculatedPrice = ((loan.getPricePerDayWhenBookWasLoaned()
                    .multiply(multiply)
                    .add(loan.getPenaltyFee()))
                    .multiply(loan.getBorrower().getDiscount()));

            loan.setFinalPrice(calculatedPrice.setScale(2, RoundingMode.HALF_UP));
        } else {
            long daysOfLoan = ChronoUnit.DAYS.between(loan.getLoanDate(), loan.getReturnDate());
            if (daysOfLoan == 0){
                daysOfLoan = 1;
            }
            BigDecimal multiply = new BigDecimal(daysOfLoan);

            BigDecimal calculatedPrice = ((loan.getPricePerDayWhenBookWasLoaned()
                    .multiply(multiply))
                    .multiply(loan.getBorrower().getDiscount()));

            loan.setFinalPrice(calculatedPrice.setScale(2, RoundingMode.HALF_UP));
        }

        return loanRepository.save(loan);
    }

    // GET
    public Loan findLoanById(Long loanId){
        return loanRepository.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException(loanId));
    }

    public Page<Loan> findAllLoans(Pageable pageable){
        return loanRepository.findAll(pageable);
    }

    // By Borrower
        // name
            public Page<Loan> findAllByBorrower_FirstNameEqualsIgnoreCase(String borrowerFirstName, Pageable pageable){
                return loanRepository.findAllByBorrower_FirstNameEqualsIgnoreCase(borrowerFirstName, pageable);
            }

            public Page<Loan> findAllByBorrower_LastNameEqualsIgnoreCase(String borrowerLastName, Pageable pageable){
                return loanRepository.findAllByBorrower_LastNameEqualsIgnoreCase(borrowerLastName, pageable);
            }

            public Page<Loan> findAllByBorrower_FirstNameEqualsIgnoreCaseAndBorrower_LastNameEqualsIgnoreCase(String borrowerFirstName, String borrowerLastName, Pageable pageable){
                return loanRepository.findAllByBorrower_FirstNameEqualsIgnoreCaseAndBorrower_LastNameEqualsIgnoreCase(borrowerFirstName, borrowerLastName, pageable);
            }

        // email
        public Page<Loan> findAllByBorrower_EmailEqualsIgnoreCase(String borrowerEmail, Pageable pageable){
            return loanRepository.findAllByBorrower_EmailEqualsIgnoreCase(borrowerEmail, pageable);
        }

        // library card number
        public Page<Loan> findAllByBorrower_LibraryCardNumber(Integer borrowerLibraryCardNumber, Pageable pageable){
            return loanRepository.findAllByBorrower_LibraryCardNumber(borrowerLibraryCardNumber, pageable);
        }

        // acc type
        public Page<Loan> findAllByBorrower_BorrowerAccType(BorrowerAccType borrowerBorrowerAccType, Pageable pageable){
            return loanRepository.findAllByBorrower_BorrowerAccType(borrowerBorrowerAccType, pageable);
        }

    // By Book
        // ISBN
        public Page<Loan> findAllByBook_Isbn(String isbn, Pageable pageable){
            return loanRepository.findAllByBook_IsbnEqualsIgnoreCase(isbn, pageable);
        }

        // to search for an author's popularity
            public Page<Loan> findAllByBook_Author_FirstNameEqualsIgnoreCaseAndBook_Author_LastNameEqualsIgnoreCase(String bookAuthorFirstName, String bookAuthorLastName, Pageable pageable){
                return loanRepository.findAllByBook_Author_FirstNameEqualsIgnoreCaseAndBook_Author_LastNameEqualsIgnoreCase(bookAuthorFirstName, bookAuthorLastName, pageable);
            }
            public Page<Loan> findAllByBook_Author_Id(Long bookAuthorId, Pageable pageable){
                return loanRepository.findAllByBook_Author_Id(bookAuthorId, pageable);
            }

        // to find out which books are popular (paid or free)
            public Page<Loan> findAllByBook_PricePerDayIsLessThanEqual(BigDecimal bookPricePerDay, Pageable pageable){
                return loanRepository.findAllByBook_PricePerDayIsLessThanEqual(bookPricePerDay, pageable);
            }
            public Page<Loan> findAllByBook_PricePerDayIsGreaterThanEqual(BigDecimal bookPricePerDay, Pageable pageable){
                return loanRepository.findAllByBook_PricePerDayIsGreaterThanEqual(bookPricePerDay, pageable);
            }
            public Page<Loan> findAllByBook_PricePerDayIsBetween(BigDecimal bookPricePerDayAfter, BigDecimal bookPricePerDayBefore, Pageable pageable){
                return loanRepository.findAllByBook_PricePerDayIsBetween(bookPricePerDayAfter, bookPricePerDayBefore, pageable);
            }

        // to find out which type is the most popular
        public Page<Loan> findAllByBook_BookGenreType(BookGenreType bookBookGenreType, Pageable pageable){
            return loanRepository.findAllByBook_BookGenreType(bookBookGenreType, pageable);
        }

        // to find out which years' books are the most popular
            public Page<Loan> findAllByBook_ReleaseYearIsLessThanEqual(Integer releaseYear, Pageable pageable){
                return loanRepository.findAllByBook_ReleaseYearIsLessThanEqual(releaseYear, pageable);
            }
            public Page<Loan> findAllByBook_ReleaseYearIsGreaterThanEqual(Integer releaseYear, Pageable pageable){
                return loanRepository.findAllByBook_ReleaseYearIsGreaterThanEqual(releaseYear, pageable);
            }
            public Page<Loan> findAllByBook_ReleaseYearIsBetween(Integer releaseYearAfter, Integer releaseYearBefore, Pageable pageable){
                return loanRepository.findAllByBook_ReleaseYearIsBetween(releaseYearAfter, releaseYearBefore, pageable);
            }

    // By Loan date
        public Page<Loan> findAllByLoanDate(LocalDate loanDate, Pageable pageable){  // <- for exact date
            return loanRepository.findAllByLoanDate(loanDate, pageable);
        }
        public Page<Loan> findAllByLoanDateIsAfter(LocalDate loanDateAfter, Pageable pageable){
            return loanRepository.findAllByLoanDateIsAfter(loanDateAfter, pageable);
        }
        public Page<Loan> findAllByLoanDateIsBefore(LocalDate loanDateBefore, Pageable pageable){
            return loanRepository.findAllByLoanDateIsBefore(loanDateBefore, pageable);
        }
        public Page<Loan> findAllByLoanDateIsBetween(LocalDate loanDateAfter, LocalDate loanDateBefore, Pageable pageable){
            return loanRepository.findAllByLoanDateIsBetween(loanDateAfter, loanDateBefore, pageable);
        }

    // By penalty fee
        public Page<Loan> findAllByPenaltyFeeIsLessThanEqual(BigDecimal penaltyFeeIsLessThan, Pageable pageable){
            return loanRepository.findAllByPenaltyFeeIsLessThanEqual(penaltyFeeIsLessThan, pageable);
        }
        public Page<Loan> findAllByPenaltyFeeIsGreaterThanEqual(BigDecimal penaltyFeeIsGreaterThan, Pageable pageable){
            return loanRepository.findAllByPenaltyFeeIsGreaterThanEqual(penaltyFeeIsGreaterThan, pageable);
        }
        public Page<Loan> findAllByPenaltyFeeIsBetween(BigDecimal penaltyFeeAfter, BigDecimal penaltyFeeBefore, Pageable pageable){
            return loanRepository.findAllByPenaltyFeeIsBetween(penaltyFeeAfter, penaltyFeeBefore, pageable);
        }

    // By final price
        public Page<Loan> findAllByFinalPriceIsLessThanEqual(BigDecimal finalPriceIsLessThan, Pageable pageable){
            return loanRepository.findAllByFinalPriceIsLessThanEqual(finalPriceIsLessThan, pageable);
        }
        public Page<Loan> findAllByFinalPriceIsGreaterThanEqual(BigDecimal finalPriceIsGreaterThan, Pageable pageable){
            return loanRepository.findAllByFinalPriceIsGreaterThanEqual(finalPriceIsGreaterThan, pageable);
        }
        public Page<Loan> findAllByFinalPriceIsBetween(BigDecimal finalPriceAfter, BigDecimal finalPriceBefore, Pageable pageable){
            return loanRepository.findAllByFinalPriceIsBetween(finalPriceAfter, finalPriceBefore, pageable);
        }

    // By loan status (ACTIVE, DONE)
    public Page<Loan> findAllByLoanStatus(LoanStatus loanStatus, Pageable pageable){
        return loanRepository.findAllByLoanStatus(loanStatus, pageable);
    }
}
