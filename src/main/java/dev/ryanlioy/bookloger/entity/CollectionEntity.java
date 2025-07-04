package dev.ryanlioy.bookloger.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class CollectionEntity {
    public CollectionEntity() {}
    public CollectionEntity(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long userId;
    private String title;
    private String description;
    // default collections should not allow title and description changes
    private Boolean isDefaultCollection; // TODO better name and actually enforce this
    @ManyToMany(targetEntity = BookEntity.class, fetch = FetchType.EAGER)
    private List<BookEntity> books;
}
