package dev.ryanlioy.bookloger.test.controller;

import dev.ryanlioy.bookloger.controller.CollectionItemController;
import dev.ryanlioy.bookloger.dto.CollectionItemDto;
import dev.ryanlioy.bookloger.service.CollectionItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CollectionItemControllerTest {
    @Mock
    private CollectionItemService collectionItemService;

    private CollectionItemController collectionItemController;

    @BeforeEach
    public void setUp() {
        collectionItemController = new CollectionItemController(collectionItemService);
    }

    @Test
    public void findAllByUserIdAndType_returnListOrDtos() throws Exception {
        when(collectionItemService.findAllByUserIdAndType(any(), any())).thenReturn(List.of(new CollectionItemDto(1L), new CollectionItemDto(2L)));
        ResponseEntity<List<CollectionItemDto>> response = collectionItemController.getCollectionItemsByUserIdAndType("type", 1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertThat(response.getBody().size()).isEqualTo(2);
        assertThat(response.getBody().get(0).getId()).isEqualTo(1L);
        assertThat(response.getBody().get(1).getId()).isEqualTo(2L);
    }

    @Test
    public void create_returnDtosAnd200() {
        when(collectionItemService.create(any())).thenReturn(new CollectionItemDto(1L));
        CollectionItemDto resource = new CollectionItemDto();
        ResponseEntity<CollectionItemDto> response = collectionItemController.create(resource);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    public void findAll_whenItemsDontExist_returnEmptyListAnd200() {
        when(collectionItemService.findAll()).thenReturn(List.of());
        ResponseEntity<List<CollectionItemDto>> response = collectionItemController.getAllCollectionItems();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }

    @Test
    public void findAll_whenItemsExist_returnListAnd200() {
        when(collectionItemService.findAll()).thenReturn(List.of(new  CollectionItemDto(1L)));
        ResponseEntity<List<CollectionItemDto>> response = collectionItemController.getAllCollectionItems();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    public void createCurrentlyReading_whenEntityExists_returnResourceAnd200() {
        collectionItemController.deleteById(1L);

        verify(collectionItemService, times(1)).deleteById(any());
    }

    @Test
    public void deleteById() {
        collectionItemController.deleteById(1L);

        verify(collectionItemService, times(1)).deleteById(any());
    }
}
