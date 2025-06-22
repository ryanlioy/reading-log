package dev.ryanlioy.bookloger.mapper;

import dev.ryanlioy.bookloger.entity.UserEntity;
import dev.ryanlioy.bookloger.dto.UserDto;
import org.springframework.stereotype.Component;

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
        entity.setFavorites(user.getFavorites() != null ? user.getFavorites().stream().map(bookMapper::resourceToEntity).toList(): null);
        entity.setFinished(user.getFinished()  != null ? user.getFinished().stream().map(bookMapper::resourceToEntity).toList(): null);
        entity.setCurrentlyReading(user.getCurrentlyReading() != null ? user.getCurrentlyReading().stream().map(bookMapper::resourceToEntity).toList(): null);
        entity.setReadList(user.getReadList() != null ? user.getReadList().stream().map(bookMapper::resourceToEntity).toList(): null);;

        return entity;
    }

    public UserDto entityToResource(UserEntity book) {
        UserDto resource = new UserDto();

        resource.setId(book.getId());
        resource.setUsername(book.getUsername());
        resource.setFavorites(book.getFavorites() != null ? book.getFavorites().stream().map(bookMapper::entityToResource).toList() : null);
        resource.setFinished(book.getFinished() != null ? book.getFinished().stream().map(bookMapper::entityToResource).toList() : null);
        resource.setCurrentlyReading(book.getCurrentlyReading() != null ? book.getCurrentlyReading().stream().map(bookMapper::entityToResource).toList() : null);
        resource.setReadList(book.getReadList() != null ? book.getReadList().stream().map(bookMapper::entityToResource).toList() : null);

        return resource;
    }
}
