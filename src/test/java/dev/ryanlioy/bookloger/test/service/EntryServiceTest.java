package dev.ryanlioy.bookloger.test.service;

import dev.ryanlioy.bookloger.entity.EntryEntity;
import dev.ryanlioy.bookloger.mapper.EntryMapper;
import dev.ryanlioy.bookloger.repository.EntryRepository;
import dev.ryanlioy.bookloger.resource.EntryResource;
import dev.ryanlioy.bookloger.service.EntryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EntryServiceTest {
    @Mock
    private EntryRepository entryRepository;
    @Mock
    private EntryMapper entryMapper;

    private EntryService entryService;

    @BeforeEach
    public void setUp() {
        entryService = new EntryService(entryRepository, entryMapper);
    }

    @Test
    public void createEntry_whenEntryCreated_returnResource() {
        when(entryRepository.save(any())).thenReturn(new EntryEntity());
        EntryResource entryResource = new EntryResource(1L);
        when(entryMapper.entityToResource(any())).thenReturn(entryResource);
        EntryResource returnResource = entryService.createEntry(entryResource);

        Assertions.assertEquals(entryResource, returnResource);
    }

    @Test
    public void getEntryById_whenEntryFound_returnNonEmptyOptional() {
        EntryEntity entity = new EntryEntity(1L);
        when(entryRepository.findById(any())).thenReturn(Optional.of(entity));

        Optional<EntryEntity> returnResource = entryService.getEntryById(1L);
        Assertions.assertTrue(returnResource.isPresent());
        Assertions.assertEquals(entity, returnResource.get());
    }

    @Test
    public void getEntryById_whenEntryNotFound_returnEmptyOptional() {
        when(entryRepository.findById(any())).thenReturn(Optional.empty());
        Optional<EntryEntity> returnResource = entryService.getEntryById(1L);
        Assertions.assertFalse(returnResource.isPresent());
    }

    @Test
    public void getAllEntriesByBookIdAndUserId_whenEntriesFound_returnResourceList() {
        when(entryRepository.findAllByUserIdAndBookId(any(), any())).thenReturn(List.of(new EntryEntity(1L, 3L, 4L), new EntryEntity(2L, 3L, 4L)));
        List<EntryResource> resources = List.of(new EntryResource(1L, 3L, 4L), new EntryResource(1L, 3L, 4L));
        when(entryMapper.entityToResource(any())).thenReturn(resources.get(0)).thenReturn(resources.get(1));

        List<EntryResource> returnEntries = entryService.getEntryByBookIdAndUserId(3L, 4L);
        Assertions.assertEquals(resources, returnEntries);
    }

    @Test
    public void getAllEntriesByBookIdAndUserId_whenEntriesNotFound_returnEmptyList() {
        when(entryRepository.findAllByUserIdAndBookId(any(), any())).thenReturn(List.of());

        List<EntryResource> returnEntries = entryService.getEntryByBookIdAndUserId(3L, 4L);
        Assertions.assertTrue(returnEntries.isEmpty());
    }

    @Test
    public void deleteEntryById_returnResource() {
        entryService.deleteEntryById(1L);

        verify(entryRepository, times(1)).deleteById(1L);
    }
}
