package dev.ryanlioy.bookloger.dto;

import lombok.Data;

@Data
public class FinishedBookDto {
    private Long id;
    private Long bookId;
    private Long userId;
}
