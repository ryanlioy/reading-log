package dev.ryanlioy.booklogger.test.mapper;

import dev.ryanlioy.booklogger.dto.AuthorDto;
import dev.ryanlioy.booklogger.dto.BookDto;
import dev.ryanlioy.booklogger.dto.CreateSeriesDto;
import dev.ryanlioy.booklogger.dto.SeriesDto;
import dev.ryanlioy.booklogger.entity.AuthorEntity;
import dev.ryanlioy.booklogger.entity.BookEntity;
import dev.ryanlioy.booklogger.entity.SeriesEntity;
import dev.ryanlioy.booklogger.mapper.AuthorMapper;
import dev.ryanlioy.booklogger.mapper.BookMapper;
import dev.ryanlioy.booklogger.mapper.SeriesMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SeriesMapperTest {
    @Mock
    private AuthorMapper authorMapper;
    @Mock
    private BookMapper bookMapper;
    private SeriesMapper seriesMapper;

    @BeforeEach
    public void setUp() {
        seriesMapper = new SeriesMapper(authorMapper, bookMapper);
    }

    @Test
    public void createDtoToEntity() {
        AuthorEntity authorEntity = new AuthorEntity(1L);
        authorEntity.setName("author");
        when(authorMapper.dtoToEntity(any())).thenReturn(authorEntity);
        when(bookMapper.dtoToEntity(any())).thenReturn(new BookEntity(1L)).thenReturn(new BookEntity(2L));
        CreateSeriesDto dto = new CreateSeriesDto();
        dto.setAuthor("author");
        dto.setTitle("title");
        dto.setDescription("description");
        dto.setBooks(List.of(1L, 2L));

        List<BookDto> books = List.of(new BookDto(1L), new BookDto(2L));
        SeriesEntity entity = seriesMapper.createDtoToEntity(dto, books, new AuthorDto(1L));

        assertEquals(entity.getAuthor().getName(), dto.getAuthor());
        assertEquals(entity.getTitle(), dto.getTitle());
        assertEquals(entity.getDescription(), dto.getDescription());
        dto.getBooks().forEach(book -> assertTrue(entity.getBooks().stream().anyMatch(b -> b.getId().equals(book))));
    }

    @Test
    public void entityToDto() {
        when(authorMapper.entityToDto(any())).thenReturn(new AuthorDto(1L));
        when(bookMapper.entityToDto(any())).thenReturn(new BookDto(1L)).thenReturn(new BookDto(2L));
        SeriesEntity entity = new SeriesEntity();
        entity.setId(1L);
        entity.setAuthor(new AuthorEntity(1L));
        entity.setTitle("title");
        entity.setDescription("description");
        entity.setBooks(List.of(new BookEntity(1L), new  BookEntity(2L)));

        SeriesDto dto = seriesMapper.entityToDto(entity);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getAuthor().getId(), dto.getAuthor().getId());
        assertEquals(entity.getTitle(), dto.getTitle());
        assertEquals(entity.getDescription(), dto.getDescription());
        assertEquals(entity.getBooks().size(), dto.getBooks().size());
        dto.getBooks().forEach(book -> assertTrue(entity.getBooks().stream().anyMatch(b -> b.getId().equals(book.getId()))));
    }

    @Test
    public void dtoToEntity() {
        when(authorMapper.dtoToEntity(any())).thenReturn(new AuthorEntity(1L));
        when(bookMapper.dtoToEntity(any())).thenReturn(new BookEntity(1L)).thenReturn(new BookEntity(2L));

        SeriesDto dto = new SeriesDto();
        dto.setId(1L);
        dto.setTitle("title");
        dto.setDescription("description");
        dto.setAuthor(new AuthorDto(1L));
        dto.setBooks(List.of(new BookDto(1L), new  BookDto(2L)));

        SeriesEntity entity = seriesMapper.dtoToEntity(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getAuthor().getId(), dto.getAuthor().getId());
        assertEquals(entity.getTitle(), dto.getTitle());
        assertEquals(entity.getDescription(), dto.getDescription());
        assertEquals(entity.getBooks().size(), dto.getBooks().size());
        entity.getBooks().forEach(book -> assertTrue(entity.getBooks().stream().anyMatch(b -> b.getId().equals(book.getId()))));
    }
}
