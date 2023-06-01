package de.workshops.bookshelf.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.workshops.bookshelf.domain.Book;
import de.workshops.bookshelf.domain.BookException;
import jakarta.annotation.PostConstruct;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/book")
@Validated
public class BookRestController {

    private final ObjectMapper mapper;

    private final ResourceLoader resourceLoader;

    private List<Book> books;

    public BookRestController(ObjectMapper mapper, ResourceLoader resourceLoader) {
        this.mapper = mapper;
        this.resourceLoader = resourceLoader;
    }

    @PostConstruct
    public void init() throws Exception {
        final var resource = resourceLoader.getResource("classpath:books.json");
        this.books = mapper.readValue(resource.getInputStream(), new TypeReference<>() {
        });
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return books;
    }

    @GetMapping("/{isbn}")
    public Book getSingleBook(@PathVariable String isbn) {
        return this.books.stream()
                .filter(book -> hasIsbn(book, isbn))
                .findFirst()
                .orElseThrow(() -> new BookException("Unknown ISBN " + isbn));
    }

    @GetMapping(params = "author")
    public Book searchBookByAuthor(@RequestParam @NotBlank @Size(min = 3) String author) {
        return this.books.stream()
                .filter(book -> hasAuthor(book, author))
                .findFirst()
                .orElseThrow(() -> new BookException("Unknown author " + author));
    }

    @PostMapping("/search")
    public List<Book> searchBooks(@RequestBody BookSearchRequest request) {
        return this.books.stream()
                .filter(book -> hasIsbn(book, request.getIsbn()))
                .filter(book -> hasAuthor(book, request.getAuthor()))
                .toList();
    }

    private boolean hasIsbn(Book book, String isbn) {
        return book.getIsbn().equals(isbn);
    }

    private boolean hasAuthor(Book book, String author) {
        return book.getAuthor().contains(author);
    }

    @ExceptionHandler(BookException.class)
    public ResponseEntity<String> handle(BookException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handle(ConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
