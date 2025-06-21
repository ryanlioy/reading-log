package dev.ryanlioy.bookloger.test.service;

import dev.ryanlioy.bookloger.entity.UserEntity;
import dev.ryanlioy.bookloger.mapper.BookMapper;
import dev.ryanlioy.bookloger.mapper.UserMapper;
import dev.ryanlioy.bookloger.repository.UserRepository;
import dev.ryanlioy.bookloger.dto.UserDto;
import dev.ryanlioy.bookloger.service.BookService;
import dev.ryanlioy.bookloger.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private BookService bookService;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        userService = new UserService(userRepository, userMapper, bookService);
    }

    @Test
    public void getUser_whenUserIsFound_returnUser() {
        UserEntity userEntity = new UserEntity();
        when(userRepository.findById(any())).thenReturn(Optional.of(userEntity));

        UserDto user = userService.getUserById(1L);
        Assertions.assertEquals(1L, user.getId());
    }

    @Test
    public void getUser_whenNoUserIsFound_returnNull() {
        UserEntity userEntity = new UserEntity();
        when(userRepository.findById(any())).thenReturn(Optional.of(userEntity));

        UserDto user = userService.getUserById(1L);
        Assertions.assertNull(user);
    }

    @Test
    public void addUser() {
        UserEntity userEntity = new UserEntity();
        when(userRepository.save(any())).thenReturn(userEntity);
        UserDto userDto = new UserDto();
        when(userMapper.entityToResource(any())).thenReturn(userDto);

        Assertions.assertEquals(userService.addUser(userDto), userDto);
    }

    @Test
    public void deleteUserById_deleteUser() {
        userRepository.deleteById(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }
}
