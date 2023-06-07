package de.workshops.bookshelf.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.workshops.bookshelf.domain.Book;
import de.workshops.bookshelf.persistence.BookRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class BookRestControllerTest {

    @Autowired
    private BookRestController bookRestController;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllBooks_should_return_all_books() {
        List<Book> books = bookRestController.getAllBooks();
        assertThat(books).hasSize(3);
    }

    @Test
    void request_to_getAllBooks_should_return_all_books() throws Exception {
        mvc.perform(get("/book"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[1].title", is("Clean Code")))
        ;
    }

    @Test
    void request_to_getAllBooks_should_return_still_all_books() throws Exception {
        MvcResult result = mvc.perform(get("/book")).andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        List<Book> books = objectMapper.readValue(jsonResponse, new TypeReference<>() {
        });
        assertThat(books).hasSize(3);
        assertThat(books.get(1).getTitle()).isEqualTo("Clean Code");
        assertThat(books).extracting(Book::getIsbn).doesNotHaveDuplicates();
    }



    @SpyBean
    private BookRepository bookRepository;

    @Test
    void createBook_should_store_new_book() throws Exception {
        Book book = new Book();
        book.setIsbn("1234567890");
        book.setTitle("Spring Boot 3");
        book.setDescription("Spring Boot 3 explained with all details");
        book.setAuthor("Someone");

        String jsonRequest = objectMapper.writeValueAsString(book);
        mvc.perform(post("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isCreated());

        ArgumentCaptor<Book> newBookCaptor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository).save(newBookCaptor.capture());

        bookRepository.delete(newBookCaptor.getValue());
    }

    @TestConfiguration
    static class JsonTestConfiguration {

        @Bean
        ObjectMapper objectMapper() {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            return objectMapper;
        }
    }
}