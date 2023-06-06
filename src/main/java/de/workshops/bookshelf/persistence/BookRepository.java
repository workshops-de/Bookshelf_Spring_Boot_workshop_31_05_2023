package de.workshops.bookshelf.persistence;

import de.workshops.bookshelf.domain.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    @Override
    List<Book> findAll();

    Optional<Book> findByIsbn(String isbn);

    Optional<Book> findByAuthorContains(String author);

    List<Book> findByIsbnEqualsOrAuthorContains(String isbn, String author);

}
