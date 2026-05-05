package pl.nauka5.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.nauka5.dto.AuthorUserDTO;
import pl.nauka5.mapper.AuthorUserMapper;
import pl.nauka5.model.Author;
import pl.nauka5.service.AuthorService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0.1/user/author")
public class AuthorUserController {
    private final AuthorService authorService;
    private final AuthorUserMapper authorUserMapper;

    // Get
    // All
    @GetMapping
    public ResponseEntity<Page<AuthorUserDTO>> findAll(Pageable pageable){
        Page<Author> authors = authorService.findAll(pageable);
        Page<AuthorUserDTO> dtoPage = authors.map(authorUserMapper::toDto);

        return ResponseEntity.ok(dtoPage);
    }

    // By first name
    @GetMapping("/search/first-name")
    public ResponseEntity<Page<AuthorUserDTO>> findAllByFirstNameEqualsIgnoreCase(@RequestParam String firstName, Pageable pageable) {
        Page<Author> authors = authorService.findAllByFirstNameEqualsIgnoreCase(firstName, pageable);
        Page<AuthorUserDTO> dtoPage = authors.map(authorUserMapper::toDto);

        return ResponseEntity.ok(dtoPage);
    }

    // By last name
    @GetMapping("/search/last-name")
    public ResponseEntity<Page<AuthorUserDTO>> findAllByLastNameEqualsIgnoreCase(@RequestParam String lastName, Pageable pageable) {
        Page<Author> authors = authorService.findAllByLastNameEqualsIgnoreCase(lastName, pageable);
        Page<AuthorUserDTO> dtoPage = authors.map(authorUserMapper::toDto);

        return ResponseEntity.ok(dtoPage);
    }

    // By first and last name
    @GetMapping("/search/full-name")
    public ResponseEntity<Page<AuthorUserDTO>> findAllByFirstNameEqualsIgnoreCaseAndLastNameEqualsIgnoreCase(@RequestParam String firstName, @RequestParam String lastName, Pageable pageable) {
        Page<Author> authors = authorService.findAllByFirstNameEqualsIgnoreCaseAndLastNameEqualsIgnoreCase(firstName, lastName, pageable);
        Page<AuthorUserDTO> dtoPage = authors.map(authorUserMapper::toDto);

        return ResponseEntity.ok(dtoPage);
    }

    // By nationality
    @GetMapping("/search/nationality")
    public ResponseEntity<Page<AuthorUserDTO>> findAllByNationalityEqualsIgnoreCase(@RequestParam String nationality, Pageable pageable) {
        Page<Author> authors = authorService.findAllByNationalityEqualsIgnoreCase(nationality, pageable);
        Page<AuthorUserDTO> dtoPage = authors.map(authorUserMapper::toDto);

        return ResponseEntity.ok(dtoPage);
    }

    //By last name and nationality
    @GetMapping("/search/last-name-and-nationality")
    public ResponseEntity<Page<AuthorUserDTO>> findAllByLastNameEqualsIgnoreCaseAndNationalityEqualsIgnoreCase(@RequestParam String lastName, @RequestParam String nationality, Pageable pageable) {
        Page<Author> authors = authorService.findAllByLastNameEqualsIgnoreCaseAndNationalityEqualsIgnoreCase(lastName, nationality, pageable);
        Page<AuthorUserDTO> dtoPage = authors.map(authorUserMapper::toDto);

        return ResponseEntity.ok(dtoPage);
    }
}
