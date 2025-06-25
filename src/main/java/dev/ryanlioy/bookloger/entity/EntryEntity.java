package dev.ryanlioy.bookloger.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "entry")
public class EntryEntity {

    public EntryEntity() {}

    public EntryEntity(Long id) {
        this.id = id;
    }

    public EntryEntity(Long id, Long userId, Long bookId) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    Long userId;
    Long bookId;
    String description;
}