package dev.ryanlioy.bookloger.test.service;

import dev.ryanlioy.bookloger.dto.EntryDto;
import dev.ryanlioy.bookloger.entity.EntryEntity;
import dev.ryanlioy.bookloger.mapper.EntryMapper;
import dev.ryanlioy.bookloger.repository.EntryRepository;
import dev.ryanlioy.bookloger.service.EntryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    public void createEntry_whenEntryCreated_returnDto() {
        when(entryRepository.save(any())).thenReturn(new EntryEntity());
        EntryDto entryDto = new EntryDto(1L);
        when(entryMapper.entityToDto(any())).thenReturn(entryDto);
        EntryDto dto = entryService.createEntry(entryDto);

        assertEquals(entryDto, dto);
    }

    @Test
    public void getEntryById_whenEntryFound_returnDto() {
        EntryEntity entity = new EntryEntity(1L);
        when(entryRepository.findById(any())).thenReturn(Optional.of(entity));
        when(entryMapper.entityToDto(any())).thenReturn(new EntryDto());

        EntryDto dto = entryService.getEntryById(1L);
        assertNotNull(dto);
    }

    @Test
    public void getEntryById_whenEntryNotFound_returnEmptyOptional() {
        when(entryRepository.findById(any())).thenReturn(Optional.empty());
        EntryDto dto = entryService.getEntryById(1L);
        assertNull(dto);
    }

    @Test
    public void getAllEntriesByBookIdAndUserId_whenEntriesFound_returnDtoList() {
        when(entryRepository.findAllByUserIdAndBookId(any(), any())).thenReturn(List.of(new EntryEntity(1L, 3L, 4L), new EntryEntity(2L, 3L, 4L)));
        List<EntryDto> dtos = List.of(new EntryDto(1L, 3L, 4L), new EntryDto(1L, 3L, 4L));
        when(entryMapper.entityToDto(any())).thenReturn(dtos.get(0)).thenReturn(dtos.get(1));

        List<EntryDto> returnEntries = entryService.getEntryByBookIdAndUserId(3L, 4L);
        assertEquals(dtos, returnEntries);
    }

    @Test
    public void getAllEntriesByBookIdAndUserId_whenEntriesNotFound_returnEmptyList() {
        when(entryRepository.findAllByUserIdAndBookId(any(), any())).thenReturn(List.of());

        List<EntryDto> returnEntries = entryService.getEntryByBookIdAndUserId(3L, 4L);
        assertTrue(returnEntries.isEmpty());
    }

    @Test
    public void deleteEntryById_returnDto() {
        entryService.deleteEntryById(1L);

        verify(entryRepository, times(1)).deleteById(1L);
    }
}
