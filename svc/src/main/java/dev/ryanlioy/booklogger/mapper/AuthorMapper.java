package dev.ryanlioy.booklogger.mapper;

import dev.ryanlioy.booklogger.dto.AuthorDto;
import dev.ryanlioy.booklogger.dto.BookDto;
import dev.ryanlioy.booklogger.dto.CreateAuthorDto;
import dev.ryanlioy.booklogger.entity.AuthorEntity;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public AuthorEntity createDtoToEntity(CreateAuthorDto createAuthorDto, List<BookDto> books) {
        AuthorEntity entity = new AuthorEntity();
        entity.setId(createAuthorDto.getId());
        entity.setAge(createAuthorDto.getAge());
        entity.setName(createAuthorDto.getName());
        entity.setBooks(books.stream().map(bookMapper::dtoToEntity).toList());

        return entity;
    }
}
