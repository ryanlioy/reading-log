package dev.ryanlioy.bookloger.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    public UserDto() {}
    public UserDto(Long id) {
        this.id = id;
    }
    Long id;
    String username;
    List<BookDto> currentlyReading;
    List<BookDto> finished;
    List<BookDto> favorites;
    List<BookDto> readList;
}
