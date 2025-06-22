package dev.ryanlioy.bookloger.test.service;

import dev.ryanlioy.bookloger.dto.GenreDto;
import dev.ryanlioy.bookloger.entity.GenreEntity;
import dev.ryanlioy.bookloger.mapper.GenreMapper;
import dev.ryanlioy.bookloger.repository.GenreRepository;
import dev.ryanlioy.bookloger.service.GenreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GenreServiceTest {
    @Mock
    private GenreRepository genreRepository;

    @Mock
    private GenreMapper genreMapper;

    private GenreService genreService;

    @BeforeEach
    public void setup() {
        genreService = new GenreService(genreRepository, genreMapper);
    }

    @Test
    public void findById_whenFound_returnDto() {
        when(genreRepository.findById(any())).thenReturn(Optional.of(new GenreEntity()));
        GenreDto dto = new GenreDto();
        dto.setId(1L);
        dto.setName("name");
        when(genreMapper.entityToDto(any())).thenReturn(dto);

        GenreDto response = genreService.getGenre(1L);
        assertThat(dto.getId()).isEqualTo(response.getId());
        assertThat(dto.getName()).isEqualTo(response.getName());
    }

    @Test
    public void findById_whenNotFound_returnNull() {
        when(genreRepository.findById(any())).thenReturn(Optional.empty());

        GenreDto response = genreService.getGenre(1L);
        assertThat(response).isNull();
    }

    @Test
    public void createGenre_createsGenre() {
        when(genreRepository.save(any())).thenReturn(new GenreEntity());
        GenreDto dto = new GenreDto();
        dto.setId(1L);
        dto.setName("name");
        when(genreMapper.entityToDto(any())).thenReturn(dto);

        GenreDto response = genreService.createGenre(new GenreDto());
        assertThat(dto.getId()).isEqualTo(response.getId());
        assertThat(dto.getName()).isEqualTo(response.getName());
    }

    @Test
    public void getAllGenres_whenFound_returnDtos() {
        when(genreRepository.findAll()).thenReturn(List.of(new GenreEntity()));
        List<GenreDto> dtos = genreService.getAllGenres();
        assertEquals(1, dtos.size());
    }

    @Test
    public void getAllGenres_whenNotFound_returnEmptyList() {
        when(genreRepository.findAll()).thenReturn(List.of());
        List<GenreDto> dtos = genreService.getAllGenres();
        assertTrue(dtos.isEmpty());
    }

    @Test
    public void deleteGenre() {
        genreService.deleteGenre(1L);
        verify(genreRepository, times(1)).deleteById(1L);
    }
}
