package de.workshops.bookshelf.config;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties("bookshelf")
@ToString
public class BookshelfProperties {

    @Getter
    @Setter
    private String stringValue;

    @Min(1024)
    @Max(65535)
    @Getter
    @Setter
    private Integer intValue;

    @Getter
    @Setter
    private IsbnLookupProperties isbnLookup;
}

