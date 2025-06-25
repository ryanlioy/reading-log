package dev.ryanlioy.bookloger.dto;

import dev.ryanlioy.bookloger.constants.CollectionType;
import lombok.Data;

@Data
public class CollectionItemDto {
    public CollectionItemDto() {}
    public CollectionItemDto(Long id) {
        this.id = id;
    }
    private Long id;
    private Long bookId;
    private Long userId;
    private CollectionType collectionType;
}
