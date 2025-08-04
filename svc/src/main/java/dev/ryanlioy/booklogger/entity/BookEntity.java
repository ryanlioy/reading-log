package dev.ryanlioy.booklogger.entity;

import dev.ryanlioy.booklogger.constants.Genre;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "book")
public class BookEntity {
    public BookEntity() {}
    public BookEntity(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String author;
    private String publisher;
    private LocalDate publishDate;

    @ElementCollection(targetClass = Genre.class)
    @JoinTable(name = "genres", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "genre")
    @Enumerated(EnumType.STRING)
    private List<Genre> genres;
}
