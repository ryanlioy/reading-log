package dev.ryanlioy.bookloger.test.service;

import dev.ryanlioy.bookloger.constants.Errors;
import dev.ryanlioy.bookloger.dto.AuthorDto;
import dev.ryanlioy.bookloger.dto.BookDto;
import dev.ryanlioy.bookloger.dto.CreateAuthorDto;
import dev.ryanlioy.bookloger.dto.meta.ErrorDto;
import dev.ryanlioy.bookloger.entity.AuthorEntity;
import dev.ryanlioy.bookloger.mapper.AuthorMapper;
import dev.ryanlioy.bookloger.repository.AuthorRepository;
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
public class AuthorServiceTest {
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private AuthorMapper authorMapper;
    @Mock
    private BookService bookService;

    private AuthorService authorService;

    @BeforeEach
    public void setup() {
        authorService = new AuthorService(authorRepository, authorMapper, bookService);
    }

    @Test
    public void findAuthorById_entityExists_returnDto() {
        when(authorRepository.findById(any())).thenReturn(Optional.of(new AuthorEntity()));
        AuthorDto expected = new AuthorDto();
        when(authorMapper.entityToDto(any())).thenReturn(expected);

        AuthorDto actual = authorService.findAuthorById(1L);
        assertEquals(expected, actual);
    }

    @Test
    public void findAuthorById_entityDoesNotExist_returnNull() {
        when(authorRepository.findById(any())).thenReturn(Optional.empty());

        AuthorDto actual = authorService.findAuthorById(1L);
        assertNull(actual);
    }

    @Test
    public void createAuthor_returnDto() {
        List<BookDto> books = List.of(new BookDto(1L));
        when(bookService.getAllBooksById(any())).thenReturn(books);
        AuthorDto expected = new AuthorDto(1L);
        when(authorMapper.entityToDto(any())).thenReturn(expected);
        when(authorRepository.save(any())).thenReturn(new AuthorEntity(1L));

        List<ErrorDto> errors = new ArrayList<>();
        AuthorDto actual = authorService.createAuthor(new CreateAuthorDto(), errors);

        assertNotNull(actual);
        assertTrue(errors.isEmpty());
        assertEquals(actual, expected);
    }

    @Test
    public void createAuthor_authorAlreadyExists_returnError() {
        when(authorRepository.findByName(any())).thenReturn(Optional.of(new AuthorEntity(1L)));
        when(authorMapper.entityToDto(any())).thenReturn(new AuthorDto(1L));

        List<ErrorDto> errors = new ArrayList<>();
        AuthorDto actual = authorService.createAuthor(new CreateAuthorDto(), errors);

        assertNull(actual);
        assertFalse(errors.isEmpty());
        assertEquals(Errors.AUTHOR_ALREADY_EXISTS, errors.getFirst().getMessage());
    }

    @Test
    public void deleteAuthorById() {
        authorService.deleteAuthorById(1L);

        verify(authorRepository, times(1)).deleteById(any());
    }

    @Test
    public void getAuthorByName_found() {
        when(authorRepository.findByName(any())).thenReturn(Optional.of(new AuthorEntity()));
        AuthorDto expected = new AuthorDto();
        when(authorMapper.entityToDto(any())).thenReturn(expected);
        AuthorDto actual = authorService.getAuthorByName("name");

        assertEquals(expected, actual);
    }

    @Test
    public void getAuthorByName_notFound() {
        when(authorRepository.findByName(any())).thenReturn(Optional.empty());

        AuthorDto actual = authorService.getAuthorByName("name");

        assertNull(actual);
    }

    @Test
    public void getAuthorsByBookId_found_returnDtos() {
        when(authorRepository.findAllAuthorsByBookId(any())).thenReturn(List.of(new AuthorEntity(1L)));
        when(authorMapper.entityToDto(any())).thenReturn(new AuthorDto(1L));
        List<AuthorDto> actual = authorService.getAuthorsByBookId(1L);
        assertEquals(1, actual.size());
        assertEquals(1L, actual.getFirst().getId());
    }

    @Test
    public void getAuthorsByBookId_notFound_returnEmptyList() {
        when(authorRepository.findAllAuthorsByBookId(any())).thenReturn(List.of());
        List<AuthorDto> actual = authorService.getAuthorsByBookId(1L);
        assertTrue(actual.isEmpty());
    }

    @Test
    public void doesAuthorExist_exists_returnTrue() {
        when(authorRepository.findByName(any())).thenReturn(Optional.of(new AuthorEntity()));

        boolean actual = authorService.doesAuthorExist("name");
        assertTrue(actual);
    }

    @Test
    public void doesAuthorExist_exists_returnFalse() {
        when(authorRepository.findByName(any())).thenReturn(Optional.empty());

        boolean actual = authorService.doesAuthorExist("name");
        assertFalse(actual);
    }
}
