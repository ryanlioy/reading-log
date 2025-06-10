package dev.ryanlioy.bookloger.controller;

import dev.ryanlioy.bookloger.entity.BookEntity;
import dev.ryanlioy.bookloger.mapper.BookMapper;
import dev.ryanlioy.bookloger.resource.BookResource;
import dev.ryanlioy.bookloger.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
     * @param bookResource book to add
     * @return {@link ResponseEntity<BookEntity>}
     */
    @PostMapping("/add")
    public ResponseEntity<BookResource> addBook(@RequestBody BookResource bookResource) {
        return new ResponseEntity<>(bookService.createBook(bookResource), HttpStatus.CREATED);
    }

    /**
     * Get a book by ID
     * @param id the ID of the book to find
     * @return a book and 200 if found, otherwise 404 and no response body
     */
    @GetMapping("/{id}")
    public ResponseEntity<BookResource> getBook(@PathVariable Long id) {
        Optional<BookEntity> optional = bookService.getBookById(id);
        BookResource bookResource = null;
        HttpStatus status = HttpStatus.NO_CONTENT;
        if (optional.isPresent()) {
            bookResource = bookMapper.entityToResource(optional.get());
            status = HttpStatus.OK;
        }

        return new  ResponseEntity<>(bookResource, status);
    }

    /**
     * Get all books
     * @return a {@link List} of all books
     */
    @GetMapping("/all")
    public ResponseEntity<List<BookResource>> getAllBooks() {
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
    }

    /**
     * Delete a bok by ID
     * @param id ID of book to delete
     * @return 200 with no response body
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BookResource> deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
