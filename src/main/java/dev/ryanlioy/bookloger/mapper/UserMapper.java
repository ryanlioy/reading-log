package dev.ryanlioy.bookloger.mapper;

import dev.ryanlioy.bookloger.entity.UserEntity;
import dev.ryanlioy.bookloger.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {
    private final BookMapper bookMapper;

    public UserMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }
    public UserEntity resourceToEntity(UserDto user) {
        UserEntity entity = new UserEntity();

        entity.setId(user.getId());
        entity.setUsername(user.getUsername());
        entity.setFavorites(user.getFavorites().stream().map(bookMapper::resourceToEntity).toList());
        entity.setFinished(user.getFinished().stream().map(bookMapper::resourceToEntity).toList());
        entity.setCurrentlyReading(user.getCurrentlyReading().stream().map(bookMapper::resourceToEntity).toList());
        entity.setReadList(user.getReadList().stream().map(bookMapper::resourceToEntity).toList());

        return entity;
    }

    public UserDto entityToResource(UserEntity book) {
        UserDto resource = new UserDto();

        resource.setId(book.getId());
        resource.setUsername(book.getUsername());
        resource.setFavorites(book.getFavorites().stream().map(bookMapper::entityToResource).toList());
        resource.setFinished(book.getFinished().stream().map(bookMapper::entityToResource).toList());
        resource.setCurrentlyReading(book.getCurrentlyReading().stream().map(bookMapper::entityToResource).toList());
        resource.setReadList(book.getReadList().stream().map(bookMapper::entityToResource).toList());

        return resource;
    }
}
