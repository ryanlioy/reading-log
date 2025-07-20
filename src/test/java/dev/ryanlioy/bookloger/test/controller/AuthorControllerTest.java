package dev.ryanlioy.bookloger.test.controller;

import dev.ryanlioy.bookloger.controller.AuthorController;
import dev.ryanlioy.bookloger.dto.AuthorDto;
import dev.ryanlioy.bookloger.dto.CreateAuthorDto;
import dev.ryanlioy.bookloger.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthorControllerTest {
    @Mock
    private AuthorService authorService;

    private AuthorController authorController;

    @BeforeEach
    public void setup() {
        authorController = new AuthorController(authorService);
    }

    @Test
    public void getAuthor_whenEntityExists_return200AndDto() {
        AuthorDto expected = new AuthorDto();
        when(authorService.findAuthorById(any())).thenReturn(expected);

        var response = authorController.getAuthor(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody().getContent());
    }

    @Test
    public void getAuthor_whenEntityDoesNotExist_return204NoBody() {
        when(authorService.findAuthorById(any())).thenReturn(null);

        var response = authorController.getAuthor(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void createAuthor_returnsDto() {
        AuthorDto expected = new AuthorDto();
        when(authorService.createAuthor(any(), any())).thenReturn(expected);

        var response = authorController.createAuthor(new CreateAuthorDto());
        assertEquals(expected, response.getBody().getContent());
    }

    @Test
    public void deleteAuthor() {
        authorController.deleteAuthor(1L);

        verify(authorService, times(1)).deleteAuthorById(any());
    }

    @Test
    public void getAuthorsByBookId_entitiesFound_returnDtosAnd200() {
        AuthorDto expected = new AuthorDto();
        when(authorService.getAuthorsByBookId(any())).thenReturn(List.of(expected));

        var response = authorController.getAuthorsByBookId(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody().getContent().getFirst());
    }

    @Test
    public void getAuthorsByBookId_entitiesNotFound_returnEmptyListAnd200() {
        when(authorService.getAuthorsByBookId(any())).thenReturn(List.of());

        var response = authorController.getAuthorsByBookId(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().getContent().isEmpty());
    }
}
