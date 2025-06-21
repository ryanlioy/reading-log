package dev.ryanlioy.bookloger.service;

import dev.ryanlioy.bookloger.entity.UserEntity;
import dev.ryanlioy.bookloger.mapper.UserMapper;
import dev.ryanlioy.bookloger.repository.UserRepository;
import dev.ryanlioy.bookloger.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BookService bookService;

    public UserService(UserRepository userRepository, UserMapper userMapper, BookService bookService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.bookService = bookService;
    }

    public UserDto getUserById(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        UserDto userDto = null;
        if (userEntity.isPresent()) {
            userDto = userEntity.map(userMapper::entityToResource).orElse(null);
            userDto.setCurrentlyReading(bookService.findAllCurrentlyReadingBooks(id));
        }

        return userDto;
    }

    public UserDto addUser(UserDto userDto) {
        UserEntity entity = userMapper.resourceToEntity(userDto);
        return userMapper.entityToResource(userRepository.save(entity));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
