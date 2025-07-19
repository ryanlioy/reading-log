package dev.ryanlioy.bookloger.test.mapper;

import dev.ryanlioy.bookloger.constants.Role;
import dev.ryanlioy.bookloger.dto.CollectionDto;
import dev.ryanlioy.bookloger.entity.CollectionEntity;
import dev.ryanlioy.bookloger.entity.RoleEntity;
import dev.ryanlioy.bookloger.entity.UserEntity;
import dev.ryanlioy.bookloger.mapper.CollectionMapper;
import dev.ryanlioy.bookloger.mapper.UserMapper;
import dev.ryanlioy.bookloger.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest {
    private UserMapper userMapper;

    @Mock
    private CollectionMapper collectionMapper;

    @BeforeEach
    public void setup() {
        userMapper = new UserMapper(collectionMapper);
    }

    @Test
    public void entityToDto() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUsername("username");
        userEntity.setCollections(List.of(new CollectionEntity()));
        userEntity.setRole(new RoleEntity(1L, "ADMIN"));

        UserDto userDto = userMapper.entityToDto(userEntity);

        assertEquals(userDto.getId(), userEntity.getId());
        assertEquals(userDto.getUsername(), userEntity.getUsername());
        assertEquals(userDto.getCollections().size(), userEntity.getCollections().size());
        assertEquals(userDto.getRole().name(), userEntity.getRole().getName());
    }

    @Test
    public void dtoToEntity() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setUsername("username");
        userDto.setCollections(Map.of("title", new CollectionDto()));
        userDto.setRole(Role.ADMIN);

        UserEntity userEntity = userMapper.dtoToEntity(userDto, new RoleEntity(1L, "ADMIN"));
        assertEquals(userDto.getId(), userEntity.getId());
        assertEquals(userDto.getUsername(), userEntity.getUsername());
        assertEquals(userDto.getCollections().size(), userEntity.getCollections().size());
        assertEquals(userDto.getRole().name(), userEntity.getRole().getName());
    }
}
