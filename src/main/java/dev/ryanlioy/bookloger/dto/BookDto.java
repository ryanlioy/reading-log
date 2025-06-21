package dev.ryanlioy.bookloger.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class BookDto {
    @Id
    Long id;
    int seriesId;
    String title;
    String author;
    String publisher;
    Date publishDate;
    List<String> genres;
}
