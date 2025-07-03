package dev.ryanlioy.bookloger.test.service;

import dev.ryanlioy.bookloger.dto.CollectionDto;
import dev.ryanlioy.bookloger.dto.CreateCollectionDto;
import dev.ryanlioy.bookloger.entity.CollectionEntity;
import dev.ryanlioy.bookloger.mapper.CollectionMapper;
import dev.ryanlioy.bookloger.repository.CollectionRepository;
import dev.ryanlioy.bookloger.service.BookService;
import dev.ryanlioy.bookloger.service.CollectionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CollectionServiceTest {
    @Mock
    private CollectionRepository collectionItemRepository;

    @Mock
    private CollectionMapper collectionMapper;

    @Mock
    private BookService bookService;

    private CollectionService collectionService;

    @BeforeEach
    public void setUp() {
        collectionService = new CollectionService(collectionItemRepository, collectionMapper, bookService);
    }

    @Test
    public void findById_whenFound_returnResource() {
        when(collectionItemRepository.findById(any())).thenReturn(Optional.of(new CollectionEntity()));
        CollectionDto resource = new CollectionDto();
        when(collectionMapper.entityToDto(any())).thenReturn(resource);
        CollectionDto response = collectionService.findById(1L);

        assertEquals(resource, response);
    }

    @Test
    public void findById_whenNotFound_returnNull() {
        when(collectionItemRepository.findById(any())).thenReturn(Optional.empty());

        assertNull(collectionService.findById(1L));
    }

    @Test
    public void create_returnResource() {
        when(collectionItemRepository.save(any())).thenReturn(new CollectionEntity());
        when(collectionMapper.entityToDto(any())).thenReturn(new CollectionDto(1L));

        CollectionDto response = collectionService.create(new CreateCollectionDto());

        assertNotNull(response.getId());
    }

    @Test
    public void findAll_whenFound_returnNonEmptyList() {
        when(collectionItemRepository.findAll()).thenReturn(List.of(new CollectionEntity()));
        when(collectionMapper.entityToDto(any())).thenReturn(new CollectionDto());

        List<CollectionDto> response = collectionService.findAll();
        assertThat(response.isEmpty()).isFalse();
    }

    @Test
    public void findAll_whenNotFound_returnEmptyList() {
        when(collectionItemRepository.findAll()).thenReturn(new ArrayList<>());

        List<CollectionDto> response = collectionService.findAll();
        assertThat(response.isEmpty()).isTrue();
    }

    @Test
    public void findAllByUserId_whenFound_returnNonEmptyList() {
        when(collectionItemRepository.findAllByUserId(any())).thenReturn(List.of(new CollectionEntity()));
        CollectionDto dto =  new CollectionDto();
        when(collectionMapper.entityToDto(any())).thenReturn(dto);
        List<CollectionDto> response = collectionService.findAllByUserId(1L);
        assertThat(response.isEmpty()).isFalse();
        assertEquals(response.getFirst(), dto);
    }

    @Test
    public void findAllByUserId_whenNotFound_returnEmptyList() {
        when(collectionItemRepository.findAllByUserId(any())).thenReturn(new ArrayList<>());
        List<CollectionDto> response = collectionService.findAllByUserId(1L);
        assertThat(response.isEmpty()).isTrue();
    }

    @Test
    public void deleteById_deleteEntity() {
        collectionService.deleteById(1L);

        verify(collectionItemRepository, times(1)).deleteById(1L);
    }
}
