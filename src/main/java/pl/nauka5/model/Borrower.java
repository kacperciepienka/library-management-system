package pl.nauka5.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "BORROWERS")
public class Borrower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Borrower: first name can't be empty")
    @Column(length = 40)
    @Length(max = 40, message = "Borrower: first name can't be longer than 40 characters")
    private String firstName;

    @NotBlank(message = "Borrower: last name can't be empty")
    @Column(length = 50)
    @Length(max = 50, message = "Borrower: last name can't be longer than 50 characters")
    private String lastName;

    @NotBlank(message = "Borrower: email can't be empty") // dla przesyłania powiadomień o oddaniu książki
    @Email(message = "Borrower: email must have good format (...@...com/pl)")
    @Column(unique = true, comment = "Borrower: email has to be unique")
    private String email;

    // może być null, bo sami go nadpiszmy automatem w service
    @Column(unique = true)
    private Integer libraryCardNumber;

    @Min(value = 0, message = "Borrower can't have less than 0 overall loans")
    private Integer loanCount;

    @Enumerated(EnumType.STRING)
    // może być null, bo sami nadamy go w serwisie
    private BorrowerAccType borrowerAccType;

    // może być null, bo automatycznie nadamy
    @DecimalMax(value = "1.00", message = "Minimum discount (BEGINNER) -0%")
    @DecimalMin(value = "0.70", message = "Maximum discount (VIP) -30%")
    private BigDecimal discount;

    @Min(value = 0, message = "Borrower: borrower can't have negative number of loans")
    @Max(value = 5, message = "Borrower: borrower can't have more than 5 active loans at once")
    // może być null, bo sami zapisujemy ile ma w servisie (standardowo ma 0)
    private Integer activeLoansCount;

    @Enumerated(EnumType.STRING)
    private BorrowerAccStatus borrowerAccStatus;
}
