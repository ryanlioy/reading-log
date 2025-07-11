package dev.ryanlioy.bookloger.mapper;

import dev.ryanlioy.bookloger.dto.AuthorDto;
import dev.ryanlioy.bookloger.entity.AuthorEntity;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {
    private final BookMapper bookMapper;

    public AuthorMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    public AuthorDto entityToDto(AuthorEntity entity) {
        AuthorDto dto = new AuthorDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAge(entity.getAge());
        dto.setBooks(entity.getBooks().stream().map(bookMapper::entityToDto).toList());

        return dto;
    }

    public AuthorEntity dtoToEntity(AuthorDto dto) {
        AuthorEntity entity = new AuthorEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setAge(dto.getAge());
        entity.setBooks(dto.getBooks().stream().map(bookMapper::dtoToEntity).toList());

        return entity;
    }
}
