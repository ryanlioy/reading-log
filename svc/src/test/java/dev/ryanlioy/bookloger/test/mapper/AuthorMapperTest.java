package dev.ryanlioy.bookloger.test.mapper;

import dev.ryanlioy.bookloger.dto.AuthorDto;
import dev.ryanlioy.bookloger.dto.BookDto;
import dev.ryanlioy.bookloger.dto.CreateAuthorDto;
import dev.ryanlioy.bookloger.entity.AuthorEntity;
import dev.ryanlioy.bookloger.entity.BookEntity;
import dev.ryanlioy.bookloger.mapper.AuthorMapper;
import dev.ryanlioy.bookloger.mapper.BookMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthorMapperTest {
    private AuthorMapper authorMapper;

    @Mock
    private BookMapper bookMapper;

    @BeforeEach
    public void setup() {
        authorMapper = new AuthorMapper(bookMapper);
    }

    @Test
    public void dtoToEntity() {
        BookEntity book = new BookEntity();
        book.setId(1L);
        when(bookMapper.dtoToEntity(any())).thenReturn(book);

        AuthorDto dto = new AuthorDto();
        dto.setId(1L);
        dto.setName("name");
        dto.setAge(1);
        dto.setBooks(List.of(new BookDto(1L)));

        AuthorEntity entity = authorMapper.dtoToEntity(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getAge(), dto.getAge());
        assertEquals(entity.getName(), dto.getName());
        assertNotNull(entity.getBooks());
    }

    @Test
    public void entityToDto() {
        BookDto book = new BookDto();
        book.setId(1L);
        when(bookMapper.entityToDto(any())).thenReturn(book);

        AuthorEntity entity = new AuthorEntity();
        entity.setId(1L);
        entity.setName("name");
        entity.setAge(1);
        entity.setBooks(List.of(new BookEntity()));

        AuthorDto dto = authorMapper.entityToDto(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getAge(), entity.getAge());
        assertEquals(dto.getName(), entity.getName());
        assertNotNull(dto.getBooks());
    }

    @Test
    public void createDtoToEntity() {
        when(bookMapper.dtoToEntity(any())).thenReturn(new BookEntity(1L));

        CreateAuthorDto expected = new CreateAuthorDto();
        expected.setId(1L);
        expected.setAge(1);
        expected.setName("name");
        expected.setBookIds(List.of(1L));

        BookDto book = new BookDto(1L);
        AuthorEntity actual = authorMapper.createDtoToEntity(expected, List.of(book));
        assertEquals(actual.getId(), expected.getId());
        assertEquals(actual.getAge(), expected.getAge());
        assertEquals(actual.getName(), expected.getName());
        assertEquals(actual.getBooks().getFirst().getId(), expected.getBookIds().getFirst());
    }
}
