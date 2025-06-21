package dev.ryanlioy.bookloger.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CurrentlyReadingEntity {
    public CurrentlyReadingEntity() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long userId;
    private Long bookId;
}
