package dev.ryanlioy.bookloger.test.service;

import dev.ryanlioy.bookloger.constants.Errors;
import dev.ryanlioy.bookloger.constants.Role;
import dev.ryanlioy.bookloger.dto.CollectionDto;
import dev.ryanlioy.bookloger.dto.UserDto;
import dev.ryanlioy.bookloger.dto.meta.ErrorDto;
import dev.ryanlioy.bookloger.entity.RoleEntity;
import dev.ryanlioy.bookloger.entity.UserEntity;
import dev.ryanlioy.bookloger.mapper.UserMapper;
import dev.ryanlioy.bookloger.repository.UserRepository;
import dev.ryanlioy.bookloger.service.CollectionService;
import dev.ryanlioy.bookloger.service.RoleService;
import dev.ryanlioy.bookloger.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

    @Mock
    private RoleService roleService;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        userService = new UserService(userRepository, userMapper, collectionService, roleService);
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
        userDto.setRole(Role.USER.name());
        when(userMapper.entityToDto(any())).thenReturn(userDto);
        when(userMapper.dtoToEntity(any(), any())).thenReturn(new UserEntity(1L));
        when(userRepository.findById(any())).thenReturn(Optional.of(userEntity));
        when(roleService.getRoleByName(any())).thenReturn(new RoleEntity(1L, "ADMIN"));

        assertEquals(userService.addUser(userDto, new ArrayList<>()), userDto);
    }

    @Test
    public void addUser_invalidRole() {
        when(roleService.getRoleByName(any())).thenReturn(null);
        List<ErrorDto> errors = new ArrayList<>();
        UserDto dto = userService.addUser(new UserDto(), errors);

        assertNull(dto);
        assertEquals(1, errors.size());
        assertEquals(Errors.ROLE_DOES_NOT_EXIST, errors.getFirst().getMessage());
    }

    @Test
    public void addUser_invalidRole_throwError() {
        when(roleService.getRoleByName(any())).thenReturn(null);

        UserDto userDto = new UserDto();
        userDto.setRole(Role.ADMIN.name());
        assertThrows(RuntimeException.class, () -> userService.addUser(userDto, new ArrayList<>()));
    }

    @Test
    public void deleteUser_deleteUser() {
        userRepository.deleteById(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void deleteUser_deleteAllCollections() {
        when(userRepository.findById(any())).thenReturn(Optional.of(new UserEntity()));
        when(userMapper.entityToDto(any())).thenReturn(new UserDto());
        when(collectionService.findAllByUserId(any())).thenReturn(List.of(new CollectionDto(1L)));

        userService.deleteUser(1L);
        verify(collectionService, times(1)).deleteAllById(any());
    }

    @Test
    public void doesUserExist_userExists_returnTrue() {
        when(userRepository.existsById(any())).thenReturn(true);
        boolean userExists = userService.doesUserExist(1L);
        assertTrue(userExists);
    }

    @Test
    public void doesUserExist_userExists_returnFalse() {
        when(userRepository.existsById(any())).thenReturn(false);
        boolean userExists = userService.doesUserExist(1L);
        assertFalse(userExists);
    }
}
