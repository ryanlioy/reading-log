package dev.ryanlioy.bookloger.test.controller;

import dev.ryanlioy.bookloger.controller.BookController;
import dev.ryanlioy.bookloger.entity.BookEntity;
import dev.ryanlioy.bookloger.mapper.BookMapper;
import dev.ryanlioy.bookloger.resource.BookResource;
import dev.ryanlioy.bookloger.service.BookService;
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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    @Mock
    private BookService bookService;

    @Mock
    private BookMapper bookMapper;

    private BookController bookController;

    @BeforeEach
    void setUp() {
        bookController = new BookController(bookMapper, bookService);
    }

    @Test
    public void addBook() {
        BookResource bookResource = new BookResource();
        when(bookService.createBook(any())).thenReturn(bookResource);
        ResponseEntity<BookResource> response = bookController.addBook(bookResource);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(bookResource, response.getBody());
    }

    @Test
    public void getBookById_bookFound() {
        when(bookService.getBookById(any())).thenReturn(Optional.of(new BookEntity()));
        ResponseEntity<BookResource> response = bookController.getBook(1L);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getBookById_bookNotFound() {
        when(bookService.getBookById(any())).thenReturn(Optional.empty());
        ResponseEntity<BookResource> response = bookController.getBook(1L);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void getAllBooks_booksFound() {
        List<BookResource> books = List.of(new BookResource());
        when(bookService.getAllBooks()).thenReturn(books);
        ResponseEntity<List<BookResource>> response = bookController.getAllBooks();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getAllBooks_noBooksFound() {
        when(bookService.getAllBooks()).thenReturn(new ArrayList<>());
        ResponseEntity<List<BookResource>> response = bookController.getAllBooks();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertTrue(response.getBody().isEmpty());
    }

    @Test
    public void deleteBook() {
        bookController.deleteBook(1L);
        verify(bookService, times((1))).deleteBookById(1L);
    }
}
