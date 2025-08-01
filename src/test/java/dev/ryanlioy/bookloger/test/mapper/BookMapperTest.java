package dev.ryanlioy.bookloger.test.mapper;

import dev.ryanlioy.bookloger.entity.BookEntity;
import dev.ryanlioy.bookloger.mapper.BookMapper;
import dev.ryanlioy.bookloger.dto.BookDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

public class BookMapperTest {
    private BookMapper bookMapper;

    @BeforeEach
    void setUp() {
        bookMapper = new BookMapper();
    }

    @Test
    public void entityToDto() {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(1L);
        bookEntity.setAuthor("author");
        bookEntity.setTitle("title");
        bookEntity.setGenres(new ArrayList<>());
        bookEntity.setPublisher("publisher");
        bookEntity.setPublishDate(LocalDate.of(1970, 1, 1));

        BookDto bookDto = bookMapper.entityToDto(bookEntity);

        Assertions.assertEquals(bookDto.getId(), bookEntity.getId());
        Assertions.assertEquals(bookDto.getAuthor(), bookEntity.getAuthor());
        Assertions.assertEquals(bookDto.getTitle(), bookEntity.getTitle());
        Assertions.assertEquals(bookDto.getGenres(), bookEntity.getGenres());
        Assertions.assertEquals(bookDto.getPublisher(), bookEntity.getPublisher());
        Assertions.assertEquals(bookDto.getPublishDate(), bookEntity.getPublishDate());
    }

    @Test
    public void dtoToEntity() {
        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setAuthor("author");
        bookDto.setTitle("title");
        bookDto.setGenres(new ArrayList<>());
        bookDto.setPublisher("publisher");
        bookDto.setPublishDate(LocalDate.of(1970, 1, 1));

        BookEntity bookEntity = bookMapper.dtoToEntity(bookDto);
        Assertions.assertEquals(bookDto.getId(), bookEntity.getId());
        Assertions.assertEquals(bookDto.getAuthor(), bookEntity.getAuthor());
        Assertions.assertEquals(bookDto.getTitle(), bookEntity.getTitle());
        Assertions.assertEquals(bookDto.getGenres(), bookEntity.getGenres());
        Assertions.assertEquals(bookDto.getPublisher(), bookEntity.getPublisher());
        Assertions.assertEquals(bookDto.getPublishDate(), bookEntity.getPublishDate());
    }
}
