package dev.ryanlioy.booklogger.test.controller;

import dev.ryanlioy.booklogger.controller.BookController;
import dev.ryanlioy.booklogger.dto.BookDto;
import dev.ryanlioy.booklogger.meta.EnvelopeDto;
import dev.ryanlioy.booklogger.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    @Mock
    private BookService bookService;

    private BookController bookController;

    @BeforeEach
    void setUp() {
        bookController = new BookController(bookService);
    }

    @Test
    public void addBook() {
        BookDto bookDto = new BookDto();
        when(bookService.createBook(any(), any())).thenReturn(bookDto);
        ResponseEntity<EnvelopeDto<BookDto>> response = bookController.addBook(bookDto);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(bookDto, response.getBody().getContent());
    }

    @Test
    public void getBookById_bookFound() {
        when(bookService.getBookById(any())).thenReturn(new BookDto());
        ResponseEntity<EnvelopeDto<BookDto>> response = bookController.getBook(1L);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getBookById_bookNotFound() {
        when(bookService.getBookById(any())).thenReturn(null);
        ResponseEntity<EnvelopeDto<BookDto>> response = bookController.getBook(1L);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void getAllBooks_booksFound() {
        List<BookDto> books = List.of(new BookDto());
        when(bookService.getAllBooks()).thenReturn(books);
        ResponseEntity<EnvelopeDto<List<BookDto>>> response = bookController.getAllBooks();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getAllBooks_noBooksFound() {
        when(bookService.getAllBooks()).thenReturn(new ArrayList<>());
        ResponseEntity<EnvelopeDto<List<BookDto>>> response = bookController.getAllBooks();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertTrue(response.getBody().getContent().isEmpty());
    }

    @Test
    public void deleteBook() {
        bookController.deleteBook(1L);
        verify(bookService, times((1))).deleteBookById(1L);
    }
}
