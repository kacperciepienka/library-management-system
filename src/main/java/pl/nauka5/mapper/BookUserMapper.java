package pl.nauka5.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.nauka5.dto.BookUserDTO;
import pl.nauka5.model.Book;

@Mapper(componentModel = "spring")
public interface BookUserMapper {

    @Mapping(target = "authorName", expression = "java(book.getAuthor().getFirstName() + \" \" + book.getAuthor().getLastName())")
    BookUserDTO toDto(Book book);
}
