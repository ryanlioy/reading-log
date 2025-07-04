package dev.ryanlioy.bookloger.dto;

import lombok.Data;

import java.util.List;

@Data
public class ModifyCollectionDto {
    private Long collectionId;
    private List<Long> bookIds;
}
