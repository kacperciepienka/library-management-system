package pl.nauka5.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.nauka5.exception.*;
import pl.nauka5.model.Borrower;
import pl.nauka5.model.BorrowerAccStatus;
import pl.nauka5.model.BorrowerAccType;
import pl.nauka5.repository.BorrowerRepository;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional
public class BorrowerService {
    private final BorrowerRepository borrowerRepository;

    // DELETE - we cant delete user we can make inactive acc status instead
    // POST
    public Borrower addBorrower(Borrower borrowerToAdd){
        borrowerRepository.findBorrowerByEmailEqualsIgnoreCase(borrowerToAdd.getEmail())
                .ifPresent(borrower -> {
                    throw new BorrowerAlreadyExistException(borrower.getLibraryCardNumber());
                });

        borrowerToAdd.setLibraryCardNumber(borrowerRepository.accountsCounters() + 1000);
        borrowerToAdd.setActiveLoansCount(0);
        borrowerToAdd.setBorrowerAccStatus(BorrowerAccStatus.ACTIVE);
        borrowerToAdd.setLoanCount(0);
        borrowerToAdd.setBorrowerAccType(BorrowerAccType.BEGINNER);
        borrowerToAdd.setDiscount(new BigDecimal("1.00"));  // beginner discount
        return borrowerRepository.save(borrowerToAdd);
    }

    // PUT
    public Borrower updateEmail(Integer libraryCardNumber, String newEmail){
        Borrower borrower = borrowerRepository.findByLibraryCardNumber(libraryCardNumber)
                .orElseThrow(() -> new BorrowerNotFoundException(libraryCardNumber));

        borrowerRepository.findBorrowerByEmailEqualsIgnoreCase(newEmail)
                .ifPresent(b -> {
                    throw new BorrowerEmailAlreadyExistException(newEmail);
                });

        borrower.setEmail(newEmail);
        return borrowerRepository.save(borrower);
    }

    // For example when somebody destroy our book
    public Borrower updateActiveLoanCount(Integer libraryCardNumber, Integer newActiveLoansCount){
        Borrower borrower = borrowerRepository.findByLibraryCardNumber(libraryCardNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (newActiveLoansCount > borrower.getActiveLoansCount() || newActiveLoansCount < 0){
            throw new InvalidBorrowerActiveLoansCount(libraryCardNumber, newActiveLoansCount);
        }

        borrower.setActiveLoansCount(newActiveLoansCount);
        return borrowerRepository.save(borrower);
    }

    public Borrower updateBorrowerAccStatus(Integer libraryCardNumber, BorrowerAccStatus newBorrowerAccStatus){
        Borrower borrower = borrowerRepository.findByLibraryCardNumber(libraryCardNumber)
                .orElseThrow(() -> new BorrowerNotFoundException(libraryCardNumber));

        borrower.setBorrowerAccStatus(newBorrowerAccStatus);
        return borrowerRepository.save(borrower);
    }

    // GET
    public Borrower findByLibraryCardNumber(Integer libraryCardNumber){
        return borrowerRepository.findByLibraryCardNumber(libraryCardNumber)
                .orElseThrow(() -> new BorrowerNotFoundException(libraryCardNumber));
    }

    // By email
    public Borrower findBorrowerByEmailEqualsIgnoreCase(String email){
        return borrowerRepository.findBorrowerByEmailEqualsIgnoreCase(email)
                .orElseThrow(() -> new BorrowerNotFoundExceptionByEmail(email));
    }

    public Page<Borrower> findAllBorrowers(Pageable pageable){
        return borrowerRepository.findAll(pageable);
    }

    // By name
        public Page<Borrower> findAllByFirstNameEqualsIgnoreCase(String firstName, Pageable pageable){
            return borrowerRepository.findAllByFirstNameEqualsIgnoreCase(firstName, pageable);
        }

        public Page<Borrower> findAllByLastNameEqualsIgnoreCase(String lastName, Pageable pageable){
            return borrowerRepository.findAllByLastNameEqualsIgnoreCase(lastName, pageable);
        }

        public Page<Borrower> findAllByFirstNameEqualsIgnoreCaseAndLastNameEqualsIgnoreCase(String firstName, String lastName, Pageable pageable){
            return borrowerRepository.findAllByFirstNameEqualsIgnoreCaseAndLastNameEqualsIgnoreCase(firstName, lastName, pageable);
        }

    // By  active loans counter
        public Page<Borrower> findAllByActiveLoansCountIsLessThanEqual(Integer activeLoansCountIsLessThan, Pageable pageable){
            return borrowerRepository.findAllByActiveLoansCountIsLessThanEqual(activeLoansCountIsLessThan, pageable);
        }

        public Page<Borrower> findAllByActiveLoansCountIsGreaterThanEqual(Integer activeLoansCountIsGreaterThan, Pageable pageable){
            return borrowerRepository.findAllByActiveLoansCountIsGreaterThanEqual(activeLoansCountIsGreaterThan, pageable);
        }

        public Page<Borrower> findAllByActiveLoansCountIsBetween(Integer activeLoansCountAfter, Integer activeLoansCountBefore, Pageable pageable){
            return borrowerRepository.findAllByActiveLoansCountIsBetween(activeLoansCountAfter, activeLoansCountBefore, pageable);
        }

    public Page<Borrower> findAllByBorrowerAccStatus(BorrowerAccStatus borrowerAccStatus, Pageable pageable){
        return  borrowerRepository.findAllByBorrowerAccStatus(borrowerAccStatus, pageable);
    }

    // By borrower loan count
        public Page<Borrower> findAllByLoanCountIsLessThanEqual(Integer loanCountIsLessThan, Pageable pageable){
            return borrowerRepository.findAllByLoanCountIsLessThanEqual(loanCountIsLessThan, pageable);
        }

        public Page<Borrower> findAllByLoanCountIsGreaterThanEqual(Integer loanCountIsGreaterThan, Pageable pageable){
            return borrowerRepository.findAllByLoanCountIsGreaterThanEqual(loanCountIsGreaterThan, pageable);
        }

        public Page<Borrower> findAllByLoanCountIsBetween(Integer loanCountAfter, Integer loanCountBefore, Pageable pageable){
            return borrowerRepository.findAllByLoanCountIsBetween(loanCountAfter, loanCountBefore, pageable);
        }

    // By borrower acc type
    public Page<Borrower> findAllByBorrowerAccType(BorrowerAccType borrowerAccType, Pageable pageable){
        return borrowerRepository.findAllByBorrowerAccType(borrowerAccType, pageable);
    }
}
