package pl.nauka5.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.nauka5.model.Borrower;
import pl.nauka5.model.BorrowerAccStatus;
import pl.nauka5.model.BorrowerAccType;

import java.util.Optional;

@Repository
public interface BorrowerRepository extends JpaRepository<Borrower,Long> {
    // Nie szukamy po ID, bo nikt nie ma do niego dostępu (Admin ani User)
    // By Library card number (tu nie dajemy page, bo zawsze max. 1 obiekt)
    Optional<Borrower> findByLibraryCardNumber(Integer libraryCardNumber);
    // By email
    Optional<Borrower> findBorrowerByEmailEqualsIgnoreCase(String email);

    // By name
        Page<Borrower> findAllByFirstNameEqualsIgnoreCase(String firstName, Pageable pageable);
        Page<Borrower> findAllByLastNameEqualsIgnoreCase(String lastName, Pageable pageable);
        Page<Borrower> findAllByFirstNameEqualsIgnoreCaseAndLastNameEqualsIgnoreCase(String firstName, String lastName, Pageable pageable);

    // By  active loans counter
        Page<Borrower> findAllByActiveLoansCountIsLessThanEqual(Integer activeLoansCountIsLessThan, Pageable pageable);
        Page<Borrower> findAllByActiveLoansCountIsGreaterThanEqual(Integer activeLoansCountIsGreaterThan, Pageable pageable);
        Page<Borrower> findAllByActiveLoansCountIsBetween(Integer activeLoansCountAfter, Integer activeLoansCountBefore, Pageable pageable);

    // By borrower acc status
    Page<Borrower> findAllByBorrowerAccStatus(BorrowerAccStatus borrowerAccStatus, Pageable pageable);

    // By borrower loan count
        Page<Borrower> findAllByLoanCountIsLessThanEqual(Integer loanCountIsLessThan, Pageable pageable);
        Page<Borrower> findAllByLoanCountIsGreaterThanEqual(Integer loanCountIsGreaterThan, Pageable pageable);
        Page<Borrower> findAllByLoanCountIsBetween(Integer loanCountAfter, Integer loanCountBefore, Pageable pageable);

    // By borrower acc type
    Page<Borrower> findAllByBorrowerAccType(BorrowerAccType borrowerAccType, Pageable pageable);

    // do zliczania
    @Query("SELECT COUNT(b) FROM Borrower b")
    int accountsCounters();
}
