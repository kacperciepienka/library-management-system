package pl.nauka5.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.nauka5.dto.AuthorAdminDTO;
import pl.nauka5.mapper.AuthorAdminMapper;
import pl.nauka5.model.Author;
import pl.nauka5.service.AuthorService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0.1/admin/author")
public class AuthorAdminController {
    private final AuthorService authorService;
    private final AuthorAdminMapper authorAdminMapper;

    // POST
    @PostMapping
    public ResponseEntity<AuthorAdminDTO> addAuthor(@Valid @RequestBody Author author){
        Author authorToSave = authorService.addAuthor(author);
        return ResponseEntity.status(HttpStatus.CREATED).body(authorAdminMapper.toDto(authorToSave));
    }

    // GET
    @GetMapping("/search/{id}")
    public ResponseEntity<AuthorAdminDTO> findAuthorById(@PathVariable Long id){
        Author author = authorService.findAuthorById(id);
        return ResponseEntity.ok(authorAdminMapper.toDto(author));
    }

    @GetMapping
    public ResponseEntity<Page<AuthorAdminDTO>> findAll(Pageable pageable){
        Page<Author> authors = authorService.findAll(pageable);
        Page<AuthorAdminDTO> dtoPage = authors.map(authorAdminMapper::toDto);

        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/search/first-name")
    public ResponseEntity<Page<AuthorAdminDTO>> findAllByFirstNameEqualsIgnoreCase(@RequestParam String firstName, Pageable pageable){
        Page<Author> authors = authorService.findAllByFirstNameEqualsIgnoreCase(firstName, pageable);
        Page<AuthorAdminDTO> dtoPage = authors.map(authorAdminMapper::toDto);

        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/search/last-name")
    public ResponseEntity<Page<AuthorAdminDTO>> findAllByLastNameEqualsIgnoreCase(@RequestParam String lastName, Pageable pageable){
        Page<Author> authors = authorService.findAllByLastNameEqualsIgnoreCase(lastName, pageable);
        Page<AuthorAdminDTO> dtoPage = authors.map(authorAdminMapper::toDto);

        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/search/full-name")
    public ResponseEntity<Page<AuthorAdminDTO>> findAllByFirstNameEqualsIgnoreCaseAndLastNameEqualsIgnoreCase(@RequestParam String firstName, @RequestParam String lastName, Pageable pageable){
        Page<Author> authors = authorService.findAllByFirstNameEqualsIgnoreCaseAndLastNameEqualsIgnoreCase(firstName, lastName, pageable);
        Page<AuthorAdminDTO> dtoPage = authors.map(authorAdminMapper::toDto);

        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/search/nationality")
    public ResponseEntity<Page<AuthorAdminDTO>> findAllByNationalityEqualsIgnoreCase(@RequestParam String nationality, Pageable pageable){
        Page<Author> authors = authorService.findAllByNationalityEqualsIgnoreCase(nationality, pageable);
        Page<AuthorAdminDTO> dtoPage = authors.map(authorAdminMapper::toDto);

        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/search/last-name-and-nationality")
    public ResponseEntity<Page<AuthorAdminDTO>> findAllByLastNameEqualsIgnoreCaseAndNationalityEqualsIgnoreCase(@RequestParam String lastName, @RequestParam String nationality, Pageable pageable){
        Page<Author> authors = authorService.findAllByLastNameEqualsIgnoreCaseAndNationalityEqualsIgnoreCase(lastName, nationality, pageable);
        Page<AuthorAdminDTO> dtoPage = authors.map(authorAdminMapper::toDto);

        return ResponseEntity.ok(dtoPage);
    }

}
