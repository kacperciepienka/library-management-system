package pl.nauka5.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.nauka5.dto.LoanAdminDTO;
import pl.nauka5.mapper.LoanAdminMapper;
import pl.nauka5.model.BookGenreType;
import pl.nauka5.model.BorrowerAccType;
import pl.nauka5.model.Loan;
import pl.nauka5.model.LoanStatus;
import pl.nauka5.service.LoanService;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0.1/admin/loan")
public class LoanAdminController {
    private final LoanService loanService;
    private final LoanAdminMapper loanAdminMapper;

    // POST
    @PostMapping("/{isbn}/{cardNumber}")
    public ResponseEntity<LoanAdminDTO> addLoan(@Valid @RequestBody Loan loan, @PathVariable String isbn, @PathVariable Integer cardNumber){
        Loan loanToSave = loanService.addLoan(loan, isbn, cardNumber);
        return ResponseEntity.status(HttpStatus.CREATED).body(loanAdminMapper.toDto(loanToSave));
    }

    // PUT
    @PutMapping("/{loanId}/return")
    public ResponseEntity<LoanAdminDTO> returnBook(@PathVariable Long loanId){
        Loan loan = loanService.returnBook(loanId);
        return ResponseEntity.ok(loanAdminMapper.toDto(loan));
    }


    // GET
    @GetMapping("/{loanId}")
    public ResponseEntity<LoanAdminDTO> findLoanById(@PathVariable Long loanId){
        Loan loan = loanService.findLoanById(loanId);
        return ResponseEntity.ok(loanAdminMapper.toDto(loan));
    }

    // find all loans
    @GetMapping
    public ResponseEntity<Page<LoanAdminDTO>> findAllLoans(Pageable pageable){
        Page<Loan> loans = loanService.findAllLoans(pageable);
        Page<LoanAdminDTO> dtoPage = loans.map(loanAdminMapper::toDto);

        return ResponseEntity.ok(dtoPage);
    }

    // By Borrower
        // name
    @GetMapping("/search/borrower/first-name")
        public ResponseEntity<Page<LoanAdminDTO>> findAllByBorrower_FirstNameEqualsIgnoreCase(@RequestParam String borrowerFirstName, Pageable pageable){
            Page<Loan> loans = loanService.findAllByBorrower_FirstNameEqualsIgnoreCase(borrowerFirstName, pageable);
                    Page<LoanAdminDTO> dtoPage = loans.map(loanAdminMapper::toDto);

            return ResponseEntity.ok(dtoPage);
        }

    @GetMapping("/search/borrower/last-name")
        public ResponseEntity<Page<LoanAdminDTO>> findAllByBorrower_LastNameEqualsIgnoreCase(@RequestParam String borrowerLastName, Pageable pageable){
            Page<Loan> loans = loanService.findAllByBorrower_LastNameEqualsIgnoreCase(borrowerLastName, pageable);
                    Page<LoanAdminDTO> dtoPage = loans.map(loanAdminMapper::toDto);

            return ResponseEntity.ok(dtoPage);
        }

    @GetMapping("/search/borrower/full-name")
        public ResponseEntity<Page<LoanAdminDTO>> findAllByBorrower_FirstNameEqualsIgnoreCaseAndBorrower_LastNameEqualsIgnoreCase(@RequestParam String borrowerFirstName, @RequestParam String borrowerLastName, Pageable pageable){
            Page<Loan> loans = loanService.findAllByBorrower_FirstNameEqualsIgnoreCaseAndBorrower_LastNameEqualsIgnoreCase(borrowerFirstName, borrowerLastName, pageable);
                    Page<LoanAdminDTO> dtoPage = loans.map(loanAdminMapper::toDto);

            return ResponseEntity.ok(dtoPage);
        }

        // email
    @GetMapping("/search/borrower/email")
        public ResponseEntity<Page<LoanAdminDTO>> findAllByBorrower_EmailEqualsIgnoreCase(@RequestParam String borrowerEmail, Pageable pageable){
            Page<Loan> loans = loanService.findAllByBorrower_EmailEqualsIgnoreCase(borrowerEmail, pageable);
                    Page<LoanAdminDTO> dtoPage = loans.map(loanAdminMapper::toDto);

            return ResponseEntity.ok(dtoPage);
        }

        // library card number
    @GetMapping("/search/borrower/{borrowerLibraryCardNumber}")
        public ResponseEntity<Page<LoanAdminDTO>> findAllByBorrower_LibraryCardNumber(@PathVariable Integer borrowerLibraryCardNumber, Pageable pageable){
            Page<Loan> loans = loanService.findAllByBorrower_LibraryCardNumber(borrowerLibraryCardNumber, pageable);
                    Page<LoanAdminDTO> dtoPage = loans.map(loanAdminMapper::toDto);

            return ResponseEntity.ok(dtoPage);
        }

        // acc type
    @GetMapping("/search/borrower/acc-type")
        public ResponseEntity<Page<LoanAdminDTO>> findAllByBorrower_BorrowerAccType(@RequestParam BorrowerAccType borrowerBorrowerAccType, Pageable pageable){
            Page<Loan> loans = loanService.findAllByBorrower_BorrowerAccType(borrowerBorrowerAccType, pageable);
                    Page<LoanAdminDTO> dtoPage = loans.map(loanAdminMapper::toDto);

            return ResponseEntity.ok(dtoPage);
        }

    // By Book
        // By ISBN number
    @GetMapping("/search/book/{isbn}")
        public ResponseEntity<Page<LoanAdminDTO>> findAllByBook_Isbn(@PathVariable String isbn, Pageable pageable){
            Page<Loan> loans = loanService.findAllByBook_Isbn(isbn, pageable);
                    Page<LoanAdminDTO> dtoPage = loans.map(loanAdminMapper::toDto);

            return ResponseEntity.ok(dtoPage);
        }

            // to search for an author's popularity
    @GetMapping("/search/book/author/full-name")
            public ResponseEntity<Page<LoanAdminDTO>> findAllByBook_Author_FirstNameEqualsIgnoreCaseAndBook_Author_LastNameEqualsIgnoreCase(@RequestParam String bookAuthorFirstName, @RequestParam String bookAuthorLastName, Pageable pageable){
                Page<Loan> loans = loanService.findAllByBook_Author_FirstNameEqualsIgnoreCaseAndBook_Author_LastNameEqualsIgnoreCase(bookAuthorFirstName, bookAuthorLastName, pageable);
                        Page<LoanAdminDTO> dtoPage = loans.map(loanAdminMapper::toDto);

                return ResponseEntity.ok(dtoPage);
            }
    @GetMapping("/search/book/author/{authorId}")
            public ResponseEntity<Page<LoanAdminDTO>> findAllByBook_Author_Id(@PathVariable Long authorId, Pageable pageable){
                Page<Loan> loans = loanService.findAllByBook_Author_Id(authorId, pageable);
                        Page<LoanAdminDTO> dtoPage = loans.map(loanAdminMapper::toDto);

                return ResponseEntity.ok(dtoPage);
            }

            // to find out which books are popular (paid or free)
    @GetMapping("/search/book/price-less")
            public ResponseEntity<Page<LoanAdminDTO>> findAllByBook_PricePerDayIsLessThanEqual(@RequestParam BigDecimal bookPricePerDay, Pageable pageable){
                Page<Loan> loans = loanService.findAllByBook_PricePerDayIsLessThanEqual(bookPricePerDay, pageable);
                        Page<LoanAdminDTO> dtoPage = loans.map(loanAdminMapper::toDto);

                return ResponseEntity.ok(dtoPage);
            }

    @GetMapping("/search/book/price-greater")
            public ResponseEntity<Page<LoanAdminDTO>> findAllByBook_PricePerDayIsGreaterThanEqual(@RequestParam BigDecimal bookPricePerDay, Pageable pageable){
                Page<Loan> loans = loanService.findAllByBook_PricePerDayIsGreaterThanEqual(bookPricePerDay, pageable);
                        Page<LoanAdminDTO> dtoPage = loans.map(loanAdminMapper::toDto);

                return ResponseEntity.ok(dtoPage);
            }

    @GetMapping("/search/book/price-range")
            public ResponseEntity<Page<LoanAdminDTO>> findAllByBook_PricePerDayIsBetween(@RequestParam BigDecimal bookPricePerDayAfter, @RequestParam BigDecimal bookPricePerDayBefore, Pageable pageable){
                Page<Loan> loans = loanService.findAllByBook_PricePerDayIsBetween(bookPricePerDayAfter, bookPricePerDayBefore, pageable);
                        Page<LoanAdminDTO> dtoPage = loans.map(loanAdminMapper::toDto);

                return ResponseEntity.ok(dtoPage);
            }

        // to find out which type is the most popular
    @GetMapping("/search/book/type")
        public ResponseEntity<Page<LoanAdminDTO>> findAllByBook_BookGenreType(@RequestParam BookGenreType bookBookGenreType, Pageable pageable){
            Page<Loan> loans = loanService.findAllByBook_BookGenreType(bookBookGenreType, pageable);
                    Page<LoanAdminDTO> dtoPage = loans.map(loanAdminMapper::toDto);

            return ResponseEntity.ok(dtoPage);
        }

        // to find out which years' books are the most popular
    @GetMapping("/search/book/release-less")
        public ResponseEntity<Page<LoanAdminDTO>> findAllByBook_ReleaseYearIsLessThanEqual(@RequestParam Integer releaseYear, Pageable pageable){
            Page<Loan> loans = loanService.findAllByBook_ReleaseYearIsLessThanEqual(releaseYear, pageable);
                    Page<LoanAdminDTO> dtoPage = loans.map(loanAdminMapper::toDto);

            return ResponseEntity.ok(dtoPage);
        }

    @GetMapping("/search/book/release-greater")
        public ResponseEntity<Page<LoanAdminDTO>> findAllByBook_ReleaseYearIsGreaterThanEqual(@RequestParam Integer releaseYear, Pageable pageable){
            Page<Loan> loans = loanService.findAllByBook_ReleaseYearIsGreaterThanEqual(releaseYear, pageable);
                    Page<LoanAdminDTO> dtoPage = loans.map(loanAdminMapper::toDto);

            return ResponseEntity.ok(dtoPage);
        }

    @GetMapping("/search/book/releases-range")
        public ResponseEntity<Page<LoanAdminDTO>> findAllByBook_ReleaseYearIsBetween(@RequestParam Integer releaseYearAfter, @RequestParam Integer releaseYearBefore, Pageable pageable){
            Page<Loan> loans = loanService.findAllByBook_ReleaseYearIsBetween(releaseYearAfter, releaseYearBefore, pageable);
                    Page<LoanAdminDTO> dtoPage = loans.map(loanAdminMapper::toDto);

            return ResponseEntity.ok(dtoPage);
        }


    // By Loan date
    @GetMapping("/search/date")
        public ResponseEntity<Page<LoanAdminDTO>> findAllByLoanDate(@RequestParam LocalDate loanDate, Pageable pageable){  // <- for exact date
            Page<Loan> loans = loanService.findAllByLoanDate(loanDate, pageable);
                    Page<LoanAdminDTO> dtoPage = loans.map(loanAdminMapper::toDto);

            return ResponseEntity.ok(dtoPage);
        }

    @GetMapping("/search/date-after")
        public ResponseEntity<Page<LoanAdminDTO>> findAllByLoanDateIsAfter(@RequestParam LocalDate loanDateAfter, Pageable pageable){
            Page<Loan> loans = loanService.findAllByLoanDateIsAfter(loanDateAfter, pageable);
                    Page<LoanAdminDTO> dtoPage = loans.map(loanAdminMapper::toDto);

            return ResponseEntity.ok(dtoPage);
        }

    @GetMapping("/search/date-before")
        public ResponseEntity<Page<LoanAdminDTO>> findAllByLoanDateIsBefore(@RequestParam LocalDate loanDateBefore, Pageable pageable){
            Page<Loan> loans = loanService.findAllByLoanDateIsBefore(loanDateBefore, pageable);
                    Page<LoanAdminDTO> dtoPage = loans.map(loanAdminMapper::toDto);

            return ResponseEntity.ok(dtoPage);
        }

    @GetMapping("/search/date-between")
        public ResponseEntity<Page<LoanAdminDTO>> findAllByLoanDateIsBetween(@RequestParam LocalDate loanDateAfter, @RequestParam LocalDate loanDateBefore, Pageable pageable){
            Page<Loan> loans = loanService.findAllByLoanDateIsBetween(loanDateAfter, loanDateBefore, pageable);
                    Page<LoanAdminDTO> dtoPage = loans.map(loanAdminMapper::toDto);

            return ResponseEntity.ok(dtoPage);
        }

    // By penalty fee
    @GetMapping("/search/penalty-less")
        public ResponseEntity<Page<LoanAdminDTO>> findAllByPenaltyFeeIsLessThanEqual(@RequestParam BigDecimal penaltyFeeIsLessThan, Pageable pageable){
            Page<Loan> loans = loanService.findAllByPenaltyFeeIsLessThanEqual(penaltyFeeIsLessThan, pageable);
                    Page<LoanAdminDTO> dtoPage = loans.map(loanAdminMapper::toDto);

            return ResponseEntity.ok(dtoPage);
        }

    @GetMapping("/search/penalty-greter")
        public ResponseEntity<Page<LoanAdminDTO>> findAllByPenaltyFeeIsGreaterThanEqual(@RequestParam BigDecimal penaltyFeeIsGreaterThan, Pageable pageable){
            Page<Loan> loans = loanService.findAllByPenaltyFeeIsGreaterThanEqual(penaltyFeeIsGreaterThan, pageable);
                    Page<LoanAdminDTO> dtoPage = loans.map(loanAdminMapper::toDto);

            return ResponseEntity.ok(dtoPage);
        }

    @GetMapping("/search/penalty-range")
        public ResponseEntity<Page<LoanAdminDTO>> findAllByPenaltyFeeIsBetween(@RequestParam BigDecimal penaltyFeeAfter, @RequestParam BigDecimal penaltyFeeBefore, Pageable pageable){
            Page<Loan> loans = loanService.findAllByPenaltyFeeIsBetween(penaltyFeeAfter, penaltyFeeBefore, pageable);
                    Page<LoanAdminDTO> dtoPage = loans.map(loanAdminMapper::toDto);

            return ResponseEntity.ok(dtoPage);
        }

    // By final price
    @GetMapping("/search/final-price-less")
        public ResponseEntity<Page<LoanAdminDTO>> findAllByFinalPriceIsLessThanEqual(@RequestParam BigDecimal finalPriceIsLessThan, Pageable pageable){
            Page<Loan> loans = loanService.findAllByFinalPriceIsLessThanEqual(finalPriceIsLessThan, pageable);
                    Page<LoanAdminDTO> dtoPage = loans.map(loanAdminMapper::toDto);

            return ResponseEntity.ok(dtoPage);
        }

    @GetMapping("/search/final-price-greater")
        public ResponseEntity<Page<LoanAdminDTO>> findAllByFinalPriceIsGreaterThanEqual(@RequestParam BigDecimal finalPriceIsGreaterThan, Pageable pageable){
            Page<Loan> loans = loanService.findAllByFinalPriceIsGreaterThanEqual(finalPriceIsGreaterThan, pageable);
                    Page<LoanAdminDTO> dtoPage = loans.map(loanAdminMapper::toDto);

            return ResponseEntity.ok(dtoPage);
        }

    @GetMapping("/search/final-price-range")
        public ResponseEntity<Page<LoanAdminDTO>> findAllByFinalPriceIsBetween(@RequestParam BigDecimal finalPriceAfter, @RequestParam BigDecimal finalPriceBefore, Pageable pageable){
            Page<Loan> loans = loanService.findAllByFinalPriceIsBetween(finalPriceAfter, finalPriceBefore, pageable);
                    Page<LoanAdminDTO> dtoPage = loans.map(loanAdminMapper::toDto);

            return ResponseEntity.ok(dtoPage);
        }

    // By loan status (ACTIVE, DONE)
    @GetMapping("/search/status")
    public ResponseEntity<Page<LoanAdminDTO>> findAllByLoanStatus(@RequestParam LoanStatus loanStatus, Pageable pageable){
        Page<Loan> loans = loanService.findAllByLoanStatus(loanStatus, pageable);
                Page<LoanAdminDTO> dtoPage = loans.map(loanAdminMapper::toDto);

        return ResponseEntity.ok(dtoPage);
    }
}
