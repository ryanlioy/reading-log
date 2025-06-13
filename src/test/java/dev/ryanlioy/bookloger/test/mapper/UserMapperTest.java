package dev.ryanlioy.bookloger.test.mapper;

import dev.ryanlioy.bookloger.entity.UserEntity;
import dev.ryanlioy.bookloger.mapper.UserMapper;
import dev.ryanlioy.bookloger.resource.UserResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class UserMapperTest {
    private UserMapper userMapper;

    @BeforeEach
    public void setup() {
        userMapper = new UserMapper();
    }

    @Test
    public void entityToResource() {
        UserEntity userEntity = new UserEntity();
        userEntity.setCurrentlyReading(new ArrayList<>());
        userEntity.setId(1L);
        userEntity.setUsername("username");
        userEntity.setFinished(new ArrayList<>());
        userEntity.setFavorites(new ArrayList<>());
        userEntity.setUsername("username");

        UserResource userResource = userMapper.entityToResource(userEntity);

        Assertions.assertEquals(userResource.getId(), userEntity.getId());

        Assertions.assertEquals(userResource.getCurrentlyReading(), userEntity.getCurrentlyReading());
        Assertions.assertEquals(userResource.getFinished(), userEntity.getFinished());
        Assertions.assertEquals(userResource.getUsername(), userEntity.getUsername());
        Assertions.assertEquals(userResource.getFavorites(), userEntity.getFavorites());
        Assertions.assertEquals(userResource.getUsername(), userEntity.getUsername());
        Assertions.assertEquals(userResource.getId(), userEntity.getId());
    }

    @Test
    public void resourceToEntity() {
        UserResource userResource = new UserResource();
        userResource.setCurrentlyReading(new ArrayList<>());
        userResource.setId(1L);
        userResource.setUsername("username");
        userResource.setFinished(new ArrayList<>());
        userResource.setFavorites(new ArrayList<>());

        UserEntity userEntity = userMapper.resourceToEntity(userResource);
        Assertions.assertEquals(userResource.getId(), userEntity.getId());
        Assertions.assertEquals(userResource.getCurrentlyReading(), userEntity.getCurrentlyReading());
        Assertions.assertEquals(userResource.getFinished(), userEntity.getFinished());
        Assertions.assertEquals(userResource.getFavorites(), userEntity.getFavorites());
        Assertions.assertEquals(userResource.getUsername(), userEntity.getUsername());
    }
}
