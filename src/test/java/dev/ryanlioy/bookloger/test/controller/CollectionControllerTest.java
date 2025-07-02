package dev.ryanlioy.bookloger.test.controller;

import dev.ryanlioy.bookloger.controller.CollectionController;
import dev.ryanlioy.bookloger.dto.CollectionDto;
import dev.ryanlioy.bookloger.dto.CreateCollectionDto;
import dev.ryanlioy.bookloger.service.CollectionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CollectionControllerTest {
    @Mock
    private CollectionService collectionService;

    private CollectionController collectionItemController;

    @BeforeEach
    public void setUp() {
        collectionItemController = new CollectionController(collectionService);
    }

    @Test
    public void create_returnDtosAnd200() {
        when(collectionService.create(any())).thenReturn(new CollectionDto(1L));
        CreateCollectionDto resource = new CreateCollectionDto(1L);
        ResponseEntity<CollectionDto> response = collectionItemController.create(resource);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    public void findAll_whenItemsDontExist_returnEmptyListAnd200() {
        when(collectionService.findAll()).thenReturn(List.of());
        ResponseEntity<List<CollectionDto>> response = collectionItemController.getAllCollectionItems();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }

    @Test
    public void findAll_whenItemsExist_returnListAnd200() {
        when(collectionService.findAll()).thenReturn(List.of(new  CollectionDto(1L)));
        ResponseEntity<List<CollectionDto>> response = collectionItemController.getAllCollectionItems();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    public void createCurrentlyReading_whenEntityExists_returnResourceAnd200() {
        when(collectionService.create(any())).thenReturn(new CollectionDto(1L));

        ResponseEntity<CollectionDto> response = collectionItemController.create(new CreateCollectionDto(1L));
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void getCollectionItemsByUserId_whenEntitiesExist_returnNonEmptyList() {
        CollectionDto dto = new CollectionDto(1L);
        when(collectionService.findAllByUserId(any())).thenReturn(List.of(dto));
        ResponseEntity<List<CollectionDto>> response = collectionItemController.getCollectionItemsByUserId(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody().getFirst());
    }

    @Test
    public void getCollectionItemsByUserId_whenNoEntitiesExist_returnEmptyList() {
        when(collectionService.findAllByUserId(any())).thenReturn(List.of());
        ResponseEntity<List<CollectionDto>> response = collectionItemController.getCollectionItemsByUserId(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }

    @Test
    public void deleteById() {
        collectionItemController.deleteById(1L);

        verify(collectionService, times(1)).deleteById(any());
    }
}
