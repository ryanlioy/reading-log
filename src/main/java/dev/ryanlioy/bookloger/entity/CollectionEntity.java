package dev.ryanlioy.bookloger.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class CollectionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long userId;
    private String title;
    private String description;
    // default collections should not allow title and description changes
    private Boolean isDefaultCollection; // TODO better name and actually enforce this
    @OneToMany
    private List<BookEntity> books;
}
