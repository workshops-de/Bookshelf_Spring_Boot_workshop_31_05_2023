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
        return repository.getAllBooks();
    }

    public Book getBookByIsbn(String isbn) throws BookException {
        return this.repository.getAllBooks().stream()
                .filter(book -> hasIsbn(book, isbn))
                .findFirst()
                .orElseThrow(() -> new BookException("Unknown ISBN " + isbn));
    }

    public Book searchBookByAuthor(String author) throws BookException {
        return this.repository.getAllBooks().stream()
                .filter(book -> hasAuthor(book, author))
                .findFirst()
                .orElseThrow(() -> new BookException("Unknown author " + author));
    }

    public List<Book> searchBooks(String isbn, String author) {
        return this.repository.getAllBooks().stream()
                .filter(book -> hasIsbn(book, isbn))
                .filter(book -> hasAuthor(book, author))
                .toList();
    }

    private boolean hasIsbn(Book book, String isbn) {
        return book.getIsbn().equals(isbn);
    }

    private boolean hasAuthor(Book book, String author) {
        return book.getAuthor().contains(author);
    }

    public void createBook(Book book) {
        repository.createBook(book);
    }
}
