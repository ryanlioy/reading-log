package dev.ryanlioy.bookloger.test.mapper;

import dev.ryanlioy.bookloger.dto.GenreDto;
import dev.ryanlioy.bookloger.entity.GenreEntity;
import dev.ryanlioy.bookloger.mapper.GenreMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GenreMapperTest {
    private GenreMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new GenreMapper();
    }

    @Test
    public void dtoToEntity() {
        GenreDto genreDto = new GenreDto();
        genreDto.setId(1L);
        genreDto.setName("name");

        GenreEntity genreEntity = mapper.dtoToEntity(genreDto);

        assertThat(genreEntity.getId()).isEqualTo(genreDto.getId());
        assertThat(genreEntity.getName()).isEqualTo(genreDto.getName());
    }

    @Test
    public void entityToDto() {
        GenreEntity genreEntity = new GenreEntity();
        genreEntity.setId(1L);
        genreEntity.setName("name");

        GenreDto genreDto = mapper.entityToDto(genreEntity);

        assertThat(genreDto.getId()).isEqualTo(genreEntity.getId());
        assertThat(genreDto.getName()).isEqualTo(genreEntity.getName());
    }
}
