package dev.ryanlioy.bookloger.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    Long id;
    String username;
    List<BookDto> currentlyReading;
    List<BookDto> finished;
    List<BookDto> favorites;
    List<BookDto> readList;
}
