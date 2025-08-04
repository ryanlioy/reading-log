package dev.ryanlioy.booklogger.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

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
    @CreationTimestamp
    LocalDateTime creationDate;
}