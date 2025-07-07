package dev.ryanlioy.bookloger.dto;

import dev.ryanlioy.bookloger.constants.Genre;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BookDto {
    public BookDto() {}
    public BookDto(Long id) {
        this.id = id;
    }
    @Id
    Long id;
    Long seriesId;
    String title;
    String author;
    String publisher;
    LocalDate publishDate;
    List<Genre> genres;
}
