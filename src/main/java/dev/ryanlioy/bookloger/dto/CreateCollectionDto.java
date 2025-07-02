package dev.ryanlioy.bookloger.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreateCollectionDto {
    public CreateCollectionDto() {
        isDefaultCollection = false; // assume collection is not a default collection
    }
    public CreateCollectionDto(Long id) {
        this.id = id;
    }
    public CreateCollectionDto(Long userId, String title, Boolean isDefaultCollection) {
        this.userId = userId;
        this.title = title;
        this.isDefaultCollection = isDefaultCollection;
    }
    private Long id;
    private Long userId;
    private String title;
    private String description;
    private Boolean isDefaultCollection;
    private List<Long> bookIds = new ArrayList<>();
}
