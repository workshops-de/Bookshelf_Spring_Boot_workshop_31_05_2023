package de.workshops.bookshelf.service;

import de.workshops.bookshelf.domain.Book;
import de.workshops.bookshelf.domain.BookException;
import de.workshops.bookshelf.persistence.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    public Book getBookByIsbn(String isbn) throws BookException {
        return this.repository.findByIsbn(isbn)
                .orElseThrow(() -> new BookException("Unknown ISBN " + isbn));
    }

    public Book searchBookByAuthor(String author) throws BookException {
        return this.repository.findByAuthorContains(author)
                .orElseThrow(() -> new BookException("Unknown author " + author));
    }

    public List<Book> searchBooks(String isbn, String author) {
        return this.repository.findByIsbnEqualsOrAuthorContains(isbn, author);
    }

    public void createBook(Book book) {
        repository.save(book);
    }
}
