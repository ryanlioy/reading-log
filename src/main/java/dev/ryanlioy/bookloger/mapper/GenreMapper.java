package dev.ryanlioy.bookloger.mapper;

import dev.ryanlioy.bookloger.dto.GenreDto;
import dev.ryanlioy.bookloger.entity.GenreEntity;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper {
    public GenreDto entityToDto(GenreEntity genre) {
        GenreDto genreDto = new GenreDto();
        genreDto.setId(genre.getId());
        genreDto.setName(genre.getName());

        return genreDto;
    }

    public GenreEntity dtoToEntity(GenreDto genreDto) {
        GenreEntity genreEntity = new GenreEntity();
        genreEntity.setId(genreDto.getId());
        genreEntity.setName(genreDto.getName());

        return genreEntity;
    }
}
