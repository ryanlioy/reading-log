package dev.ryanlioy.bookloger.test.controller;

import dev.ryanlioy.bookloger.controller.EntryController;
import dev.ryanlioy.bookloger.dto.EntryDto;
import dev.ryanlioy.bookloger.entity.EntryEntity;
import dev.ryanlioy.bookloger.mapper.EntryMapper;
import dev.ryanlioy.bookloger.service.EntryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EntryControllerTest {
    @Mock
    private EntryService entryService;
    @Mock
    private EntryMapper entryMapper;

    private EntryController entryController;

    @BeforeEach
    public void setUp() {
        entryController = new EntryController(entryService, entryMapper);
    }

    @Test
    public void createEntry_createsEntry() {
        EntryDto entryDto = new EntryDto();
        when(entryService.createEntry(any())).thenReturn(entryDto);

        var response = entryController.createEntry(entryDto);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getContent()).isEqualTo(entryDto);
    }

    @Test
    public void getEntriesByUserIdAndBookId_whenEntitiesExist_returnNonEmptyResponse() {
        EntryDto entryDto = new EntryDto();
        when(entryService.getEntryByBookIdAndUserId(any(), any())).thenReturn(List.of(entryDto));
        var response = entryController.getEntriesByUserIdAndBookId(1L, 2L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertTrue(response.getBody().getContent().contains(entryDto));
    }

    @Test
    public void getEntriesByUserIdAndBookId_whenEntitiesDontExist_returnEmptyResponse() {
        when(entryService.getEntryByBookIdAndUserId(any(), any())).thenReturn(List.of());
        var response = entryController.getEntriesByUserIdAndBookId(1L, 2L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertTrue(response.getBody().getContent().isEmpty());
    }

    @Test
    public void getEntryById_entityExists() {
        EntryEntity entryEntity = new EntryEntity();
        when(entryService.getEntryById(any())).thenReturn(Optional.of(entryEntity));
        when(entryMapper.entityToResource(any())).thenReturn(new EntryDto());
        var response = entryController.getEntryById(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getContent()).isNotNull();
    }

    @Test
    public void getEntryById_entityDoesNotExist() {
        when(entryService.getEntryById(any())).thenReturn(Optional.empty());
        var response = entryController.getEntryById(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody().getContent()).isNull();
    }

    @Test
    public void deleteEntryById() {
        entryController.deleteEntryById(1L);

        verify(entryService, times(1)).deleteEntryById(any());
    }
}
