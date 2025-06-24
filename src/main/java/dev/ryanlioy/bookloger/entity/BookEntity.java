package dev.ryanlioy.bookloger.entity;

import dev.ryanlioy.bookloger.constants.Genre;
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

    @ElementCollection(targetClass = Genre.class)
    @JoinTable(name = "genres", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "genre")
    @Enumerated(EnumType.STRING)
    List<Genre> genres;
}
