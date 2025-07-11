package dev.ryanlioy.bookloger.controller;

import dev.ryanlioy.bookloger.dto.BookDto;
import dev.ryanlioy.bookloger.dto.meta.EnvelopeDto;
import dev.ryanlioy.bookloger.entity.BookEntity;
import dev.ryanlioy.bookloger.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Add a book
     * @param bookDto book to add
     * @return {@link ResponseEntity<BookEntity>}
     */
    @PostMapping("/add")
    public ResponseEntity<EnvelopeDto<BookDto>> addBook(@RequestBody BookDto bookDto) {
        return new ResponseEntity<>(new EnvelopeDto<>(bookService.createBook(bookDto)), HttpStatus.CREATED);
    }

    /**
     * Get a book by ID
     * @param id the ID of the book to find
     * @return a book and 200 if found, otherwise 404 and no response body
     */
    @GetMapping("/{id}")
    public ResponseEntity<EnvelopeDto<BookDto>> getBook(@PathVariable Long id) {
        BookDto book = bookService.getBookById(id);
        HttpStatus status = HttpStatus.NO_CONTENT;
        if (book != null) {
            status = HttpStatus.OK;
        }

        return new ResponseEntity<>(new EnvelopeDto<>(book), status);
    }

    /**
     * Get all books
     * @return a {@link List} of all books
     */
    @GetMapping("/all")
    public ResponseEntity<EnvelopeDto<List<BookDto>>> getAllBooks() {
        return new ResponseEntity<>(new EnvelopeDto<>(bookService.getAllBooks()), HttpStatus.OK);
    }

    /**
     * Delete a bok by ID
     * @param id ID of book to delete
     * @return 200 with no response body
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<EnvelopeDto<BookDto>> deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
