package dev.ryanlioy.bookloger.dto;

import lombok.Data;

import java.util.Map;

@Data
public class UserDto {
    public UserDto() {}
    public UserDto(Long id) {
        this.id = id;
    }
    private Long id;
    private String username;
    private String role;
    private Map<String, CollectionDto> collections; // TODO not sure if a map or list would be best here
}
