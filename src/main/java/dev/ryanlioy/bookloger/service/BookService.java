package dev.ryanlioy.bookloger.service;

import dev.ryanlioy.bookloger.entity.BookEntity;
import dev.ryanlioy.bookloger.mapper.BookMapper;
import dev.ryanlioy.bookloger.repository.BookRepository;
import dev.ryanlioy.bookloger.dto.BookDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private GenreService genreService;

    public BookService(BookRepository bookRepository, BookMapper bookMapper,  GenreService genreService) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.genreService = genreService;
    }

    public Optional<BookEntity> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public BookDto createBook(BookDto bookDto) {
        // check if genre exists
        bookDto.getGenres().forEach(genre -> {
            if (genreService.getGenre(genre.getId()) == null) {
                throw new IllegalArgumentException("Genre with ID=" + genre.getId() + " does not exist");
            }
        });
        BookEntity bookEntity = bookMapper.resourceToEntity(bookDto);
        return bookMapper.entityToResource(bookRepository.save(bookEntity));
    }

    public List<BookDto> getAllBooks() {
        Iterable<BookEntity> entities = bookRepository.findAll();
        List<BookDto> bookDtos = new ArrayList<>();

        for (BookEntity entity : entities) {
            bookDtos.add(bookMapper.entityToResource(entity));
        }

        return bookDtos;
    }

    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    public List<BookDto> findAllCurrentlyReadingBooks(Long userId) {
        return bookRepository.getCurrentlyReadingBooksByUserId(userId).stream().map(bookMapper::entityToResource).toList();
    }
}
