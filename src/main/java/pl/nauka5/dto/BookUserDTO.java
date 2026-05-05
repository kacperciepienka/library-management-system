package pl.nauka5.dto;

import lombok.Data;
import pl.nauka5.model.BookGenreType;

import java.math.BigDecimal;

@Data
public class BookUserDTO {
    private String title;
    private String authorName;
    private BookGenreType bookGenreType;
    private Integer releaseYear;
    private BigDecimal pricePerDay;
    private String isbn;
    private Boolean isAvailable;
}
