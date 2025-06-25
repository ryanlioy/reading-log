package dev.ryanlioy.bookloger.mapper;

import dev.ryanlioy.bookloger.dto.CollectionItemDto;
import dev.ryanlioy.bookloger.entity.CollectionItemEntity;
import org.springframework.stereotype.Component;

@Component
public class CollectionItemMapper {
    public CollectionItemDto entityToDto(CollectionItemEntity entity) {
        CollectionItemDto dto = new CollectionItemDto();
        dto.setId(entity.getId());
        dto.setBookId(entity.getBookId());
        dto.setUserId(entity.getUserId());
        dto.setCollectionType(entity.getCollectionType());
        return dto;
    }

    public CollectionItemEntity dtoToEntity(CollectionItemDto dto) {
        CollectionItemEntity entity = new CollectionItemEntity();
        entity.setId(dto.getId());
        entity.setBookId(dto.getBookId());
        entity.setUserId(dto.getUserId());
        entity.setCollectionType(dto.getCollectionType());
        return entity;
    }
}
