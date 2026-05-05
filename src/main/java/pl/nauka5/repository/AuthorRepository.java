package pl.nauka5.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.nauka5.model.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    // tu w domyśle jest szukanie po ID.

    // By first name
    Page<Author> findAllByFirstNameEqualsIgnoreCase(String firstName, Pageable pageable);

    // By last name
    Page<Author> findAllByLastNameEqualsIgnoreCase(String lastName, Pageable pageable);

    // By first and last name
    Page<Author> findAllByFirstNameEqualsIgnoreCaseAndLastNameEqualsIgnoreCase(String firstName, String lastName, Pageable pageable);

    // By nationalitya
    Page<Author> findAllByNationalityEqualsIgnoreCase(String nationality, Pageable pageable);

    //By last name and nationality
    Page<Author> findAllByLastNameEqualsIgnoreCaseAndNationalityEqualsIgnoreCase(String lastName, String nationality, Pageable pageable);
}
