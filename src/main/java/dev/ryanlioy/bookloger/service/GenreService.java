package dev.ryanlioy.bookloger.service;

import dev.ryanlioy.bookloger.dto.GenreDto;
import dev.ryanlioy.bookloger.entity.GenreEntity;
import dev.ryanlioy.bookloger.mapper.GenreMapper;
import dev.ryanlioy.bookloger.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GenreService {
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    public GenreService(GenreRepository genreRepository, GenreMapper genreMapper) {
        this.genreRepository = genreRepository;
        this.genreMapper = genreMapper;
    }

    public GenreDto createGenre(GenreDto genreDto) {
        return genreMapper.entityToDto(genreRepository.save(genreMapper.dtoToEntity(genreDto)));
    }

    public GenreDto getGenre(Long id) {
        Optional<GenreEntity> optional = genreRepository.findById(id);
        return optional.map(genreMapper::entityToDto).orElse(null);
    }

    public List<GenreDto> getAllGenres() {
        Iterable<GenreEntity> genres = genreRepository.findAll();
        List<GenreDto> dtos = new ArrayList<>();
        genres.forEach(entity -> dtos.add(genreMapper.entityToDto(entity)));
        return dtos;
    }

    public void deleteGenre(Long id) {
        genreRepository.deleteById(id);
    }
}
