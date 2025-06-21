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
    void entityToResourceTest() {
        EntryEntity entity = new EntryEntity();
        entity.setId(1L);
        entity.setDescription("description");
        entity.setUserId(2L);
        entity.setBookId(3L);

        EntryDto resource = entryMapper.entityToResource(entity);
        Assertions.assertEquals(entity.getId(), resource.getId());
        Assertions.assertEquals(entity.getDescription(), resource.getDescription());
        Assertions.assertEquals(entity.getUserId(), resource.getUserId());
        Assertions.assertEquals(entity.getBookId(), resource.getBookId());
    }

    @Test
    void resourceToEntityTest() {
        EntryDto resource = new EntryDto();
        resource.setId(1L);
        resource.setDescription("description");
        resource.setUserId(2L);
        resource.setBookId(3L);

        EntryEntity entity = entryMapper.resourceToEntity(resource);
        Assertions.assertEquals(resource.getId(), entity.getId());
        Assertions.assertEquals(resource.getDescription(), entity.getDescription());
        Assertions.assertEquals(resource.getUserId(), entity.getUserId());
        Assertions.assertEquals(resource.getBookId(), entity.getBookId());
    }
}
