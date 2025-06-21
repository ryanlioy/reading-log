package dev.ryanlioy.bookloger.service;

import dev.ryanlioy.bookloger.entity.UserEntity;
import dev.ryanlioy.bookloger.mapper.UserMapper;
import dev.ryanlioy.bookloger.repository.UserRepository;
import dev.ryanlioy.bookloger.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public Optional<UserEntity> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public UserDto addUser(UserDto userDto) {
        UserEntity entity = userMapper.resourceToEntity(userDto);
        return userMapper.entityToResource(userRepository.save(entity));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
