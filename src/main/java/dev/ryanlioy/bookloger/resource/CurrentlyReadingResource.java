package dev.ryanlioy.bookloger.resource;

import lombok.Data;

@Data
public class CurrentlyReadingResource {
    public CurrentlyReadingResource() {}

    public  CurrentlyReadingResource(Long id) {
        this.id = id;
    }

    private Long id;
    private Long userId;
    private Long bookId;
}
