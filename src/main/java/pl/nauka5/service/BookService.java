package pl.nauka5.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.nauka5.exception.AuthorNotFoundException;
import pl.nauka5.exception.BookAlreadyExistException;
import pl.nauka5.exception.BookNotFoundException;
import pl.nauka5.exception.InvalidBookNewPriceException;
import pl.nauka5.model.Author;
import pl.nauka5.model.Book;
import pl.nauka5.model.BookGenreType;
import pl.nauka5.repository.AuthorRepository;
import pl.nauka5.repository.BookRepository;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    // POST
    public Book addBook(Book bookToAdd, Long authorId) {
        Author author = authorRepository.findById(authorId)
                        .orElseThrow(() -> new AuthorNotFoundException(authorId));

        bookRepository.findBookByIsbnEqualsIgnoreCase(bookToAdd.getIsbn())
                .ifPresent(c -> {
                    throw new BookAlreadyExistException(bookToAdd.getIsbn());
                });

        bookToAdd.setAuthor(author);
        bookToAdd.setIsAvailable(true);
        return bookRepository.save(bookToAdd);
    }

    // DELETE
    public void deleteBook(String isbnNumber) {
        Book book = bookRepository.findBookByIsbnEqualsIgnoreCase(isbnNumber)
                .orElseThrow(() -> new BookNotFoundException(isbnNumber));

        bookRepository.delete(book);
    }

    // PUT
    public Book updatePricePerDay(String isbnNumber, BigDecimal newPricePerDay) {
        Book book = bookRepository.findBookByIsbnEqualsIgnoreCase(isbnNumber)
                .orElseThrow(() -> new BookNotFoundException(isbnNumber));

        if (newPricePerDay.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidBookNewPriceException(newPricePerDay);
        }

        book.setPricePerDay(newPricePerDay);
        return bookRepository.save(book);
    }

    public Book updateAvailable(String isbnNumber, Boolean newAvailable) {
        Book book = bookRepository.findBookByIsbnEqualsIgnoreCase(isbnNumber)
                .orElseThrow(() -> new BookNotFoundException(isbnNumber));

        book.setIsAvailable(newAvailable);
        return bookRepository.save(book);
    }

    // GET
    public Book findBookByIsbnNumber(String isbnNumber) {
        return bookRepository.findBookByIsbnEqualsIgnoreCase(isbnNumber)
                .orElseThrow(() -> new BookNotFoundException(isbnNumber));
    }

    public Page<Book> findAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public Page<Book> findAllByTitleEqualsIgnoreCase(String title, Pageable pageable) {
        return bookRepository.findAllByTitleEqualsIgnoreCase(title, pageable);
    }

    // By Author
        public Page<Book> findAllByAuthor_Id(Long authorId, Pageable pageable) {
            return bookRepository.findAllByAuthor_Id(authorId, pageable);
        }

        public Page<Book> findAllByAuthor_FirstNameEqualsIgnoreCase(String authorFirstName, Pageable pageable) {
            return bookRepository.findAllByAuthor_FirstNameEqualsIgnoreCase(authorFirstName, pageable);
        }

        public Page<Book> findAllByAuthor_LastNameEqualsIgnoreCase(String authorLastName, Pageable pageable) {
            return bookRepository.findAllByAuthor_LastNameEqualsIgnoreCase(authorLastName, pageable);
        }

        public Page<Book> findAllByAuthor_FirstNameEqualsIgnoreCaseAndAuthor_LastNameEqualsIgnoreCase(String authorFirstName, String authorLastName, Pageable pageable) {
            return bookRepository.findAllByAuthor_FirstNameEqualsIgnoreCaseAndAuthor_LastNameEqualsIgnoreCase(authorFirstName, authorLastName, pageable);
        }

        public Page<Book> findAllByAuthor_NationalityEqualsIgnoreCase(String nationality, Pageable pageable) {
            return bookRepository.findAllByAuthor_NationalityEqualsIgnoreCase(nationality, pageable);
        }

    // By type
    public Page<Book> findAllByBookGenreType(BookGenreType bookGenreType, Pageable pageable) {
        return bookRepository.findAllByBookGenreType(bookGenreType, pageable);
    }

    // By release year
        public Page<Book> findAllByReleaseYearIsLessThanEqual(Integer releaseYearIsLessThan, Pageable pageable) {
            return bookRepository.findAllByReleaseYearIsLessThanEqual(releaseYearIsLessThan, pageable);
        }

        public Page<Book> findAllByReleaseYearIsGreaterThanEqual(Integer releaseYearIsGreaterThan, Pageable pageable) {
            return bookRepository.findAllByReleaseYearIsGreaterThanEqual(releaseYearIsGreaterThan, pageable);
        }

        public Page<Book> findAllByReleaseYearIsBetween(Integer releaseYearAfter, Integer releaseYearBefore, Pageable pageable) {
            return bookRepository.findAllByReleaseYearIsBetween(releaseYearAfter, releaseYearBefore, pageable);
        }

    // By price per day
    public Page<Book> findAllByPricePerDayIsLessThanEqual(BigDecimal pricePerDayIsLessThan, Pageable pageable) {
        return bookRepository.findAllByPricePerDayIsLessThanEqual(pricePerDayIsLessThan, pageable);
    }

    public Page<Book> findAllByPricePerDayIsGreaterThanEqual(BigDecimal pricePerDayIsGreaterThan, Pageable pageable) {
        return bookRepository.findAllByPricePerDayIsGreaterThanEqual(pricePerDayIsGreaterThan, pageable);
    }

    public Page<Book> findAllByPricePerDayIsBetween(BigDecimal pricePerDayAfter, BigDecimal pricePerDayBefore, Pageable pageable) {
        return bookRepository.findAllByPricePerDayIsBetween(pricePerDayAfter, pricePerDayBefore, pageable);
    }

    public Page<Book> findAllByIsAvailable(Boolean isAvailable, Pageable pageable) {
        return bookRepository.findAllByIsAvailable(isAvailable, pageable);
    }
}
