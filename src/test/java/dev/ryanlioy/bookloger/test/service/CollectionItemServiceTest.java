package dev.ryanlioy.bookloger.test.service;

import dev.ryanlioy.bookloger.dto.CollectionItemDto;
import dev.ryanlioy.bookloger.entity.CollectionItemEntity;
import dev.ryanlioy.bookloger.mapper.CollectionItemMapper;
import dev.ryanlioy.bookloger.repository.CollectionItemRepositoy;
import dev.ryanlioy.bookloger.service.CollectionItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CollectionItemServiceTest {
    @Mock
    private CollectionItemRepositoy collectionItemRepositoy;

    @Mock
    private CollectionItemMapper collectionItemMapper;

    private CollectionItemService collectionItemService;

    @BeforeEach
    public void setUp() {
        collectionItemService = new CollectionItemService(collectionItemRepositoy, collectionItemMapper);
    }

    @Test
    public void findById_whenFound_returnResource() {
        when(collectionItemRepositoy.findById(any())).thenReturn(Optional.of(new CollectionItemEntity()));
        CollectionItemDto resource = new CollectionItemDto(1L);
        when(collectionItemMapper.entityToDto(any())).thenReturn(resource);
        CollectionItemDto response = collectionItemService.findById(1L);

        assertEquals(resource, response);
    }

    @Test
    public void findById_whenNotFound_returnNull() {
        when(collectionItemRepositoy.findById(any())).thenReturn(Optional.empty());

        assertNull(collectionItemService.findById(1L));
    }

    @Test
    public void create_returnResource() {
        when(collectionItemRepositoy.save(any())).thenReturn(new CollectionItemEntity());
        when(collectionItemMapper.entityToDto(any())).thenReturn(new CollectionItemDto(1L));

        CollectionItemDto response = collectionItemService.create(new CollectionItemDto());

        assertNotNull(response.getId());
    }

    @Test
    public void findAll_whenFound_returnNonEmptyList() {
        when(collectionItemRepositoy.findAll()).thenReturn(List.of(new CollectionItemEntity()));
        when(collectionItemMapper.entityToDto(any())).thenReturn(new CollectionItemDto());

        List<CollectionItemDto> response = collectionItemService.findAll();
        assertThat(response.isEmpty()).isFalse();
    }

    @Test
    public void findAll_whenNotFound_returnEmptyList() {
        when(collectionItemRepositoy.findAll()).thenReturn(new ArrayList<>());

        List<CollectionItemDto> response = collectionItemService.findAll();
        assertThat(response.isEmpty()).isTrue();
    }

    @Test
    public void findAllByUserIdAndType_whenFound_returnNonEmptyList() {
        when(collectionItemRepositoy.findAllItemsByUserIdAndType(any(), any())).thenReturn(List.of(new CollectionItemEntity()));
        when(collectionItemMapper.entityToDto(any())).thenReturn(new CollectionItemDto());

        List<CollectionItemDto> response = collectionItemService.findAllByUserIdAndType(1L, "favorites");
        assertThat(response.isEmpty()).isFalse();
    }

    @Test
    public void findAllByUserIdAndType_whenNothingFound_returnEmptyList() {
        when(collectionItemRepositoy.findAllItemsByUserIdAndType(any(), any())).thenReturn(List.of());

        List<CollectionItemDto> response = collectionItemService.findAllByUserIdAndType(1L, "favorites");
        assertThat(response.isEmpty()).isTrue();
    }

    @Test
    public void findAllByUserIdAndType_whenInvalidType_throwError() {
        assertThrows(IllegalArgumentException.class, () -> collectionItemService.findAllByUserIdAndType(1L, "test"));
    }

    @Test
    public void deleteById_deleteEntity() {
        collectionItemService.deleteById(1L);

        verify(collectionItemRepositoy, times(1)).deleteById(1L);
    }
}
