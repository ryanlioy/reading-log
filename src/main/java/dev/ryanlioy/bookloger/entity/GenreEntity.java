package dev.ryanlioy.bookloger.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class GenreEntity {
    public GenreEntity() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
}
