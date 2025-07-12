package dev.ryanlioy.bookloger.dto;

import lombok.Data;

import java.util.List;

@Data
public class AuthorDto {
    public AuthorDto() {}
    public AuthorDto(Long id) {
        this.id = id;
    }

    private Long id;
    private String name;
    private Integer age;
    private List<BookDto> books;
}
