package dev.ryanlioy.bookloger.test.controller;

import dev.ryanlioy.bookloger.controller.GenreController;
import dev.ryanlioy.bookloger.dto.GenreDto;
import dev.ryanlioy.bookloger.service.GenreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GenreControllerTest {
    @Mock
    private GenreService genreService;

    private GenreController genreController;

    @BeforeEach
    public void setUp() {
        genreController = new GenreController(genreService);
    }

    @Test
    public void getAllGenres() {
        when(genreService.getAllGenres()).thenReturn(List.of(new GenreDto()));

        ResponseEntity<List<GenreDto>> response = genreController.getAllGenres();

        assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    public void getGenreById_entityFound_return200() {
        when(genreService.getGenre(any())).thenReturn(new GenreDto());
        ResponseEntity<GenreDto> response = genreController.getGenreById(1L);
        assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void getGenreById_entityFound_return204() {
        when(genreService.getGenre(any())).thenReturn(null);
        ResponseEntity<GenreDto> response = genreController.getGenreById(1L);
        assertThat(HttpStatus.NO_CONTENT).isEqualTo(response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void createGenre() {
        when(genreService.createGenre(any())).thenReturn(new GenreDto());
        ResponseEntity<GenreDto> response = genreController.createGenre(new GenreDto());
        assertThat(HttpStatus.CREATED).isEqualTo(response.getStatusCode());
    }

    @Test
    public void deleteGenre() {
        genreController.deleteGenre(1L);

        verify(genreService, times(1)).deleteGenre(1L);
    }
}
