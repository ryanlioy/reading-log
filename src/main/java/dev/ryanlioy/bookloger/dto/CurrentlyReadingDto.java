package dev.ryanlioy.bookloger.dto;

import lombok.Data;

@Data
public class CurrentlyReadingDto {
    public CurrentlyReadingDto() {}

    public CurrentlyReadingDto(Long id) {
        this.id = id;
    }

    private Long id;
    private Long userId;
    private Long bookId;
}
