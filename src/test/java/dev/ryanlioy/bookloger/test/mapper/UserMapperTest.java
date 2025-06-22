package dev.ryanlioy.bookloger.test.mapper;

import dev.ryanlioy.bookloger.entity.UserEntity;
import dev.ryanlioy.bookloger.mapper.BookMapper;
import dev.ryanlioy.bookloger.mapper.UserMapper;
import dev.ryanlioy.bookloger.dto.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest {
    private UserMapper userMapper;

    @Mock
    private BookMapper bookMapper;

    @BeforeEach
    public void setup() {
        userMapper = new UserMapper(bookMapper);
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

        Assertions.assertEquals(userDto.getCurrentlyReading(), userEntity.getCurrentlyReading().stream().map(bookMapper::entityToResource).toList());
        Assertions.assertEquals(userDto.getFinished(), userEntity.getFinished().stream().map(bookMapper::entityToResource).toList());
        Assertions.assertEquals(userDto.getUsername(), userEntity.getUsername());
        Assertions.assertEquals(userDto.getFavorites(), userEntity.getFavorites().stream().map(bookMapper::entityToResource).toList());
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
        Assertions.assertEquals(userDto.getCurrentlyReading(), userEntity.getCurrentlyReading().stream().map(bookMapper::entityToResource).toList());
        Assertions.assertEquals(userDto.getFinished(), userEntity.getFinished().stream().map(bookMapper::entityToResource).toList());
        Assertions.assertEquals(userDto.getFavorites(), userEntity.getFavorites().stream().map(bookMapper::entityToResource).toList());
        Assertions.assertEquals(userDto.getUsername(), userEntity.getUsername());
    }
}
