package dev.ryanlioy.bookloger.controller;

import dev.ryanlioy.bookloger.constants.CollectionType;
import dev.ryanlioy.bookloger.entity.BookEntity;
import dev.ryanlioy.bookloger.mapper.BookMapper;
import dev.ryanlioy.bookloger.dto.BookDto;
import dev.ryanlioy.bookloger.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static dev.ryanlioy.bookloger.constants.CollectionType.convertFromCamelCase;

@Controller
@RequestMapping("/book")
public class BookController {
    private BookMapper bookMapper;
    private BookService bookService;

    public BookController(BookMapper bookMapper, BookService bookService) {
        this.bookMapper = bookMapper;
        this.bookService = bookService;
    }

    /**
     * Add a book
     * @param bookDto book to add
     * @return {@link ResponseEntity<BookEntity>}
     */
    @PostMapping("/add")
    public ResponseEntity<BookDto> addBook(@RequestBody BookDto bookDto) {
        return new ResponseEntity<>(bookService.createBook(bookDto), HttpStatus.CREATED);
    }

    /**
     * Get a book by ID
     * @param id the ID of the book to find
     * @return a book and 200 if found, otherwise 404 and no response body
     */
    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable Long id) {
        Optional<BookEntity> optional = bookService.getBookById(id);
        BookDto bookDto = null;
        HttpStatus status = HttpStatus.NO_CONTENT;
        if (optional.isPresent()) {
            bookDto = bookMapper.entityToResource(optional.get());
            status = HttpStatus.OK;
        }

        return new ResponseEntity<>(bookDto, status);
    }

    /**
     * Get all books
     * @return a {@link List} of all books
     */
    @GetMapping("/all")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
    }

    @GetMapping("/collection/{type}/user/{userId}")
    public ResponseEntity<List<BookDto>> getCollectionByUser(@PathVariable String type, @PathVariable Long userId) {
        return new ResponseEntity<>(bookService.findAllBooksInCollectionByUserIdAndType(userId, convertFromCamelCase(type)), HttpStatus.OK);
    }

    /**
     * Delete a bok by ID
     * @param id ID of book to delete
     * @return 200 with no response body
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BookDto> deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
