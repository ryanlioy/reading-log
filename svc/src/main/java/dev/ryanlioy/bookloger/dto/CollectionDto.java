package dev.ryanlioy.bookloger.dto;

import lombok.Data;

import java.util.List;

@Data
public class CollectionDto {
    public CollectionDto() {}
    public CollectionDto(Long id) {
        this.id = id;
    }
    private Long id;
    private Long userId;
    private String title;
    private String description;
    private Boolean isDefaultCollection;
    private List<BookDto> books;
}
