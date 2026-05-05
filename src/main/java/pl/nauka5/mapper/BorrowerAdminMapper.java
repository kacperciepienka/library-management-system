package pl.nauka5.mapper;

import org.mapstruct.Mapper;
import pl.nauka5.dto.BorrowerAdminDTO;
import pl.nauka5.model.Borrower;

@Mapper(componentModel = "spring")
public interface BorrowerAdminMapper {

    BorrowerAdminDTO toDto(Borrower borrower);
}
