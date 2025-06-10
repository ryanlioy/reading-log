package dev.ryanlioy.bookloger.resource;

import dev.ryanlioy.bookloger.entity.BookEntity;
import lombok.Data;

import java.util.List;

@Data
public class UserResource {
    Long id;
    String username;
    List<BookEntity> currentlyReading;
    List<BookEntity> finished;
    List<BookEntity> favorites;
    List<BookEntity> readList;
}
