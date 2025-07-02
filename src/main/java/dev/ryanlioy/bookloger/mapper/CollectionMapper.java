package dev.ryanlioy.bookloger.mapper;

import dev.ryanlioy.bookloger.dto.BookDto;
import dev.ryanlioy.bookloger.dto.CollectionDto;
import dev.ryanlioy.bookloger.dto.CreateCollectionDto;
import dev.ryanlioy.bookloger.entity.CollectionEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CollectionMapper {
    private final BookMapper bookMapper;
    public CollectionMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    public CollectionEntity createDtoToEntity(CreateCollectionDto createDto, List<BookDto> books) {
        CollectionEntity collectionDto = new CollectionEntity();
        collectionDto.setId(createDto.getId());
        collectionDto.setTitle(createDto.getTitle());
        collectionDto.setDescription(createDto.getDescription());
        collectionDto.setUserId(createDto.getUserId());
        collectionDto.setIsDefaultCollection(createDto.getIsDefaultCollection());
        collectionDto.setBooks(books.stream().map(bookMapper::resourceToEntity).toList());

        return collectionDto;
    }
    public CollectionDto entityToDto(CollectionEntity entity) {
        CollectionDto dto = new CollectionDto();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setIsDefaultCollection(entity.getIsDefaultCollection());
        dto.setBooks(entity.getBooks().stream().map(e -> bookMapper.entityToResource(e)).toList());
        return dto;
    }

    public CollectionEntity dtoToEntity(CollectionDto dto) {
        CollectionEntity entity = new CollectionEntity();
        entity.setId(dto.getId());
        entity.setUserId(dto.getUserId());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setIsDefaultCollection(dto.getIsDefaultCollection());
        entity.setBooks(dto.getBooks().stream().map(d -> bookMapper.resourceToEntity(d)).toList());
        return entity;
    }
}
