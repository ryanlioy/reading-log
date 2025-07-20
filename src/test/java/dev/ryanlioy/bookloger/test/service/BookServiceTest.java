package dev.ryanlioy.bookloger.test.service;

import dev.ryanlioy.bookloger.constants.Errors;
import dev.ryanlioy.bookloger.dto.BookDto;
import dev.ryanlioy.bookloger.dto.meta.ErrorDto;
import dev.ryanlioy.bookloger.entity.BookEntity;
import dev.ryanlioy.bookloger.mapper.BookMapper;
import dev.ryanlioy.bookloger.repository.BookRepository;
import dev.ryanlioy.bookloger.service.AuthorService;
import dev.ryanlioy.bookloger.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private AuthorService authorService;

    private BookService bookService;

    @BeforeEach
    public void setUp() {
        bookService = new BookService(bookRepository, bookMapper, authorService);
    }

    @Test
    public void getBookById_returnsBook() {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(1L);
        when(bookRepository.findById((any()))).thenReturn(Optional.of(bookEntity));
        when(bookMapper.entityToDto(bookEntity)).thenReturn(new BookDto());
        BookDto book = bookService.getBookById(1L);

        assertNotNull(book);
    }

    @Test
    public void getBookById_whenNoBookIsFound_returnsNull() {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(1L);
        when(bookRepository.findById((any()))).thenReturn(Optional.empty());
        BookDto book = bookService.getBookById(1L);

        assertNull(book);
    }

    @Test
    public void createBook_whenBookCreated_returnDto() {
        when(bookRepository.save(any())).thenReturn(new BookEntity());
        when(authorService.doesAuthorExist(any())).thenReturn(true);
        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        when(bookMapper.entityToDto(any())).thenReturn(bookDto);

        List<ErrorDto> errors = new ArrayList<>();
        BookDto returnDto = bookService.createBook(new BookDto(), errors);

        assertTrue(errors.isEmpty());
        assertNotNull(returnDto.getId());
    }

    @Test
    public void createBook_whenAuthorDoesNotExist_returnError() {
        when(authorService.doesAuthorExist(any())).thenReturn(false);

        List<ErrorDto> errors = new ArrayList<>();
        BookDto dto = bookService.createBook(new BookDto(), errors);

        assertNull(dto);
        assertFalse(errors.isEmpty());
        assertEquals(Errors.AUTHOR_DOES_NOT_EXIST, errors.getFirst().getMessage());
    }

    @Test
    public void getAllBooks_whenBooksFound_returnNonEmptyList() {
        when(bookRepository.findAll()).thenReturn(List.of(new BookEntity()));
        when(bookMapper.entityToDto(any())).thenReturn(new BookDto());

        List<BookDto> list = bookService.getAllBooks();
        assertFalse(list.isEmpty());
    }

    @Test
    public void getAllBooks_whenBooksNotFound_returnEmptyList() {
        when(bookRepository.findAll()).thenReturn(new ArrayList<>());

        List<BookDto> list = bookService.getAllBooks();
        assertTrue(list.isEmpty());
    }

    @Test
    public void getAllBooksById_whenBooksFound_returnNonEmptyList() {
        when(bookRepository.findAllById(any())).thenReturn(List.of(new BookEntity()));
        BookDto bookDto = new BookDto();
        when(bookMapper.entityToDto(any())).thenReturn(bookDto);
        List<BookDto> response = bookService.getAllBooksById(new ArrayList<>());
        assertFalse(response.isEmpty());
        assertEquals(response.getFirst(), bookDto);
    }

    @Test
    public void getAllBooksById_whenBooksNotFound_returnEmptyList() {
        when(bookRepository.findAllById(any())).thenReturn(new ArrayList<>());

        List<BookDto> response = bookService.getAllBooksById(new ArrayList<>());
        assertTrue(response.isEmpty());
    }

    @Test
    public void deleteBookById_deletesBook() {
        bookRepository.deleteById(1L);

        verify(bookRepository, times(1)).deleteById(any());
    }
}
