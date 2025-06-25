package dev.ryanlioy.bookloger.test.mapper;

import dev.ryanlioy.bookloger.constants.CollectionType;
import dev.ryanlioy.bookloger.dto.CollectionItemDto;
import dev.ryanlioy.bookloger.entity.CollectionItemEntity;
import dev.ryanlioy.bookloger.mapper.CollectionItemMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CollectionItemMapperTest {
    private CollectionItemMapper collectionItemMapper;

    @BeforeEach
    public void setup() {
        collectionItemMapper = new CollectionItemMapper();
    }

    @Test
    public void entityToDto() {
        CollectionItemEntity entity = new CollectionItemEntity();
        entity.setId(1L);
        entity.setUserId(2L);
        entity.setBookId(3L);
        entity.setCollectionType(CollectionType.FINISHED);

        CollectionItemDto dto = collectionItemMapper.entityToDto(entity);
        assertThat(dto.getId()).isEqualTo(entity.getId());
        assertThat(dto.getUserId()).isEqualTo(entity.getUserId());
        assertThat(dto.getBookId()).isEqualTo(entity.getBookId());
        assertThat(dto.getCollectionType()).isEqualTo(entity.getCollectionType());
    }

    @Test
    public void dtoToEntity() {
        CollectionItemDto dto = new CollectionItemDto();
        dto.setId(1L);
        dto.setUserId(2L);
        dto.setBookId(3L);
        dto.setCollectionType(CollectionType.FINISHED);

        CollectionItemEntity entity = collectionItemMapper.dtoToEntity(dto);
        assertThat(entity.getId()).isEqualTo(dto.getId());
        assertThat(entity.getUserId()).isEqualTo(dto.getUserId());
        assertThat(entity.getBookId()).isEqualTo(dto.getBookId());
        assertThat(entity.getCollectionType()).isEqualTo(dto.getCollectionType());
    }
}
