package pl.nauka5.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.nauka5.dto.BookAdminDTO;
import pl.nauka5.mapper.BookAdminMapper;
import pl.nauka5.model.Book;
import pl.nauka5.model.BookGenreType;
import pl.nauka5.service.BookService;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0.1/admin/book")
public class BookAdminController {
    private final BookService bookService;
    private final BookAdminMapper bookAdminMapper;


// POST
    @PostMapping("/{authorId}")
    public ResponseEntity<BookAdminDTO> addBook(@Valid @RequestBody Book book, @PathVariable Long authorId) {
        Book bookToSave = bookService.addBook(book, authorId);

        return ResponseEntity.status(HttpStatus.CREATED).body(bookAdminMapper.toDto(bookToSave));
    }

// DELETE
    @DeleteMapping("/{isbnNumber}")
    public ResponseEntity<Void> deleteBook(@PathVariable String isbnNumber) {
        bookService.deleteBook(isbnNumber);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

// PUT
    @PutMapping("/{isbnNumber}/new-price")
    public ResponseEntity<BookAdminDTO> updatePricePerDay(@PathVariable String isbnNumber, @RequestParam BigDecimal newPrice) {
        Book book = bookService.updatePricePerDay(isbnNumber, newPrice);

        return ResponseEntity.ok(bookAdminMapper.toDto(book));
    }

    @PutMapping("/{isbnNumber}/new-available")
    public ResponseEntity<BookAdminDTO> updateAvailable(@PathVariable String isbnNumber, @RequestParam Boolean newAvailable) {
        Book book = bookService.updateAvailable(isbnNumber, newAvailable);

        return ResponseEntity.ok(bookAdminMapper.toDto(book));
    }

// GET
// By isbn Number
    @GetMapping("/{isbnNumber}")
    public ResponseEntity<BookAdminDTO> findBookByIsbnNumber(@PathVariable String isbnNumber) {
        Book book = bookService.findBookByIsbnNumber(isbnNumber);

        return ResponseEntity.ok(bookAdminMapper.toDto(book));
    }


// All books
    @GetMapping
    public ResponseEntity<Page<BookAdminDTO>> findAllBooks(Pageable pageable) {
        Page<Book> books = bookService.findAllBooks(pageable);
        Page<BookAdminDTO> dtoPage = books.map(bookAdminMapper::toDto);

        return ResponseEntity.ok(dtoPage);
    }

// By title
    @GetMapping("/search/title")
    public ResponseEntity<Page<BookAdminDTO>> findAllByTitleEqualsIgnoreCase(@RequestParam String title, Pageable pageable) {
        Page<Book> books = bookService.findAllByTitleEqualsIgnoreCase(title, pageable);
        Page<BookAdminDTO> dtoPage = books.map(bookAdminMapper::toDto);

        return ResponseEntity.ok(dtoPage);
    }

// By Author
    @GetMapping("/search/author/{authorId}")
    public ResponseEntity<Page<BookAdminDTO>> findAllByAuthor_Id(@PathVariable Long authorId, Pageable pageable) {
        Page<Book> books = bookService.findAllByAuthor_Id(authorId, pageable);
        Page<BookAdminDTO> dtoPage = books.map(bookAdminMapper::toDto);

        return ResponseEntity.ok(dtoPage);

    }

    @GetMapping("/search/author/first-name")
    public ResponseEntity<Page<BookAdminDTO>> findAllByAuthor_FirstNameEqualsIgnoreCase(@RequestParam String authorFirstName, Pageable pageable) {
        Page<Book> books = bookService.findAllByAuthor_FirstNameEqualsIgnoreCase(authorFirstName, pageable);
        Page<BookAdminDTO> dtoPage = books.map(bookAdminMapper::toDto);

        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/search/author/last-name")
    public ResponseEntity<Page<BookAdminDTO>> findAllByAuthor_LastNameEqualsIgnoreCase(@RequestParam String authorLastName, Pageable pageable) {
        Page<Book> books = bookService.findAllByAuthor_LastNameEqualsIgnoreCase(authorLastName, pageable);
        Page<BookAdminDTO> dtoPage = books.map(bookAdminMapper::toDto);

        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/search/author/full-name")
    public ResponseEntity<Page<BookAdminDTO>> findAllByAuthor_FirstNameEqualsIgnoreCaseAndAuthor_LastNameEqualsIgnoreCase(@RequestParam String authorFirstName, @RequestParam String authorLastName, Pageable pageable) {
        Page<Book> books = bookService.findAllByAuthor_FirstNameEqualsIgnoreCaseAndAuthor_LastNameEqualsIgnoreCase(authorFirstName, authorLastName, pageable);
        Page<BookAdminDTO> dtoPage = books.map(bookAdminMapper::toDto);

        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/search/author/nationality")
    public ResponseEntity<Page<BookAdminDTO>> findAllByAuthor_NationalityEqualsIgnoreCase(@RequestParam String nationality, Pageable pageable) {
        Page<Book> books = bookService.findAllByAuthor_NationalityEqualsIgnoreCase(nationality, pageable);
        Page<BookAdminDTO> dtoPage = books.map(bookAdminMapper::toDto);

        return ResponseEntity.ok(dtoPage);
    }


// By type
    @GetMapping("/search/type")
    public ResponseEntity<Page<BookAdminDTO>> findAllByBookGenreType(@RequestParam BookGenreType bookGenreType, Pageable pageable) {
        Page<Book> books = bookService.findAllByBookGenreType(bookGenreType, pageable);
        Page<BookAdminDTO> dtoPage = books.map(bookAdminMapper::toDto);

        return ResponseEntity.ok(dtoPage);
    }

// By release year
    @GetMapping("/search/reless-lesser")
    public ResponseEntity<Page<BookAdminDTO>> findAllByReleaseYearIsLessThanEqual(@RequestParam Integer releaseYearIsLessThan, Pageable pageable) {
        Page<Book> books = bookService.findAllByReleaseYearIsLessThanEqual(releaseYearIsLessThan, pageable);
        Page<BookAdminDTO> dtoPage = books.map(bookAdminMapper::toDto);

        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/search/reless-greater")
    public ResponseEntity<Page<BookAdminDTO>> findAllByReleaseYearIsGreaterThanEqual(@RequestParam Integer releaseYearIsGreaterThan, Pageable pageable) {
        Page<Book> books = bookService.findAllByReleaseYearIsGreaterThanEqual(releaseYearIsGreaterThan, pageable);
        Page<BookAdminDTO> dtoPage = books.map(bookAdminMapper::toDto);

        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/search/relesses-between")
    public ResponseEntity<Page<BookAdminDTO>> findAllByReleaseYearIsBetween(@RequestParam Integer releaseYearAfter, @RequestParam Integer releaseYearBefore, Pageable pageable) {
        Page<Book> books = bookService.findAllByReleaseYearIsBetween(releaseYearAfter, releaseYearBefore, pageable);
        Page<BookAdminDTO> dtoPage = books.map(bookAdminMapper::toDto);

        return ResponseEntity.ok(dtoPage);
    }

// By price per day
    @GetMapping("/search/price-lesser")
    public ResponseEntity<Page<BookAdminDTO>> findAllByPricePerDayIsLessThanEqual(@RequestParam BigDecimal pricePerDayIsLessThan, Pageable pageable) {
        Page<Book> books = bookService.findAllByPricePerDayIsLessThanEqual(pricePerDayIsLessThan, pageable);
        Page<BookAdminDTO> dtoPage = books.map(bookAdminMapper::toDto);

        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/search/price-greater")
    public ResponseEntity<Page<BookAdminDTO>> findAllByPricePerDayIsGreaterThanEqual(@RequestParam BigDecimal pricePerDayIsGreaterThan, Pageable pageable) {
        Page<Book> books = bookService.findAllByPricePerDayIsGreaterThanEqual(pricePerDayIsGreaterThan, pageable);
        Page<BookAdminDTO> dtoPage = books.map(bookAdminMapper::toDto);

        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/search/price-range")
    public ResponseEntity<Page<BookAdminDTO>> findAllByPricePerDayIsBetween(@RequestParam BigDecimal pricePerDayAfter, @RequestParam BigDecimal pricePerDayBefore, Pageable pageable) {
        Page<Book> books = bookService.findAllByPricePerDayIsBetween(pricePerDayAfter, pricePerDayBefore, pageable);
        Page<BookAdminDTO> dtoPage = books.map(bookAdminMapper::toDto);

        return ResponseEntity.ok(dtoPage);
    }

// By available
    @GetMapping("/search/available")
    public ResponseEntity<Page<BookAdminDTO>> findAllByIsAvailable(@RequestParam Boolean isAvailable, Pageable pageable) {
        Page<Book> books = bookService.findAllByIsAvailable(isAvailable, pageable);
        Page<BookAdminDTO> dtoPage = books.map(bookAdminMapper::toDto);

        return ResponseEntity.ok(dtoPage);
    }
}