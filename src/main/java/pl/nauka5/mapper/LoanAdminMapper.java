package pl.nauka5.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.nauka5.dto.LoanAdminDTO;
import pl.nauka5.model.Loan;

@Mapper(componentModel = "spring")
public interface LoanAdminMapper {

    @Mapping(target = "borrowerCardNumber", source = "borrower.libraryCardNumber")
    @Mapping(target = "borrowerName", expression = "java(loan.getBorrower().getFirstName() + \" \" + loan.getBorrower().getLastName())")
    @Mapping(target = "borrowerAccType", source = "borrower.borrowerAccType")
    @Mapping(target = "bookTitle", source = "book.title")
    @Mapping(target = "bookAuthorName", expression = "java(loan.getBook().getAuthor().getFirstName() + \" \" + loan.getBook().getAuthor().getLastName())")
    @Mapping(target = "bookISBN", source = "book.isbn")
    LoanAdminDTO toDto(Loan loan);
}
