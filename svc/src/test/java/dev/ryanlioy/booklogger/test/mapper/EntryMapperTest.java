package dev.ryanlioy.booklogger.test.mapper;

import dev.ryanlioy.booklogger.entity.EntryEntity;
import dev.ryanlioy.booklogger.mapper.EntryMapper;
import dev.ryanlioy.booklogger.dto.EntryDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

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
        entity.setCreationDate(LocalDateTime.now());

        EntryDto dto = entryMapper.entityToDto(entity);
        Assertions.assertEquals(entity.getId(), dto.getId());
        Assertions.assertEquals(entity.getDescription(), dto.getDescription());
        Assertions.assertEquals(entity.getUserId(), dto.getUserId());
        Assertions.assertEquals(entity.getBookId(), dto.getBookId());
        Assertions.assertEquals(entity.getCreationDate(), dto.getCreationDate());
    }

    @Test
    void dtoToEntityTest() {
        EntryDto dto = new EntryDto();
        dto.setId(1L);
        dto.setDescription("description");
        dto.setUserId(2L);
        dto.setBookId(3L);
        dto.setCreationDate(LocalDateTime.now());

        EntryEntity entity = entryMapper.dtoToEntity(dto);
        Assertions.assertEquals(dto.getId(), entity.getId());
        Assertions.assertEquals(dto.getDescription(), entity.getDescription());
        Assertions.assertEquals(dto.getUserId(), entity.getUserId());
        Assertions.assertEquals(dto.getBookId(), entity.getBookId());
        Assertions.assertEquals(dto.getCreationDate(), entity.getCreationDate());
    }
}
