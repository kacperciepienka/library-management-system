package pl.nauka5.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "LOANS")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "borrower_id")
    private Borrower borrower;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    // może być null, bo sami to ustawiamy w service
    @Column(updatable = false)
    private LocalDate loanDate;

    // tutaj też null, bo ustala się samo, gdy się zwraca
    private LocalDate returnDate;

    // Zawsze 14 dni to standard zwrotu później np. 2 zł/dzień
    // może być null, bo to obliczamy automatycznie w service
    @Column(updatable = false) // zablokujmy to, żeby nie było, że po znajomości ktoś da dłużej
    private LocalDate dueDate;

    // ustawione na 0, ale bdy return date - loan date > due date to  wtedy różnice * 2zł i to = penaltyFee
    @DecimalMin(value = "0.00", message = "Loan: min penalty fee is 0,00 PLN")
    private BigDecimal penaltyFee = BigDecimal.ZERO;

    @DecimalMin(value = "0.00", message = "Loan: price when book was loaned can't be lesser than 0.00PLN")
    @Column(updatable = false)
    private BigDecimal pricePerDayWhenBookWasLoaned;

    @DecimalMin(value = "0.00", message = "Loan: min price for loan is 0.00 PLN")
    // może być null, sami będziemy set robić w service (różnica dat, koszt wypożyczenia i ewentualna kara)
    private BigDecimal finalPrice;

    // może być null, bo sami sobie przypiszemy
    @Enumerated(EnumType.STRING)
    private LoanStatus loanStatus;
}
