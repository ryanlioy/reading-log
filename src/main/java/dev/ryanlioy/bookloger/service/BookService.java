package dev.ryanlioy.bookloger.service;

import dev.ryanlioy.bookloger.dto.BookDto;
import dev.ryanlioy.bookloger.entity.BookEntity;
import dev.ryanlioy.bookloger.mapper.BookMapper;
import dev.ryanlioy.bookloger.repository.BookRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final AuthorService authorService;

    public BookService(BookRepository bookRepository, BookMapper bookMapper, @Lazy AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.authorService = authorService;
    }

    /**
     * Get a book by ID
     * @param id the book ID
     * @return an optional containing the specified book
     */
    public BookDto getBookById(Long id) {
        Optional<BookEntity> optional = bookRepository.findById(id);
        return optional.map(bookMapper::entityToDto).orElse(null);
    }

    /**
     * Get all books by ID
     * @param ids a {@link List} of book IDs
     * @return the matching books
     */
    public List<BookDto> getAllBooksById(List<Long> ids) {
        Iterable<BookEntity> entities = bookRepository.findAllById(ids);
        List<BookDto> books = new ArrayList<>();
        entities.forEach(e -> books.add(bookMapper.entityToDto(e)));
        return books;
    }

    /**
     * Create a book
     * @param bookDto the book to create
     * @return the created book
     */
    public BookDto createBook(BookDto bookDto) {
        // check that author exists
        String authorName = bookDto.getAuthor();
        boolean authorExists = authorService.doesAuthorExist(authorName);
        if (!authorExists) {
            throw new RuntimeException(String.format("No author with name %s exists", authorName));
        }

        BookEntity bookEntity = bookMapper.dtoToEntity(bookDto);
        return bookMapper.entityToDto(bookRepository.save(bookEntity));
    }

    /**
     * Get all books
     * @return {@link List} of all books
     */
    public List<BookDto> getAllBooks() {
        Iterable<BookEntity> entities = bookRepository.findAll();
        List<BookDto> bookDtos = new ArrayList<>();

        for (BookEntity entity : entities) {
            bookDtos.add(bookMapper.entityToDto(entity));
        }

        return bookDtos;
    }

    /**
     * Deletes a book
     * @param id ID of book to delete
     */
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }
}
