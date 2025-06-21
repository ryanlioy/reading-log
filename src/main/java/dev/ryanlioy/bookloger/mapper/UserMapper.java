package dev.ryanlioy.bookloger.mapper;

import dev.ryanlioy.bookloger.entity.UserEntity;
import dev.ryanlioy.bookloger.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserEntity resourceToEntity(UserDto user) {
        UserEntity entity = new UserEntity();

        entity.setId(user.getId());
        entity.setUsername(user.getUsername());
        entity.setFavorites(user.getFavorites());
        entity.setFinished(user.getFinished());
        entity.setCurrentlyReading(user.getCurrentlyReading());
        entity.setReadList(user.getReadList());

        return entity;
    }

    public UserDto entityToResource(UserEntity book) {
        UserDto resource = new UserDto();

        resource.setId(book.getId());
        resource.setUsername(book.getUsername());
        resource.setFavorites(book.getFavorites());
        resource.setFinished(book.getFinished());
        resource.setCurrentlyReading(book.getCurrentlyReading());
        resource.setReadList(book.getReadList());

        return resource;
    }
}
