package pl.nauka5.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.nauka5.model.Book;
import pl.nauka5.model.BookGenreType;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    // Nie szukamy po ID, bo nikt nie ma do niego dostępu (Admin ani User)
    // By ISBN (tu nie dajemy page, bo zawsze będzie max. 1 obiekt)
    Optional<Book> findBookByIsbnEqualsIgnoreCase(String isbnNumber);

    // By tittle
    Page<Book> findAllByTitleEqualsIgnoreCase(String title, Pageable pageable);

    // By Author
        // id
        Page<Book> findAllByAuthor_Id(Long authorId, Pageable pageable);
        // name
        Page<Book> findAllByAuthor_FirstNameEqualsIgnoreCase(String authorFirstName, Pageable pageable);
        Page<Book> findAllByAuthor_LastNameEqualsIgnoreCase(String authorLastName, Pageable pageable);
        Page<Book> findAllByAuthor_FirstNameEqualsIgnoreCaseAndAuthor_LastNameEqualsIgnoreCase(String authorFirstName, String authorLastName, Pageable pageable);
        //nationality
        Page<Book> findAllByAuthor_NationalityEqualsIgnoreCase(String nationality, Pageable pageable);

    // By type
    Page<Book> findAllByBookGenreType(BookGenreType bookGenreType, Pageable pageable);

    // By release year
    Page<Book> findAllByReleaseYearIsLessThanEqual(Integer releaseYearIsLessThan, Pageable pageable);
    Page<Book> findAllByReleaseYearIsGreaterThanEqual(Integer releaseYearIsGreaterThan, Pageable pageable);
    Page<Book> findAllByReleaseYearIsBetween(Integer releaseYearAfter, Integer releaseYearBefore, Pageable pageable);

    // By price per day
    Page<Book> findAllByPricePerDayIsLessThanEqual(BigDecimal pricePerDayIsLessThan, Pageable pageable);
    Page<Book> findAllByPricePerDayIsGreaterThanEqual(BigDecimal pricePerDayIsGreaterThan, Pageable pageable);
    Page<Book> findAllByPricePerDayIsBetween(BigDecimal pricePerDayAfter, BigDecimal pricePerDayBefore, Pageable pageable);

    Page<Book> findAllByIsAvailable(Boolean isAvailable, Pageable pageable);
}
