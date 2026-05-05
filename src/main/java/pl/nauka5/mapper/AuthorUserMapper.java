package pl.nauka5.mapper;

import org.mapstruct.Mapper;
import pl.nauka5.dto.AuthorUserDTO;
import pl.nauka5.model.Author;

@Mapper(componentModel = "spring")
public interface AuthorUserMapper {

    AuthorUserDTO toDto(Author author);
}
