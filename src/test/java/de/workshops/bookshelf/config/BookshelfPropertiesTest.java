package de.workshops.bookshelf.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {"bookshelf.stringValue=test", "bookshelf.intValue=1234"})
class BookshelfPropertiesTest {

    @Autowired
    private BookshelfProperties properties;

    @Test
    void should_read_properties() {
        System.out.println("properties = " + properties);
        assertThat(properties.getStringValue()).isEqualTo("test");
        assertThat(properties.getIntValue()).isEqualTo(1234);
        assertThat(properties.getIsbnLookup().getService()).isEqualTo("https://isbn-lookup.org/search");
    }
}