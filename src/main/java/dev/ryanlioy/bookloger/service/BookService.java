package dev.ryanlioy.bookloger.service;

import dev.ryanlioy.bookloger.constants.Errors;
import dev.ryanlioy.bookloger.dto.BookDto;
import dev.ryanlioy.bookloger.dto.meta.ErrorDto;
import dev.ryanlioy.bookloger.entity.BookEntity;
import dev.ryanlioy.bookloger.mapper.BookMapper;
import dev.ryanlioy.bookloger.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class BookService {
    private static final Logger LOG = LoggerFactory.getLogger(BookService.class);
    private static final String CLASS_LOG = "BookService::";
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
        LOG.info("getBookById() " + (optional.isPresent() ? "{}Found book with ID={}" : "{}Could not find book with ID={}"), CLASS_LOG, id);
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
        LOG.info("{}getAllBooksById() Found {} books", CLASS_LOG, books.size());
        return books;
    }

    /**
     * Create a book
     * @param bookDto the book to create
     * @return the created book
     */
    public BookDto createBook(BookDto bookDto, List<ErrorDto> errors) {
        // check that author exists
        String authorName = bookDto.getAuthor();
        boolean authorExists = authorService.doesAuthorExist(authorName);
        if (!authorExists) {
            errors.add(new ErrorDto(Errors.AUTHOR_DOES_NOT_EXIST));
            LOG.error("Attempted to add book with author {} but no author with that name was found", authorName);
            return null;
        }

        if (doesBookExistByTitle(bookDto.getTitle())) {
            errors.add(new ErrorDto(Errors.BOOK_ALREADY_EXISTS));
            LOG.error("Attempted to add book with title {} but already exists", bookDto.getTitle());
            return null;
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
        LOG.info("{}getAllBooks() Found {} books", CLASS_LOG, bookDtos.size());
        return bookDtos;
    }

    /**
     * Deletes a book
     * @param id ID of book to delete
     */
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
        LOG.info("{}deleteBookById() Deleted book with ID={}", CLASS_LOG, id);
    }

    /**
     * Determines if a book exists based on title
     * @param title title of the book
     * @return true if it exists, otherwise false
     */
    public boolean doesBookExistByTitle(String title) {
        return bookRepository.existsByTitle(title);
    }

    /**
     * Determines if a book exists by ID
     * @param id ID of the book
     * @return true if the book exists, otherwise false
     */
    public boolean doesBookExist(Long id) {
        return id != null && bookRepository.existsById(id);
    }
}
