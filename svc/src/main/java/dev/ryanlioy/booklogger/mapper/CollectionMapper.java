package dev.ryanlioy.booklogger.mapper;

import dev.ryanlioy.booklogger.dto.BookDto;
import dev.ryanlioy.booklogger.dto.CollectionDto;
import dev.ryanlioy.booklogger.dto.CreateCollectionDto;
import dev.ryanlioy.booklogger.entity.CollectionEntity;
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
        collectionDto.setBooks(books.stream().map(bookMapper::dtoToEntity).toList());

        return collectionDto;
    }
    public CollectionDto entityToDto(CollectionEntity entity) {
        CollectionDto dto = new CollectionDto();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setIsDefaultCollection(entity.getIsDefaultCollection());
        dto.setBooks(entity.getBooks().stream().map(e -> bookMapper.entityToDto(e)).toList());
        return dto;
    }

    public CollectionEntity dtoToEntity(CollectionDto dto) {
        CollectionEntity entity = new CollectionEntity();
        entity.setId(dto.getId());
        entity.setUserId(dto.getUserId());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setIsDefaultCollection(dto.getIsDefaultCollection());
        entity.setBooks(dto.getBooks().stream().map(d -> bookMapper.dtoToEntity(d)).toList());
        return entity;
    }
}
