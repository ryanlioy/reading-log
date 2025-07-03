package dev.ryanlioy.bookloger.test.service;

import dev.ryanlioy.bookloger.entity.UserEntity;
import dev.ryanlioy.bookloger.mapper.UserMapper;
import dev.ryanlioy.bookloger.repository.UserRepository;
import dev.ryanlioy.bookloger.dto.UserDto;
import dev.ryanlioy.bookloger.service.CollectionService;
import dev.ryanlioy.bookloger.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private CollectionService collectionService;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        userService = new UserService(userRepository, userMapper, collectionService);
    }

    @Test
    public void getUser_whenUserIsFound_returnUser() {
        UserEntity userEntity = new UserEntity();
        when(userRepository.findById(any())).thenReturn(Optional.of(userEntity));
        when(userMapper.entityToDto(any())).thenReturn(new  UserDto(1L));

        UserDto user = userService.getUserById(1L);
        assertEquals(1L, user.getId());
    }

    @Test
    public void getUser_whenNoUserIsFound_returnNull() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        UserDto user = userService.getUserById(1L);
        assertNull(user);
    }

    @Test
    public void addUser() {
        UserEntity userEntity = new UserEntity();
        when(userRepository.save(any())).thenReturn(userEntity);
        UserDto userDto = new UserDto();
        when(userMapper.entityToDto(any())).thenReturn(userDto);
        when(userRepository.findById(any())).thenReturn(Optional.of(userEntity));

        assertEquals(userService.addUser(userDto), userDto);
    }

    @Test
    public void deleteUserById_deleteUser() {
        userRepository.deleteById(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }
}
