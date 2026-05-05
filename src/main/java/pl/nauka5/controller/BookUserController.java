package pl.nauka5.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.nauka5.dto.BookUserDTO;
import pl.nauka5.mapper.BookUserMapper;
import pl.nauka5.model.Book;
import pl.nauka5.model.BookGenreType;
import pl.nauka5.service.BookService;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0.1/user/book")
public class BookUserController {
    private final BookService bookService;
    private final BookUserMapper bookUserMapper;

    // GET
    @GetMapping("/search/{isbn}")
    public ResponseEntity<BookUserDTO> findBookByIsbnNumber(@PathVariable String isbn) {
        Book book = bookService.findBookByIsbnNumber(isbn);
        return ResponseEntity.ok(bookUserMapper.toDto(book));
    }

    // All
    @GetMapping
    public ResponseEntity<Page<BookUserDTO>> findAllBooks(Pageable pageable) {
        Page<Book> books = bookService.findAllBooks(pageable);
        Page<BookUserDTO> dtoPage = books.map(bookUserMapper::toDto);

        return ResponseEntity.ok(dtoPage);
    }

    // By title
    @GetMapping("/search/title")
    public ResponseEntity<Page<BookUserDTO>> findAllByTitleEqualsIgnoreCase(@RequestParam String title, Pageable pageable) {
        Page<Book> books = bookService.findAllByTitleEqualsIgnoreCase(title, pageable);
        Page<BookUserDTO> dtoPage = books.map(bookUserMapper::toDto);

        return ResponseEntity.ok(dtoPage);
    }

    // By Author
    @GetMapping("/search/author/first-name")
        public ResponseEntity<Page<BookUserDTO>> findAllByAuthor_FirstNameEqualsIgnoreCase(@RequestParam String authorFirstName, Pageable pageable) {
            Page<Book> books = bookService.findAllByAuthor_FirstNameEqualsIgnoreCase(authorFirstName, pageable);
            Page<BookUserDTO> dtoPage = books.map(bookUserMapper::toDto);

            return ResponseEntity.ok(dtoPage);
        }

    @GetMapping("/search/author/last-name")
        public ResponseEntity<Page<BookUserDTO>> findAllByAuthor_LastNameEqualsIgnoreCase(@RequestParam String authorLastName, Pageable pageable) {
            Page<Book> books = bookService.findAllByAuthor_LastNameEqualsIgnoreCase(authorLastName, pageable);
            Page<BookUserDTO> dtoPage = books.map(bookUserMapper::toDto);

            return ResponseEntity.ok(dtoPage);
        }

    @GetMapping("/search/author/full-name")
        public ResponseEntity<Page<BookUserDTO>> findAllByAuthor_FirstNameEqualsIgnoreCaseAndAuthor_LastNameEqualsIgnoreCase(@RequestParam String authorFirstName, @RequestParam String authorLastName, Pageable pageable) {
            Page<Book> books = bookService.findAllByAuthor_FirstNameEqualsIgnoreCaseAndAuthor_LastNameEqualsIgnoreCase(authorFirstName, authorLastName, pageable);
            Page<BookUserDTO> dtoPage = books.map(bookUserMapper::toDto);

            return ResponseEntity.ok(dtoPage);
        }

    @GetMapping("/search/author/nationality")
        public ResponseEntity<Page<BookUserDTO>> findAllByAuthor_NationalityEqualsIgnoreCase(@RequestParam String nationality, Pageable pageable) {
            Page<Book> books = bookService.findAllByAuthor_NationalityEqualsIgnoreCase(nationality, pageable);
            Page<BookUserDTO> dtoPage = books.map(bookUserMapper::toDto);

            return ResponseEntity.ok(dtoPage);
        }

    // By type
    @GetMapping("/search/type")
    public ResponseEntity<Page<BookUserDTO>> findAllByBookGenreType(@RequestParam BookGenreType bookGenreType, Pageable pageable) {
        Page<Book> books = bookService.findAllByBookGenreType(bookGenreType, pageable);
        Page<BookUserDTO> dtoPage = books.map(bookUserMapper::toDto);

        return ResponseEntity.ok(dtoPage);
    }

    // By release year
    @GetMapping("/search/release-lesser")
        public ResponseEntity<Page<BookUserDTO>> findAllByReleaseYearIsLessThanEqual(@RequestParam Integer releaseYearIsLessThan, Pageable pageable) {
            Page<Book> books = bookService.findAllByReleaseYearIsLessThanEqual(releaseYearIsLessThan, pageable);
            Page<BookUserDTO> dtoPage = books.map(bookUserMapper::toDto);

            return ResponseEntity.ok(dtoPage);
        }

    @GetMapping("/search/release-greater")
        public ResponseEntity<Page<BookUserDTO>> findAllByReleaseYearIsGreaterThanEqual(@RequestParam Integer releaseYearIsGreaterThan, Pageable pageable) {
            Page<Book> books = bookService.findAllByReleaseYearIsGreaterThanEqual(releaseYearIsGreaterThan, pageable);
            Page<BookUserDTO> dtoPage = books.map(bookUserMapper::toDto);

            return ResponseEntity.ok(dtoPage);
        }

    @GetMapping("/search/releases-between")
        public ResponseEntity<Page<BookUserDTO>> findAllByReleaseYearIsBetween(@RequestParam Integer releaseYearAfter, @RequestParam Integer releaseYearBefore, Pageable pageable) {
            Page<Book> books = bookService.findAllByReleaseYearIsBetween(releaseYearAfter, releaseYearBefore, pageable);
            Page<BookUserDTO> dtoPage = books.map(bookUserMapper::toDto);

            return ResponseEntity.ok(dtoPage);
    }

    // By price per day
    @GetMapping("/search/price-lesser")
        public ResponseEntity<Page<BookUserDTO>> findAllByPricePerDayIsLessThanEqual(@RequestParam BigDecimal pricePerDayIsLessThan, Pageable pageable) {
            Page<Book> books = bookService.findAllByPricePerDayIsLessThanEqual(pricePerDayIsLessThan, pageable);
            Page<BookUserDTO> dtoPage = books.map(bookUserMapper::toDto);

            return ResponseEntity.ok(dtoPage);
        }

    @GetMapping("/search/price-greater")
        public ResponseEntity<Page<BookUserDTO>> findAllByPricePerDayIsGreaterThanEqual(@RequestParam BigDecimal pricePerDayIsGreaterThan, Pageable pageable) {
            Page<Book> books = bookService.findAllByPricePerDayIsGreaterThanEqual(pricePerDayIsGreaterThan, pageable);
            Page<BookUserDTO> dtoPage = books.map(bookUserMapper::toDto);

            return ResponseEntity.ok(dtoPage);
        }

    @GetMapping("/search/price-range")
        public ResponseEntity<Page<BookUserDTO>> findAllByPricePerDayIsBetween(@RequestParam BigDecimal pricePerDayAfter, @RequestParam BigDecimal pricePerDayBefore, Pageable pageable) {
            Page<Book> books = bookService.findAllByPricePerDayIsBetween(pricePerDayAfter, pricePerDayBefore, pageable);
            Page<BookUserDTO> dtoPage = books.map(bookUserMapper::toDto);

            return ResponseEntity.ok(dtoPage);
        }

    // By available
    @GetMapping("/search/available")
    public ResponseEntity<Page<BookUserDTO>> findAllByIsAvailable(@RequestParam Boolean isAvailable, Pageable pageable) {
        Page<Book> books = bookService.findAllByIsAvailable(isAvailable, pageable);
        Page<BookUserDTO> dtoPage = books.map(bookUserMapper::toDto);

        return ResponseEntity.ok(dtoPage);
    }
}
