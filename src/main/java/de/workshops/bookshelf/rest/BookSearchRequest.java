package de.workshops.bookshelf.rest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookSearchRequest {

    private String isbn;
    private String author;
}