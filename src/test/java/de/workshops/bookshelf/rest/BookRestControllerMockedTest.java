package de.workshops.bookshelf.rest;

import de.workshops.bookshelf.domain.Book;
import de.workshops.bookshelf.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookRestControllerMockedTest {

    @MockBean
    private BookService service;

    @Autowired
    private BookRestController controller;

    @Test
    void getAllBooks() {
        Book cleanCode = new Book();
        cleanCode.setTitle("Clean Code");
        when(service.getAllBooks()).thenReturn(List.of(cleanCode));

        List<Book> books = controller.getAllBooks();

        assertThat(books).containsExactlyInAnyOrder(cleanCode);
    }
}