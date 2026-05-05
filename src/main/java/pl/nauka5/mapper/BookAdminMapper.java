package pl.nauka5.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.nauka5.dto.BookAdminDTO;
import pl.nauka5.model.Book;

@Mapper(componentModel = "spring")
public interface BookAdminMapper {

    @Mapping(target = "authorName", expression = "java(book.getAuthor().getFirstName() + \" \" + book.getAuthor().getLastName())")
    BookAdminDTO toDto(Book book);
}
