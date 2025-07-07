package dev.ryanlioy.bookloger.test.mapper;

import dev.ryanlioy.bookloger.entity.EntryEntity;
import dev.ryanlioy.bookloger.mapper.EntryMapper;
import dev.ryanlioy.bookloger.dto.EntryDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EntryMapperTest {
    private EntryMapper entryMapper;

    @BeforeEach
    void setUp() {
        entryMapper = new EntryMapper();
    }

    @Test
    void entityToDtoTest() {
        EntryEntity entity = new EntryEntity();
        entity.setId(1L);
        entity.setDescription("description");
        entity.setUserId(2L);
        entity.setBookId(3L);

        EntryDto dto = entryMapper.entityToDto(entity);
        Assertions.assertEquals(entity.getId(), dto.getId());
        Assertions.assertEquals(entity.getDescription(), dto.getDescription());
        Assertions.assertEquals(entity.getUserId(), dto.getUserId());
        Assertions.assertEquals(entity.getBookId(), dto.getBookId());
    }

    @Test
    void dtoToEntityTest() {
        EntryDto dto = new EntryDto();
        dto.setId(1L);
        dto.setDescription("description");
        dto.setUserId(2L);
        dto.setBookId(3L);

        EntryEntity entity = entryMapper.dtoToEntity(dto);
        Assertions.assertEquals(dto.getId(), entity.getId());
        Assertions.assertEquals(dto.getDescription(), entity.getDescription());
        Assertions.assertEquals(dto.getUserId(), entity.getUserId());
        Assertions.assertEquals(dto.getBookId(), entity.getBookId());
    }
}
