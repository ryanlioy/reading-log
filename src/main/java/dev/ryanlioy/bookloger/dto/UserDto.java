package dev.ryanlioy.bookloger.dto;

import lombok.Data;

import java.util.Map;

@Data
public class UserDto {
    public UserDto() {}
    public UserDto(Long id) {
        this.id = id;
    }
    Long id;
    String username;
    Map<String, CollectionDto> collections; // TODO not sure if a map or list would be best here
}
