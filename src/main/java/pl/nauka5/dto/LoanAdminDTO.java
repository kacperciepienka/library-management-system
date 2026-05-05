package pl.nauka5.dto;

import lombok.Data;
import pl.nauka5.model.BorrowerAccType;
import pl.nauka5.model.LoanStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class LoanAdminDTO {
    private Long id;
    private Integer borrowerCardNumber;
    private String borrowerName;
    private BorrowerAccType borrowerAccType;
    private String bookTitle;
    private String bookAuthorName;
    private String bookISBN;
    private LocalDate loanDate;
    private LocalDate returnDate;
    private LocalDate dueDate;
    private BigDecimal penaltyFee;
    private BigDecimal pricePerDayWhenBookWasLoaned;
    private BigDecimal finalPrice;
    private LoanStatus loanStatus;
}
