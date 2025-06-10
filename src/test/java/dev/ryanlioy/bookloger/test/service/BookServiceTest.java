package dev.ryanlioy.bookloger.test.service;

import dev.ryanlioy.bookloger.service.BookService;
import dev.ryanlioy.bookloger.entity.BookEntity;
import dev.ryanlioy.bookloger.mapper.BookMapper;
import dev.ryanlioy.bookloger.repository.BookRepository;
import dev.ryanlioy.bookloger.resource.BookResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    private BookService bookService;

    @BeforeEach
    public void setUp() {
        bookService = new BookService(bookRepository, bookMapper);
    }

    @Test
    public void getBookById_returnsBook() {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(1L);
        when(bookRepository.findById((any()))).thenReturn(Optional.of(bookEntity));
        Optional<BookEntity> entity = bookService.getBookById(1L);

        Assertions.assertTrue(entity.isPresent());
        Assertions.assertEquals(bookEntity, entity.get());
    }

    @Test
    public void getBookById_whenNoBookIsFound_returnsEmptyOptional() {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(1L);
        when(bookRepository.findById((any()))).thenReturn(Optional.empty());
        Optional<BookEntity> entity = bookService.getBookById(1L);

        Assertions.assertFalse(entity.isPresent());
    }

    @Test
    public void createBook_whenBookCreated_returnResource() {
        when(bookRepository.save(any())).thenReturn(new BookEntity());
        BookResource bookResource = new BookResource();
        bookResource.setId(1L);
        when(bookMapper.entityToResource(any())).thenReturn(bookResource);
        BookResource returnResource = bookService.createBook(new BookResource());

        Assertions.assertNotNull(returnResource.getId());
    }

    @Test
    public void getAllBooks_whenBooksFound_returnNonEmptyList() {
        when(bookRepository.findAll()).thenReturn(List.of(new BookEntity()));
        when(bookMapper.entityToResource(any())).thenReturn(new BookResource());

        List<BookResource> list = bookService.getAllBooks();
        Assertions.assertFalse(list.isEmpty());
    }

    @Test
    public void getAllBooks_whenBooksNotFound_returnEmptyList() {
        when(bookRepository.findAll()).thenReturn(new ArrayList<>());

        List<BookResource> list = bookService.getAllBooks();
        Assertions.assertTrue(list.isEmpty());
    }

    @Test
    public void deleteBookById_deletesBook() {
        bookRepository.deleteById(1L);

        verify(bookRepository, times(1)).deleteById(any());
    }
}
