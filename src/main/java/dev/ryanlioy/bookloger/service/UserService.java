package dev.ryanlioy.bookloger.service;

import dev.ryanlioy.bookloger.dto.CollectionDto;
import dev.ryanlioy.bookloger.dto.CreateCollectionDto;
import dev.ryanlioy.bookloger.entity.UserEntity;
import dev.ryanlioy.bookloger.mapper.UserMapper;
import dev.ryanlioy.bookloger.repository.UserRepository;
import dev.ryanlioy.bookloger.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
    private static final String CLASS_LOG = "UserService::";
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CollectionService collectionService;

    public UserService(UserRepository userRepository, UserMapper userMapper, CollectionService collectionService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.collectionService = collectionService;
    }

    /**
     * Find a user by ID
     * @param id the ID of the user
     * @return the user, null if not found
     */
    public UserDto getUserById(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        UserDto userDto = null;
        if (userEntity.isPresent()) {
            userDto = userMapper.entityToDto(userEntity.get());
            Map<String, CollectionDto> collections = new HashMap<>();
            collectionService.findAllByUserId(id).forEach(c -> collections.put(c.getTitle(), c));
            userDto.setCollections(collections);
        }
        LOG.info("{}getUserById() user with ID={} " + (userDto == null ? "not found" : "found"), CLASS_LOG, id);
        return userDto;
    }

    /**
     * Creates a user
     * @param userDto the user to create
     * @return the created user
     */
    public UserDto addUser(UserDto userDto) {
        UserEntity entity = userMapper.dtoToEntity(userDto);

        UserEntity savedEntity = userRepository.save(entity);
        // create empty collections for favorites, currently reading, finished, and read list
        collectionService.saveAll(List.of(
                new CreateCollectionDto(savedEntity.getId(), "Favorites", true),
                new CreateCollectionDto(savedEntity.getId(), "Currently Reading", true),
                new CreateCollectionDto(savedEntity.getId(), "Finished", true),
                new CreateCollectionDto(savedEntity.getId(), "Read List", true)
        ));
        LOG.info("{}addUser() created user with ID={}", CLASS_LOG, entity.getId());
        return getUserById(savedEntity.getId());
    }

    /**
     * Deletes a user
     * @param id the ID of the user to delete
     */
    public void deleteUser(Long id) {
        collectionService.deleteAllById(getUserById(id).getCollections().values().stream().toList());
        userRepository.deleteById(id);
        LOG.info("{}deleteUser() deleted user with ID={}", CLASS_LOG, id);
    }

    /**
     * Check if a user exists
     * @param id the user ID
     * @return true if the user exists, false otherwise
     */
    public boolean doesUserExist(Long id) {
         return userRepository.existsById(id);
    }
}
