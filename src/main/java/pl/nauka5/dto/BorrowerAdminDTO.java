package pl.nauka5.dto;

import lombok.Data;
import pl.nauka5.model.BorrowerAccStatus;
import pl.nauka5.model.BorrowerAccType;

@Data
public class BorrowerAdminDTO {
    // Admin nie potrzebuje id, bo może sprawdzać po numerze karty
    private String firstName;
    private String lastName;
    private String email;
    private Integer libraryCardNumber;
    private Integer loanCount;
    private BorrowerAccType borrowerAccType;
    private Integer activeLoansCount;
    private BorrowerAccStatus borrowerAccStatus;
}
