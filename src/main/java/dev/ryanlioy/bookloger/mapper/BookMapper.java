package dev.ryanlioy.bookloger.mapper;

import dev.ryanlioy.bookloger.entity.BookEntity;
import dev.ryanlioy.bookloger.resource.BookResource;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookEntity resourceToEntity(BookResource bookResource) {
        BookEntity bookEntity = new BookEntity();

        bookEntity.setId(bookResource.getId());
        bookEntity.setSeriesId(bookResource.getSeriesId());
        bookEntity.setTitle(bookResource.getTitle());
        bookEntity.setAuthor(bookResource.getAuthor());
        bookEntity.setPublishDate(bookResource.getPublishDate());
        bookEntity.setGenres(bookResource.getGenres());
        bookEntity.setPublisher(bookResource.getPublisher());

        return bookEntity;
    }

    public BookResource entityToResource(BookEntity bookEntity) {
        BookResource bookResource = new BookResource();

        bookResource.setId(bookEntity.getId());
        bookResource.setSeriesId(bookEntity.getSeriesId());
        bookResource.setTitle(bookEntity.getTitle());
        bookResource.setAuthor(bookEntity.getAuthor());
        bookResource.setPublishDate(bookEntity.getPublishDate());
        bookResource.setGenres(bookEntity.getGenres());
        bookResource.setPublisher(bookEntity.getPublisher());

        bookResource.setGenres(bookEntity.getGenres());
        return bookResource;
    }
}
