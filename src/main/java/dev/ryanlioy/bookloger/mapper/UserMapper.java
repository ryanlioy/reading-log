package dev.ryanlioy.bookloger.mapper;

import dev.ryanlioy.bookloger.dto.CollectionDto;
import dev.ryanlioy.bookloger.entity.CollectionEntity;
import dev.ryanlioy.bookloger.entity.UserEntity;
import dev.ryanlioy.bookloger.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserMapper {
    private final CollectionMapper collectionMapper;

    public UserMapper(CollectionMapper collectionMapper) {
        this.collectionMapper = collectionMapper;
    }
    public UserEntity dtoToEntity(UserDto user) {
        UserEntity entity = new UserEntity();

        entity.setId(user.getId());
        entity.setUsername(user.getUsername());

        List<CollectionEntity> collectionEntities = new ArrayList<>();
        if (user.getCollections() != null) {
            user.getCollections().values().forEach(dto -> collectionEntities.add(collectionMapper.dtoToEntity(dto)));
        }
        entity.setCollections(collectionEntities);

        return entity;
    }

    public UserDto entityToDto(UserEntity entity) {
        UserDto resource = new UserDto();

        resource.setId(entity.getId());
        resource.setUsername(entity.getUsername());

        Map<String, CollectionDto> collections = new HashMap<>();
        entity.getCollections().forEach(dto -> collections.put(dto.getTitle(), collectionMapper.entityToDto(dto)));
        resource.setCollections(collections);

        return resource;
    }
}
