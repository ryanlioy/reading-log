package dev.ryanlioy.booklogger.mapper;

import dev.ryanlioy.booklogger.dto.CollectionDto;
import dev.ryanlioy.booklogger.dto.UserDto;
import dev.ryanlioy.booklogger.entity.CollectionEntity;
import dev.ryanlioy.booklogger.entity.RoleEntity;
import dev.ryanlioy.booklogger.entity.UserEntity;
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

    public UserEntity dtoToEntity(UserDto user, RoleEntity role) {
        UserEntity entity = new UserEntity();

        entity.setId(user.getId());
        entity.setUsername(user.getUsername());

        List<CollectionEntity> collectionEntities = new ArrayList<>();
        if (user.getCollections() != null) {
            user.getCollections().values().forEach(dto -> collectionEntities.add(collectionMapper.dtoToEntity(dto)));
        }
        entity.setRole(role);
        entity.setCollections(collectionEntities);

        return entity;
    }

    public UserDto entityToDto(UserEntity entity) {
        UserDto dto = new UserDto();

        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());

        Map<String, CollectionDto> collections = new HashMap<>();
        entity.getCollections().forEach(e -> collections.put(e.getTitle(), collectionMapper.entityToDto(e)));
        dto.setCollections(collections);
        dto.setRole(entity.getRole().getName());

        return dto;
    }
}
