package dev.ryanlioy.bookloger.test.mapper;

import dev.ryanlioy.bookloger.entity.BookEntity;
import dev.ryanlioy.bookloger.mapper.BookMapper;
import dev.ryanlioy.bookloger.resource.BookResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

public class BookMapperTest {
    private BookMapper bookMapper;

    @BeforeEach
    void setUp() {
        bookMapper = new BookMapper();
    }

    @Test
    public void entityToResource() {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(1L);
        bookEntity.setAuthor("author");
        bookEntity.setTitle("title");
        bookEntity.setGenres(new ArrayList<>());
        bookEntity.setPublisher("publisher");
        bookEntity.setSeriesId(0);
        bookEntity.setPublishDate(new Date());

        BookResource bookResource = bookMapper.entityToResource(bookEntity);

        Assertions.assertEquals(bookResource.getId(), bookEntity.getId());
        Assertions.assertEquals(bookResource.getAuthor(), bookEntity.getAuthor());
        Assertions.assertEquals(bookResource.getTitle(), bookEntity.getTitle());
        Assertions.assertEquals(bookResource.getGenres(), bookEntity.getGenres());
        Assertions.assertEquals(bookResource.getPublisher(), bookEntity.getPublisher());
        Assertions.assertEquals(bookResource.getSeriesId(), bookEntity.getSeriesId());
        Assertions.assertEquals(bookResource.getPublishDate(), bookEntity.getPublishDate());
    }

    @Test
    public void resourceToEntity() {
        BookResource bookResource = new BookResource();
        bookResource.setId(1L);
        bookResource.setAuthor("author");
        bookResource.setTitle("title");
        bookResource.setGenres(new ArrayList<>());
        bookResource.setPublisher("publisher");
        bookResource.setSeriesId(0);
        bookResource.setPublishDate(new Date());

        BookEntity bookEntity = bookMapper.resourceToEntity(bookResource);
        Assertions.assertEquals(bookResource.getId(), bookEntity.getId());
        Assertions.assertEquals(bookResource.getAuthor(), bookEntity.getAuthor());
        Assertions.assertEquals(bookResource.getTitle(), bookEntity.getTitle());
        Assertions.assertEquals(bookResource.getGenres(), bookEntity.getGenres());
        Assertions.assertEquals(bookResource.getPublisher(), bookEntity.getPublisher());
        Assertions.assertEquals(bookResource.getSeriesId(), bookEntity.getSeriesId());
        Assertions.assertEquals(bookResource.getPublishDate(), bookEntity.getPublishDate());
    }
}
