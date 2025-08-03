package dev.ryanlioy.bookloger.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EntryDto {
    public EntryDto() {}

    public EntryDto(Long id) {
        this.id = id;
    }

    public EntryDto(Long id, Long userId, Long bookId) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
    }
    Long id;
    Long userId;
    Long bookId;
    String description;
    LocalDateTime creationDate;
}
