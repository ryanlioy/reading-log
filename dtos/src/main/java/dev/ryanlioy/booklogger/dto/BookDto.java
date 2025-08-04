package dev.ryanlioy.booklogger.dto;

import dev.ryanlioy.booklogger.constants.Genre;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BookDto {
    public BookDto() {}
    public BookDto(Long id) {
        this.id = id;
    }
    Long id;
    String title;
    String author;
    String publisher;
    LocalDate publishDate;
    List<Genre> genres;
}
