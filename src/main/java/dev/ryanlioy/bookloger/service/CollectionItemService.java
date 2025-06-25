package dev.ryanlioy.bookloger.service;

import dev.ryanlioy.bookloger.constants.CollectionType;
import dev.ryanlioy.bookloger.dto.CollectionItemDto;
import dev.ryanlioy.bookloger.entity.CollectionItemEntity;
import dev.ryanlioy.bookloger.mapper.CollectionItemMapper;
import dev.ryanlioy.bookloger.repository.CollectionItemRepositoy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static dev.ryanlioy.bookloger.constants.CollectionType.convertFromCamelCase;

@Service
public class CollectionItemService {
    private final CollectionItemRepositoy collectionItemRepositoy;
    private final CollectionItemMapper collectionItemMapper;


    public CollectionItemService(CollectionItemRepositoy collectionItemRepositoy, CollectionItemMapper collectionItemMapper) {
        this.collectionItemRepositoy = collectionItemRepositoy;
        this.collectionItemMapper = collectionItemMapper;
    }

    public CollectionItemDto findById(Long id) {
        CollectionItemEntity entity = collectionItemRepositoy.findById(id).orElse(null);
        if (entity == null) {
            return null;
        }
        return collectionItemMapper.entityToDto(entity);
    }

    public List<CollectionItemDto> findAllByUserIdAndType(Long userId, String type)  {
        CollectionType collectionType = convertFromCamelCase(type);

        List<CollectionItemEntity> entities = collectionItemRepositoy.findAllItemsByUserIdAndType(userId, collectionType.name());
        List<CollectionItemDto> dtos = new ArrayList<>();
        entities.forEach(entity -> dtos.add(collectionItemMapper.entityToDto(entity)));
        return dtos;
    }

    public CollectionItemDto create(CollectionItemDto resource) {
        return collectionItemMapper.entityToDto(collectionItemRepositoy.save(collectionItemMapper.dtoToEntity(resource)));
    }

    public List<CollectionItemDto> findAll() {
        List<CollectionItemDto> currentlyReadingDtos = new ArrayList<>();
        collectionItemRepositoy.findAll().forEach(entity ->
                currentlyReadingDtos.add(collectionItemMapper.entityToDto(entity))
        );
        return currentlyReadingDtos;
    }

    public void deleteById(Long id) {
        collectionItemRepositoy.deleteById(id);
    }
}
