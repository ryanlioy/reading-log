package dev.ryanlioy.bookloger.service;

import dev.ryanlioy.bookloger.entity.BookEntity;
import dev.ryanlioy.bookloger.mapper.BookMapper;
import dev.ryanlioy.bookloger.repository.BookRepository;
import dev.ryanlioy.bookloger.resource.BookResource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class BookService {
    private BookRepository bookRepository;
    private BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public Optional<BookEntity> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public BookResource createBook(BookResource bookResource) {
        BookEntity bookEntity = bookMapper.resourceToEntity(bookResource);
        return bookMapper.entityToResource(bookRepository.save(bookEntity));
    }

    public List<BookResource> getAllBooks() {
        Iterable<BookEntity> entities = bookRepository.findAll();
        List<BookResource> bookResources = new ArrayList<>();

        for (BookEntity entity : entities) {
            bookResources.add(bookMapper.entityToResource(entity));
        }

        return bookResources;
    }

    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }
}
