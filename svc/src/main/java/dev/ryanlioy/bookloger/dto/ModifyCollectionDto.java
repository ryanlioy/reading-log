package dev.ryanlioy.bookloger.dto;

import lombok.Data;

import java.util.List;

@Data
public class ModifyCollectionDto {
    public ModifyCollectionDto() {}
    public ModifyCollectionDto(Long collectionId, List<Long> bookIds) {
        this.collectionId = collectionId;
        this.bookIds = bookIds;
    }
    private Long collectionId;
    private List<Long> bookIds;
}
