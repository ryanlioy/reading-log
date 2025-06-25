package dev.ryanlioy.bookloger.entity;

import dev.ryanlioy.bookloger.constants.CollectionType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "collection_item")
@Data
public class CollectionItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long bookId;
    private Long userId;
    @Enumerated(EnumType.STRING)
    @Column(name="collection_type")
    private CollectionType collectionType;
}
