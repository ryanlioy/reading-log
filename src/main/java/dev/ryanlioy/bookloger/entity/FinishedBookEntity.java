package dev.ryanlioy.bookloger.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "finished_book")
public class FinishedBookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long bookId;
    private Long userId;
}
