package dev.ryanlioy.bookloger.test.service;

import dev.ryanlioy.bookloger.dto.BookDto;
import dev.ryanlioy.bookloger.dto.CollectionDto;
import dev.ryanlioy.bookloger.dto.CreateCollectionDto;
import dev.ryanlioy.bookloger.dto.ModifyCollectionDto;
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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CollectionServiceTest {
    @Mock
    private CollectionRepository collectionRepository;

    @Mock
    private CollectionMapper collectionMapper;

    @Mock
    private BookService bookService;

    private CollectionService collectionService;

    @BeforeEach
    public void setUp() {
        collectionService = new CollectionService(collectionRepository, collectionMapper, bookService);
    }

    @Test
    public void findById_whenFound_returnResource() {
        when(collectionRepository.findById(any())).thenReturn(Optional.of(new CollectionEntity()));
        CollectionDto resource = new CollectionDto();
        when(collectionMapper.entityToDto(any())).thenReturn(resource);
        CollectionDto response = collectionService.findById(1L);

        assertEquals(resource, response);
    }

    @Test
    public void findById_whenNotFound_returnNull() {
        when(collectionRepository.findById(any())).thenReturn(Optional.empty());

        assertNull(collectionService.findById(1L));
    }

    @Test
    public void saveCreateCollectionDto_returnResource() {
        when(collectionRepository.save(any())).thenReturn(new CollectionEntity());
        CollectionDto expected = new CollectionDto(1L);
        when(collectionMapper.entityToDto(any())).thenReturn(expected);
        CollectionDto response = collectionService.save(new CreateCollectionDto());
        assertEquals(expected, response);
    }

    @Test
    public void saveCollectionDto_returnDto() {
        when(collectionRepository.save(any())).thenReturn(new CollectionEntity());
        CollectionDto expected = new CollectionDto(1L);
        when(collectionMapper.entityToDto(any())).thenReturn(expected);
        CollectionDto response = collectionService.save(new CollectionDto());
        assertEquals(expected, response);
    }

    @Test
    public void findAll_whenFound_returnNonEmptyList() {
        when(collectionRepository.findAll()).thenReturn(List.of(new CollectionEntity()));
        when(collectionMapper.entityToDto(any())).thenReturn(new CollectionDto());

        List<CollectionDto> response = collectionService.findAll();
        assertThat(response.isEmpty()).isFalse();
    }

    @Test
    public void findAll_whenNotFound_returnEmptyList() {
        when(collectionRepository.findAll()).thenReturn(new ArrayList<>());

        List<CollectionDto> response = collectionService.findAll();
        assertThat(response.isEmpty()).isTrue();
    }

    @Test
    public void findAllByUserId_whenFound_returnNonEmptyList() {
        when(collectionRepository.findAllByUserId(any())).thenReturn(List.of(new CollectionEntity()));
        CollectionDto dto =  new CollectionDto();
        when(collectionMapper.entityToDto(any())).thenReturn(dto);
        List<CollectionDto> response = collectionService.findAllByUserId(1L);
        assertThat(response.isEmpty()).isFalse();
        assertEquals(response.getFirst(), dto);
    }

    @Test
    public void findAllByUserId_whenNotFound_returnEmptyList() {
        when(collectionRepository.findAllByUserId(any())).thenReturn(new ArrayList<>());
        List<CollectionDto> response = collectionService.findAllByUserId(1L);
        assertThat(response.isEmpty()).isTrue();
    }

    @Test
    public void addBooksToCollection_requestContainsBooksIds_addBooksToCollection() {
        when(collectionRepository.findById(any())).thenReturn(Optional.of(new CollectionEntity(1L)));
        BookDto expectedBook = new BookDto();
        when(bookService.getAllBooksById(any())).thenReturn(List.of(expectedBook));
        when(collectionRepository.save(any())).thenReturn(new CollectionEntity());
        CollectionDto dto = new CollectionDto(1L);
        dto.setBooks(new ArrayList<>());
        when(collectionMapper.entityToDto(any())).thenReturn(dto);
        when(collectionMapper.dtoToEntity(any())).thenReturn(new CollectionEntity(1L));
        ModifyCollectionDto modifyCollectionDto = new ModifyCollectionDto();
        modifyCollectionDto.setBookIds(List.of(1L));
        CollectionDto response = collectionService.addBooksToCollection(modifyCollectionDto);
        assertEquals(1, response.getBooks().size());
        assertEquals(response.getBooks().getFirst(), expectedBook);
    }

    @Test // TODO will need to change once returning errors
    public void addBooksToCollection_collectionDoesNotExist_throwException() {
        when(collectionRepository.findById(any())).thenReturn(Optional.empty());
        ModifyCollectionDto dto = new ModifyCollectionDto();
        dto.setBookIds(List.of(1L));
        assertThrows(RuntimeException.class, () -> collectionService.addBooksToCollection(dto));
    }

    @Test
    public void addBooksToCollection_noBookIds_throwException() {
        assertThrows(RuntimeException.class, () -> collectionService.addBooksToCollection(new ModifyCollectionDto()));
    }

    @Test
    public void deleteById_deleteEntity() {
        collectionService.deleteById(1L);

        verify(collectionRepository, times(1)).deleteById(1L);
    }
}
