package de.workshops.bookshelf.persistence;

import de.workshops.bookshelf.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @Test
    void findAll_should_find_all_books() {
        List<Book> books = repository.findAll();
        assertThat(books).hasSize(3);
    }

    @Test
    void findByAuthorContains_with_known_author() {
        Optional<Book> book = repository.findByAuthorContains("Erich");
        assertThat(book).isPresent();
    }

    @Test
    void findByAuthorContains_with_unknown_author() {
        Optional<Book> book = repository.findByAuthorContains("Unknown");
        assertThat(book).isEmpty();
    }
}