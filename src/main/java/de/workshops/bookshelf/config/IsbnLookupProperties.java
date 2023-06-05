package de.workshops.bookshelf.config;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
class IsbnLookupProperties {

    @Pattern(regexp = "http.+")
    @Getter
    @Setter
    private String service;
}
