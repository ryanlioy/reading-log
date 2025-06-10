package dev.ryanlioy.bookloger.service;

import dev.ryanlioy.bookloger.entity.UserEntity;
import dev.ryanlioy.bookloger.mapper.UserMapper;
import dev.ryanlioy.bookloger.repository.UserRepository;
import dev.ryanlioy.bookloger.resource.UserResource;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserService {
    public UserRepository userRepository;
    public UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public Optional<UserEntity> getUser(Long id) {
        return userRepository.findById(id);
    }

    public UserResource addUser(UserResource userResource) {
        UserEntity entity = userMapper.resourceToEntity(userResource);
        return userMapper.entityToResource(userRepository.save(entity));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
