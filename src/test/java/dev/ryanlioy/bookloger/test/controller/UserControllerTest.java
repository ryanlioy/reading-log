package dev.ryanlioy.bookloger.test.controller;

import dev.ryanlioy.bookloger.controller.UserController;
import dev.ryanlioy.bookloger.dto.UserDto;
import dev.ryanlioy.bookloger.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Mock
    private UserService userService;

    private UserController userController;

    @BeforeEach
    public void setUp() {
        userController = new UserController(userService);
    }

    @Test
    public void addUser() {
        UserDto userDto = new UserDto();
        when(userService.addUser(any())).thenReturn(userDto);
        ResponseEntity<UserDto> responseEntity = userController.addUser(userDto);
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assertions.assertEquals(userDto, responseEntity.getBody());
    }

    @Test
    public void getUserById_userFound() {
        when(userService.getUserById(any())).thenReturn(new UserDto());
        ResponseEntity<UserDto> response = userController.getUser(1L);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getUserById_userNotFound() {
        when(userService.getUserById(any())).thenReturn(null);
        ResponseEntity<UserDto> response = userController.getUser(1L);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void deleteUser() {
        userController.deleteUser(1L);
        verify(userService, times(1)).deleteUser(1L);
    }
}
