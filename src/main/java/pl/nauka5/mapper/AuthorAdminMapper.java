package pl.nauka5.mapper;

import org.mapstruct.Mapper;
import pl.nauka5.dto.AuthorAdminDTO;
import pl.nauka5.model.Author;

@Mapper(componentModel = "spring")
public interface AuthorAdminMapper {
    AuthorAdminDTO toDto(Author author);
}
