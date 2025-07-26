package dev.ryanlioy.bookloger.dto;

import lombok.Data;

import java.util.List;

@Data
public class SeriesDto {
    private Long id;
    private String title;
    private String description;
    private AuthorDto author;
    private List<BookDto> books;
}
