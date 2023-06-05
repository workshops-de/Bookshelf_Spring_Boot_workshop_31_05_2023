package de.workshops.bookshelf.rest;

import de.workshops.bookshelf.domain.Book;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class BookRestControllerAssuredTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void testWithRestAssured() {
        RestAssured.given().
                    log().all().
                when().
                    get("/book").
                then().
                    log().all().
                    statusCode(200).
                    body("author[0]", equalTo("Erich Gamma"));
    }

    @Test
    void TestRestTemplate() {
        TestRestTemplate rest = new TestRestTemplate();
        Book[] books = rest.getForObject("http://localhost:" + port + "/book", Book[].class);
        assertThat(books).hasSize(3);
    }
}