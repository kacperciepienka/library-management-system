package pl.nauka5.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "BOOKS")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Book: title can't be empty")  // tytuł może być dłuższy (nawet czasami bardzo długi)
    private String title;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @NotNull(message = "Book: genre can't be empty")
    @Enumerated(EnumType.STRING)
    private BookGenreType bookGenreType;

    @NotNull(message = "Book: release year can't be empty")
    @Min(value = -2100, message = "Book: there is no older book in the World")
    @Max(value = 2026 , message = "Book: Can't add book newer than current year")  // aktualizujemy co roku
    private Integer releaseYear;

    // Są książki darmowe i są książki płatne
    @NotNull(message = "Book: price per day for loan can't be empty")
    @DecimalMin(value = "0.00", message = "Book: price per day for loan can't be less than 0.00 PLN")
    private BigDecimal pricePerDay;

    @NotBlank(message = "Book: ISBN number can't be empty")
    @Column(unique = true, length = 13, comment = "ISBN is a unique book identification number")
    @Length(min = 13, max = 13, message = "Book: ISBN number always has 13 characters")
    private String isbn;

    // może być null, bo w service i tak sam robi Set
    private Boolean isAvailable;
}
