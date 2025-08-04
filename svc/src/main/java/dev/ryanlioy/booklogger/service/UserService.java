package dev.ryanlioy.booklogger.service;

import dev.ryanlioy.booklogger.constants.Errors;
import dev.ryanlioy.booklogger.dto.CollectionDto;
import dev.ryanlioy.booklogger.dto.CreateCollectionDto;
import dev.ryanlioy.booklogger.dto.UserDto;
import dev.ryanlioy.booklogger.meta.ErrorDto;
import dev.ryanlioy.booklogger.entity.RoleEntity;
import dev.ryanlioy.booklogger.entity.UserEntity;
import dev.ryanlioy.booklogger.mapper.UserMapper;
import dev.ryanlioy.booklogger.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
    private final RoleService roleService;

    public UserService(UserRepository userRepository, UserMapper userMapper, CollectionService collectionService, RoleService roleService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.collectionService = collectionService;
        this.roleService = roleService;
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
    public UserDto addUser(UserDto userDto, List<ErrorDto> errors) {
        RoleEntity role = roleService.getRoleByName(userDto.getRole());
        if (role == null) {
            LOG.error("{}addUser() role with name={} found", CLASS_LOG, userDto.getRole());
            errors.add(new ErrorDto(Errors.ROLE_DOES_NOT_EXIST));
            return null;
        }
        UserEntity entity = userMapper.dtoToEntity(userDto, role);

        UserEntity savedEntity = userRepository.save(entity);
        // create empty collections for favorites, currently reading, finished, and read list
        List<ErrorDto> saveCollectionErrors = new ArrayList<>();
        collectionService.saveAll(List.of(
                new CreateCollectionDto(savedEntity.getId(), "Favorites", true),
                new CreateCollectionDto(savedEntity.getId(), "Currently Reading", true),
                new CreateCollectionDto(savedEntity.getId(), "Finished", true),
                new CreateCollectionDto(savedEntity.getId(), "Read List", true)
        ), saveCollectionErrors);
        if (!saveCollectionErrors.isEmpty()) {
            errors.addAll(saveCollectionErrors);
            return null;
        }
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
         return id != null && userRepository.existsById(id);
    }
}
