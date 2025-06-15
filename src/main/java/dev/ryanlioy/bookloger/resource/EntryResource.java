package dev.ryanlioy.bookloger.resource;

import lombok.Data;

@Data
public class EntryResource {
    public  EntryResource() {}

    public EntryResource(Long id) {
        this.id = id;
    }

    public EntryResource(Long id, Long userId, Long bookId) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
    }
    Long id;
    Long userId;
    Long bookId;
    String description;
}
