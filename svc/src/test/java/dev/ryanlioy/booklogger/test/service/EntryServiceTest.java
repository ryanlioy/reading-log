package dev.ryanlioy.booklogger.test.service;

import dev.ryanlioy.booklogger.constants.Errors;
import dev.ryanlioy.booklogger.dto.EntryDto;
import dev.ryanlioy.booklogger.meta.ErrorDto;
import dev.ryanlioy.booklogger.entity.EntryEntity;
import dev.ryanlioy.booklogger.mapper.EntryMapper;
import dev.ryanlioy.booklogger.repository.EntryRepository;
import dev.ryanlioy.booklogger.service.BookService;
import dev.ryanlioy.booklogger.service.EntryService;
import dev.ryanlioy.booklogger.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
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
    @Mock
    private BookService bookService;
    @Mock
    private UserService userService;

    private EntryService entryService;

    @BeforeEach
    public void setUp() {
        entryService = new EntryService(entryRepository, entryMapper, userService, bookService);
    }

    @Test
    public void createEntry_whenEntryCreated_returnDto() {
        when(userService.doesUserExist(any())).thenReturn(true);
        when(bookService.doesBookExist(any())).thenReturn(true);
        when(entryRepository.save(any())).thenReturn(new EntryEntity());
        EntryDto entryDto = new EntryDto(1L);
        when(entryMapper.entityToDto(any())).thenReturn(entryDto);
        List<ErrorDto> errors = new ArrayList<>();
        EntryDto dto = entryService.createEntry(entryDto, errors);

        assertTrue(errors.isEmpty());
        assertEquals(entryDto, dto);
    }

    @Test
    public void createEntry_userDoesNotExist_returnError() {
        when(userService.doesUserExist(any())).thenReturn(false);

        List<ErrorDto> errors = new ArrayList<>();
        EntryDto entry = entryService.createEntry(new EntryDto(1L), errors);

        assertNull(entry);
        assertEquals(1, errors.size());
        assertEquals(Errors.USER_DOES_NOT_EXIST, errors.getFirst().getMessage());
    }

    @Test
    public void createEntry_bookDoesNotExist_returnError() {
        when(userService.doesUserExist(any())).thenReturn(true);
        when(bookService.doesBookExist(any())).thenReturn(false);

        List<ErrorDto> errors = new ArrayList<>();
        EntryDto entry = entryService.createEntry(new EntryDto(1L), errors);

        assertNull(entry);
        assertEquals(1, errors.size());
        assertEquals(Errors.BOOK_DOES_NOT_EXIST, errors.getFirst().getMessage());
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
