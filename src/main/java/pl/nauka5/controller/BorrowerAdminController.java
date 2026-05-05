package pl.nauka5.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.nauka5.dto.BorrowerAdminDTO;
import pl.nauka5.mapper.BorrowerAdminMapper;
import pl.nauka5.model.Borrower;
import pl.nauka5.model.BorrowerAccStatus;
import pl.nauka5.model.BorrowerAccType;
import pl.nauka5.service.BorrowerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0.1/admin/borrower")
public class BorrowerAdminController {
    private final BorrowerService borrowerService;
    private final BorrowerAdminMapper borrowerAdminMapper;

    // POST
    @PostMapping
    public ResponseEntity<BorrowerAdminDTO> addBorrower(@Valid @RequestBody Borrower borrower){
        Borrower borrowerToSave = borrowerService.addBorrower(borrower);
        return ResponseEntity.status(HttpStatus.CREATED).body(borrowerAdminMapper.toDto(borrowerToSave));
    }

    // PUT
    @PutMapping("/{libraryCardNumber}/update-email")
    public ResponseEntity<BorrowerAdminDTO> updateEmail(@PathVariable Integer libraryCardNumber, @RequestParam String newEmail){
        Borrower borrower = borrowerService.updateEmail(libraryCardNumber, newEmail);
        return ResponseEntity.ok(borrowerAdminMapper.toDto(borrower));
    }

    @PutMapping("/{libraryCardNumber}/update-active-loans")
    public ResponseEntity<BorrowerAdminDTO> activeLoanCount(@PathVariable Integer libraryCardNumber, @RequestParam Integer newActiveLoanCount){
        Borrower borrower = borrowerService.updateActiveLoanCount(libraryCardNumber, newActiveLoanCount);
        return ResponseEntity.ok(borrowerAdminMapper.toDto(borrower));
    }

    @PutMapping("/{libraryCardNumber}/update-acc-status")
    public ResponseEntity<BorrowerAdminDTO> updateBorrowerAccStatus(@PathVariable Integer libraryCardNumber, @RequestParam BorrowerAccStatus accStatus){
        Borrower borrower = borrowerService.updateBorrowerAccStatus(libraryCardNumber, accStatus);
        return ResponseEntity.ok(borrowerAdminMapper.toDto(borrower));
    }

    // GET
    @GetMapping("/{libraryCardNumber}")
    public ResponseEntity<BorrowerAdminDTO> findByLibraryCardNumber(@PathVariable Integer libraryCardNumber){
        Borrower borrower = borrowerService.findByLibraryCardNumber(libraryCardNumber);
        return ResponseEntity.ok(borrowerAdminMapper.toDto(borrower));
    }

    // By email
    @GetMapping("/search/email")
    public ResponseEntity<BorrowerAdminDTO> findBorrowerByEmailEqualsIgnoreCase(@RequestParam String email){
        Borrower borrower = borrowerService.findBorrowerByEmailEqualsIgnoreCase(email);
        return ResponseEntity.ok(borrowerAdminMapper.toDto(borrower));
    }

    @GetMapping
    public ResponseEntity<Page<BorrowerAdminDTO>> findAllBorrowers(Pageable pageable){
        Page<Borrower> borrowers = borrowerService.findAllBorrowers(pageable);
        Page<BorrowerAdminDTO> dtoPage = borrowers.map(borrowerAdminMapper::toDto);

        return ResponseEntity.ok(dtoPage);
    }

    // By name
    @GetMapping("/search/first-name")
        public ResponseEntity<Page<BorrowerAdminDTO>> findAllByFirstNameEqualsIgnoreCase(@RequestParam String firstName, Pageable pageable){
            Page<Borrower> borrowers = borrowerService.findAllByFirstNameEqualsIgnoreCase(firstName, pageable);
            Page<BorrowerAdminDTO> dtoPage = borrowers.map(borrowerAdminMapper::toDto);

            return ResponseEntity.ok(dtoPage);
        }

    @GetMapping("/search/last-name")
        public ResponseEntity<Page<BorrowerAdminDTO>> findAllByLastNameEqualsIgnoreCase(@RequestParam String lastName, Pageable pageable){
            Page<Borrower> borrowers = borrowerService.findAllByLastNameEqualsIgnoreCase(lastName, pageable);
            Page<BorrowerAdminDTO> dtoPage = borrowers.map(borrowerAdminMapper::toDto);

            return ResponseEntity.ok(dtoPage);
        }

    @GetMapping("/search/full-name")
        public ResponseEntity<Page<BorrowerAdminDTO>> findAllByFirstNameEqualsIgnoreCaseAndLastNameEqualsIgnoreCase(@RequestParam String firstName, @RequestParam String lastName, Pageable pageable){
            Page<Borrower> borrowers = borrowerService.findAllByFirstNameEqualsIgnoreCaseAndLastNameEqualsIgnoreCase(firstName, lastName, pageable);
            Page<BorrowerAdminDTO> dtoPage = borrowers.map(borrowerAdminMapper::toDto);

            return ResponseEntity.ok(dtoPage);
        }

    // By  active loans counter
    @GetMapping("/search/active-loans-lesser")
        public ResponseEntity<Page<BorrowerAdminDTO>> findAllByActiveLoansCountIsLessThanEqual(@RequestParam Integer activeLoansCountIsLessThan, Pageable pageable){
            Page<Borrower> borrowers = borrowerService.findAllByActiveLoansCountIsLessThanEqual(activeLoansCountIsLessThan, pageable);
            Page<BorrowerAdminDTO> dtoPage = borrowers.map(borrowerAdminMapper::toDto);

            return ResponseEntity.ok(dtoPage);
        }

    @GetMapping("/search/active-loans-greater")
        public ResponseEntity<Page<BorrowerAdminDTO>> findAllByActiveLoansCountIsGreaterThanEqual(@RequestParam Integer activeLoansCountIsGreaterThan, Pageable pageable){
            Page<Borrower> borrowers = borrowerService.findAllByActiveLoansCountIsGreaterThanEqual(activeLoansCountIsGreaterThan, pageable);
            Page<BorrowerAdminDTO> dtoPage = borrowers.map(borrowerAdminMapper::toDto);

            return ResponseEntity.ok(dtoPage);
        }

    @GetMapping("/search/active-loans-range")
        public ResponseEntity<Page<BorrowerAdminDTO>> findAllByActiveLoansCountIsBetween(@RequestParam Integer activeLoansCountAfter, @RequestParam Integer activeLoansCountBefore, Pageable pageable){
            Page<Borrower> borrowers = borrowerService.findAllByActiveLoansCountIsBetween(activeLoansCountAfter, activeLoansCountBefore, pageable);
            Page<BorrowerAdminDTO> dtoPage = borrowers.map(borrowerAdminMapper::toDto);

            return ResponseEntity.ok(dtoPage);
        }

    // By Acc status
    @GetMapping("/search/acc-status")
    public ResponseEntity<Page<BorrowerAdminDTO>> findAllByBorrowerAccStatus(@RequestParam BorrowerAccStatus borrowerAccStatus, Pageable pageable){
        Page<Borrower> borrowers = borrowerService.findAllByBorrowerAccStatus(borrowerAccStatus, pageable);
        Page<BorrowerAdminDTO> dtoPage = borrowers.map(borrowerAdminMapper::toDto);

        return ResponseEntity.ok(dtoPage);
    }

    // By borrower loan count
    @GetMapping("/search/total-loans-lesser")
        public ResponseEntity<Page<BorrowerAdminDTO>> findAllByLoanCountIsLessThanEqual(@RequestParam Integer loanCountIsLessThan, Pageable pageable){
            Page<Borrower> borrowers = borrowerService.findAllByLoanCountIsLessThanEqual(loanCountIsLessThan, pageable);
            Page<BorrowerAdminDTO> dtoPage = borrowers.map(borrowerAdminMapper::toDto);

            return ResponseEntity.ok(dtoPage);
        }

    @GetMapping("/search/total-loans-greater")
        public ResponseEntity<Page<BorrowerAdminDTO>> findAllByLoanCountIsGreaterThanEqual(@RequestParam Integer loanCountIsGreaterThan, Pageable pageable){
            Page<Borrower> borrowers = borrowerService.findAllByLoanCountIsGreaterThanEqual(loanCountIsGreaterThan, pageable);
            Page<BorrowerAdminDTO> dtoPage = borrowers.map(borrowerAdminMapper::toDto);

            return ResponseEntity.ok(dtoPage);
        }

    @GetMapping("/search/total-loans-range")
        public ResponseEntity<Page<BorrowerAdminDTO>> findAllByLoanCountIsBetween(@RequestParam Integer loanCountAfter, @RequestParam Integer loanCountBefore, Pageable pageable){
            Page<Borrower> borrowers = borrowerService.findAllByLoanCountIsBetween(loanCountAfter, loanCountBefore, pageable);
            Page<BorrowerAdminDTO> dtoPage = borrowers.map(borrowerAdminMapper::toDto);

            return ResponseEntity.ok(dtoPage);
        }

    // By borrower acc type
    @GetMapping("/search/acc-type")
    public ResponseEntity<Page<BorrowerAdminDTO>> findAllByBorrowerAccType(@RequestParam BorrowerAccType borrowerAccType, Pageable pageable){
        Page<Borrower> borrowers = borrowerService.findAllByBorrowerAccType(borrowerAccType, pageable);
        Page<BorrowerAdminDTO> dtoPage = borrowers.map(borrowerAdminMapper::toDto);

        return ResponseEntity.ok(dtoPage);
    }
}
