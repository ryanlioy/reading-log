package dev.ryanlioy.booklogger.test.mapper;

import dev.ryanlioy.booklogger.dto.BookDto;
import dev.ryanlioy.booklogger.dto.CollectionDto;
import dev.ryanlioy.booklogger.dto.CreateCollectionDto;
import dev.ryanlioy.booklogger.entity.BookEntity;
import dev.ryanlioy.booklogger.entity.CollectionEntity;
import dev.ryanlioy.booklogger.mapper.BookMapper;
import dev.ryanlioy.booklogger.mapper.CollectionMapper;
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

    @Test
    public void createDtoToEntity() {
        CreateCollectionDto createCollectionDto = new CreateCollectionDto();
        createCollectionDto.setId(1L);
        createCollectionDto.setTitle("title");
        createCollectionDto.setDescription("description");
        createCollectionDto.setUserId(1L);
        createCollectionDto.setIsDefaultCollection(true);
        createCollectionDto.setBookIds(List.of(1L));

        CollectionEntity entity = collectionMapper.createDtoToEntity(createCollectionDto, List.of(new BookDto()));
        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(createCollectionDto.getId());
        assertThat(entity.getTitle()).isEqualTo(createCollectionDto.getTitle());
        assertThat(entity.getDescription()).isEqualTo(createCollectionDto.getDescription());
        assertThat(entity.getUserId()).isEqualTo(createCollectionDto.getUserId());
        assertThat(entity.getIsDefaultCollection()).isEqualTo(createCollectionDto.getIsDefaultCollection());
        assertThat(entity.getBooks().size()).isEqualTo(1);
    }

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
