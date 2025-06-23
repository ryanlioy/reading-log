package dev.ryanlioy.bookloger.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "book")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    Long seriesId;
    String title;
    String author;
    String publisher;
    LocalDate publishDate;
    List<String> genres;
}
