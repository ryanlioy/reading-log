package dev.ryanlioy.bookloger.test.mapper;

import dev.ryanlioy.bookloger.dto.BookDto;
import dev.ryanlioy.bookloger.dto.CollectionDto;
import dev.ryanlioy.bookloger.entity.BookEntity;
import dev.ryanlioy.bookloger.entity.CollectionEntity;
import dev.ryanlioy.bookloger.mapper.BookMapper;
import dev.ryanlioy.bookloger.mapper.CollectionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class CollectionMapperTest {
    private CollectionMapper collectionMapper;

    @Mock
    private BookMapper bookMapper;

    @BeforeEach
    public void setup() {
        collectionMapper = new CollectionMapper(bookMapper);
    }

    // TODO test for createDtoToEntity()

    @Test
    public void entityToDto() {
        CollectionEntity entity = new CollectionEntity();
        entity.setId(1L);
        entity.setUserId(2L);
        entity.setTitle("title");
        entity.setDescription("description");
        entity.setIsDefaultCollection(true);
        List<BookEntity> bookEntities = List.of(new BookEntity());
        entity.setBooks(bookEntities);

        CollectionDto dto = collectionMapper.entityToDto(entity);
        assertThat(dto.getId()).isEqualTo(entity.getId());
        assertThat(dto.getUserId()).isEqualTo(entity.getUserId());
        assertThat(dto.getTitle()).isEqualTo(entity.getTitle());
        assertThat(dto.getDescription()).isEqualTo(entity.getDescription());
        assertThat(dto.getIsDefaultCollection()).isEqualTo(entity.getIsDefaultCollection());
        assertThat(dto.getBooks().size()).isEqualTo(entity.getBooks().size());
    }

    @Test
    public void dtoToEntity() {
        CollectionDto dto = new CollectionDto();
        dto.setId(1L);
        dto.setUserId(2L);
        dto.setTitle("title");
        dto.setDescription("description");
        dto.setIsDefaultCollection(true);
        List<BookDto> bookDtos = List.of(new BookDto());
        dto.setBooks(bookDtos);

        CollectionEntity entity = collectionMapper.dtoToEntity(dto);
        assertThat(entity.getId()).isEqualTo(dto.getId());
        assertThat(entity.getUserId()).isEqualTo(dto.getUserId());
        assertThat(entity.getTitle()).isEqualTo(dto.getTitle());
        assertThat(entity.getDescription()).isEqualTo(dto.getDescription());
        assertThat(entity.getIsDefaultCollection()).isEqualTo(dto.getIsDefaultCollection());
        assertThat(entity.getBooks().size()).isEqualTo(dto.getBooks().size());
    }
}
