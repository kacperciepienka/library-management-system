package pl.nauka5.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.nauka5.exception.AuthorNotFoundException;
import pl.nauka5.model.Author;
import pl.nauka5.repository.AuthorRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthorService {
    private final AuthorRepository authorRepository;
    // There is no method as PUT or DELETE because if we already had bok of author x,
    // and now we don't have it, we can have it in the future. PUT is unnecessary because
    // Author doesn't change his name or nationality

    // POST
    public Author addAuthor(Author authorToAdd){
        // there isn't any strong rules so we can just add
        return authorRepository.save(authorToAdd);
    }

    // GET
    public Author findAuthorById(Long id){
        return authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));
    }

    public Page<Author> findAll(Pageable pageable){
        return authorRepository.findAll(pageable);
    }

    public Page<Author> findAllByFirstNameEqualsIgnoreCase(String firstName, Pageable pageable){
        return authorRepository.findAllByFirstNameEqualsIgnoreCase(firstName, pageable);
    }

    public Page<Author> findAllByLastNameEqualsIgnoreCase(String lastName, Pageable pageable){
        return authorRepository.findAllByLastNameEqualsIgnoreCase(lastName, pageable);
    }

    public Page<Author> findAllByFirstNameEqualsIgnoreCaseAndLastNameEqualsIgnoreCase(String firstName, String lastName, Pageable pageable){
        return authorRepository.findAllByFirstNameEqualsIgnoreCaseAndLastNameEqualsIgnoreCase(firstName, lastName, pageable);
    }

    public Page<Author> findAllByNationalityEqualsIgnoreCase(String nationality, Pageable pageable){
        return authorRepository.findAllByNationalityEqualsIgnoreCase(nationality, pageable);
    }

    public Page<Author> findAllByLastNameEqualsIgnoreCaseAndNationalityEqualsIgnoreCase(String lastName, String nationality, Pageable pageable){
        return authorRepository.findAllByLastNameEqualsIgnoreCaseAndNationalityEqualsIgnoreCase(lastName, nationality, pageable);
    }
}
