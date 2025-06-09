package dev.ryanlioy.bookloger.resource;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class BookResource {
    @Id
    Long id;
    int seriesId;
    String title;
    String author;
    String publisher;
    Date publishDate;
    List<String> genres;
}
