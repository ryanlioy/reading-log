package dev.ryanlioy.bookloger.service;

import dev.ryanlioy.bookloger.dto.CollectionDto;
import dev.ryanlioy.bookloger.dto.CreateCollectionDto;
import dev.ryanlioy.bookloger.entity.UserEntity;
import dev.ryanlioy.bookloger.mapper.UserMapper;
import dev.ryanlioy.bookloger.repository.UserRepository;
import dev.ryanlioy.bookloger.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CollectionService collectionService;

    public UserService(UserRepository userRepository, UserMapper userMapper, CollectionService collectionService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.collectionService = collectionService;
    }

    public UserDto getUserById(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        UserDto userDto = null;
        if (userEntity.isPresent()) {
            userDto = userMapper.entityToDto(userEntity.get());
            Map<String, CollectionDto> collections = new HashMap<>();
            collectionService.findAllByUserId(id).forEach(c -> collections.put(c.getTitle(), c));
            userDto.setCollections(collections);
        }

        return userDto;
    }

    public UserDto addUser(UserDto userDto) {
        UserEntity entity = userMapper.dtoToEntity(userDto);

        UserEntity savedEntity = userRepository.save(entity);
        // create empty collections for favorites, currently reading, finished, and read list
        collectionService.save(new CreateCollectionDto(savedEntity.getId(), "Favorites", true));
        collectionService.save(new CreateCollectionDto(savedEntity.getId(), "Currently Reading", true));
        collectionService.save(new CreateCollectionDto(savedEntity.getId(), "Finished", true));
        collectionService.save(new CreateCollectionDto(savedEntity.getId(), "Read List", true));

        return getUserById(savedEntity.getId());
    }

    public void deleteUser(Long id) {
        collectionService.deleteAllById(getUserById(id).getCollections().values().stream().toList());
        userRepository.deleteById(id);
    }
}
