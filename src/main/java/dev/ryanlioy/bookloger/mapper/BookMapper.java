package dev.ryanlioy.bookloger.mapper;

import dev.ryanlioy.bookloger.entity.BookEntity;
import dev.ryanlioy.bookloger.dto.BookDto;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    public BookEntity dtoToEntity(BookDto bookDto) {
        BookEntity bookEntity = new BookEntity();

        bookEntity.setId(bookDto.getId());
        bookEntity.setTitle(bookDto.getTitle());
        bookEntity.setAuthor(bookDto.getAuthor());
        bookEntity.setPublishDate(bookDto.getPublishDate());
        bookEntity.setGenres(bookDto.getGenres());
        bookEntity.setPublisher(bookDto.getPublisher());

        return bookEntity;
    }

    public BookDto entityToDto(BookEntity bookEntity) {
        BookDto bookDto = new BookDto();

        bookDto.setId(bookEntity.getId());
        bookDto.setTitle(bookEntity.getTitle());
        bookDto.setAuthor(bookEntity.getAuthor());
        bookDto.setPublishDate(bookEntity.getPublishDate());
        bookDto.setGenres(bookEntity.getGenres());
        bookDto.setPublisher(bookEntity.getPublisher());

        return bookDto;
    }
}
