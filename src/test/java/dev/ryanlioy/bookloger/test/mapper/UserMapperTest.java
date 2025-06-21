package dev.ryanlioy.bookloger.test.mapper;

import dev.ryanlioy.bookloger.entity.UserEntity;
import dev.ryanlioy.bookloger.mapper.UserMapper;
import dev.ryanlioy.bookloger.dto.UserDto;
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

        UserDto userDto = userMapper.entityToResource(userEntity);

        Assertions.assertEquals(userDto.getId(), userEntity.getId());

        Assertions.assertEquals(userDto.getCurrentlyReading(), userEntity.getCurrentlyReading());
        Assertions.assertEquals(userDto.getFinished(), userEntity.getFinished());
        Assertions.assertEquals(userDto.getUsername(), userEntity.getUsername());
        Assertions.assertEquals(userDto.getFavorites(), userEntity.getFavorites());
        Assertions.assertEquals(userDto.getUsername(), userEntity.getUsername());
        Assertions.assertEquals(userDto.getId(), userEntity.getId());
    }

    @Test
    public void resourceToEntity() {
        UserDto userDto = new UserDto();
        userDto.setCurrentlyReading(new ArrayList<>());
        userDto.setId(1L);
        userDto.setUsername("username");
        userDto.setFinished(new ArrayList<>());
        userDto.setFavorites(new ArrayList<>());

        UserEntity userEntity = userMapper.resourceToEntity(userDto);
        Assertions.assertEquals(userDto.getId(), userEntity.getId());
        Assertions.assertEquals(userDto.getCurrentlyReading(), userEntity.getCurrentlyReading());
        Assertions.assertEquals(userDto.getFinished(), userEntity.getFinished());
        Assertions.assertEquals(userDto.getFavorites(), userEntity.getFavorites());
        Assertions.assertEquals(userDto.getUsername(), userEntity.getUsername());
    }
}
