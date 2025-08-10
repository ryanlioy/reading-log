package dev.ryanlioy.booklogger.test.service;

import dev.ryanlioy.booklogger.constants.Errors;
import dev.ryanlioy.booklogger.dto.BookDto;
import dev.ryanlioy.booklogger.dto.CollectionDto;
import dev.ryanlioy.booklogger.dto.CreateCollectionDto;
import dev.ryanlioy.booklogger.dto.ModifyCollectionDto;
import dev.ryanlioy.booklogger.meta.ErrorDto;
import dev.ryanlioy.booklogger.entity.CollectionEntity;
import dev.ryanlioy.booklogger.mapper.CollectionMapper;
import dev.ryanlioy.booklogger.repository.CollectionRepository;
import dev.ryanlioy.booklogger.service.BookService;
import dev.ryanlioy.booklogger.service.CollectionService;
import dev.ryanlioy.booklogger.service.UserService;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
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

    @Mock
    private UserService userService;

    private CollectionService collectionService;

    @BeforeEach
    public void setUp() {
        collectionService = new CollectionService(collectionRepository, collectionMapper, bookService, userService);
    }

    @Test
    public void findById_whenFound_returnDto() {
        when(collectionRepository.findById(any())).thenReturn(Optional.of(new CollectionEntity()));
        CollectionDto dto = new CollectionDto();
        when(collectionMapper.entityToDto(any())).thenReturn(dto);
        CollectionDto response = collectionService.findById(1L);

        assertEquals(dto, response);
    }

    @Test
    public void findById_whenNotFound_returnNull() {
        when(collectionRepository.findById(any())).thenReturn(Optional.empty());

        assertNull(collectionService.findById(1L));
    }

    @Test
    public void saveCreateCollectionDto_returnDto() {
        when(collectionRepository.save(any())).thenReturn(new CollectionEntity());
        CollectionDto expected = new CollectionDto(1L);
        when(collectionMapper.entityToDto(any())).thenReturn(expected);
        when(userService.doesUserExist(any())).thenReturn(true);

        CollectionDto response = collectionService.save(new CreateCollectionDto(), new ArrayList<>());
        assertEquals(expected, response);
    }

    @Test
    public void saveCreateCollectionDto_userDoesNotExist_returnError() {
        when(userService.doesUserExist(any())).thenReturn(false);

        List<ErrorDto> errors = new ArrayList<>();
        CollectionDto dto = collectionService.save(new CreateCollectionDto(), errors);

        assertNull(dto);
        assertEquals(1, errors.size());
        assertEquals(Errors.USER_DOES_NOT_EXIST, errors.getFirst().getMessage());
    }

    @Test
    public void saveCreateCollectionDto_bookDoesNotExist_returnError() {
        when(userService.doesUserExist(any())).thenReturn(true);
        when(bookService.getAllBooksById(any())).thenReturn(new ArrayList<>());

        List<ErrorDto> errors = new ArrayList<>();
        CreateCollectionDto createCollectionDto = new CreateCollectionDto();
        createCollectionDto.setBookIds(new ArrayList<>(List.of(1L)));
        CollectionDto dto = collectionService.save(createCollectionDto, errors);

        assertNull(dto);
        assertEquals(1, errors.size());
        assertEquals(String.format(Errors.BOOKS_DO_NOT_EXIST, "[1]"), errors.getFirst().getMessage());
    }

    @Test
    public void saveCreateCollectionDto_multipleUsers_returnError() {
        List<ErrorDto> errors = new ArrayList<>();
        List<CollectionDto> dtos = collectionService.saveAll(List.of(new CreateCollectionDto(1L, "title", false), new CreateCollectionDto(2L, "title", false)), errors);
        assertNull(dtos);
        assertEquals(1, errors.size());
        assertEquals(Errors.SAVE_COLLECTION_MULTIPLE_USERS, errors.getFirst().getMessage());
    }

    @Test
    public void saveCollectionDto_returnDto() {
        when(collectionRepository.save(any())).thenReturn(new CollectionEntity());
        CollectionDto expected = new CollectionDto(1L);
        when(collectionMapper.entityToDto(any())).thenReturn(expected);
        when(userService.doesUserExist(any())).thenReturn(true);

        CollectionDto response = collectionService.save(new CollectionDto(), new ArrayList<>());
        assertEquals(expected, response);
    }

    @Test
    public void saveCollectionDto_userDoesNotExist_returnError() {
        when(userService.doesUserExist(any())).thenReturn(false);

        List<ErrorDto> errors = new ArrayList<>();
        CollectionDto dto = collectionService.save(new CollectionDto(), errors);
        assertNull(dto);
        assertEquals(1, errors.size());
        assertEquals(Errors.USER_DOES_NOT_EXIST, errors.getFirst().getMessage());
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
        CollectionDto dto = new CollectionDto();
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
    public void save_requestContainsBooksIds_addBooksToCollection() {
        when(collectionRepository.findById(any())).thenReturn(Optional.of(new CollectionEntity(1L)));
        BookDto expectedBook = new BookDto();
        when(bookService.getAllBooksById(any())).thenReturn(List.of(expectedBook));
        when(collectionRepository.save(any())).thenReturn(new CollectionEntity());
        CollectionDto dto = new CollectionDto(1L);
        dto.setBooks(new ArrayList<>());
        when(collectionMapper.entityToDto(any())).thenReturn(dto);
        when(collectionMapper.dtoToEntity(any())).thenReturn(new CollectionEntity(1L));
        when(userService.doesUserExist(any())).thenReturn(true);

        ModifyCollectionDto modifyCollectionDto = new ModifyCollectionDto();
        modifyCollectionDto.setBookIds(List.of(1L));
        CollectionDto response = collectionService.addBooksToCollection(modifyCollectionDto, new ArrayList<>());

        assertEquals(1, response.getBooks().size());
        assertEquals(response.getBooks().getFirst(), expectedBook);
    }

    @Test
    public void save_userDoesNotExist_throwException() {
        when(userService.doesUserExist(any())).thenReturn(false);

        List<ErrorDto> errors = new ArrayList<>();
        CollectionDto dto = collectionService.save(new CollectionDto(), errors);

        assertNull(dto);
        assertEquals(1, errors.size());
        assertEquals(Errors.USER_DOES_NOT_EXIST, errors.getFirst().getMessage());
    }

    @Test
    public void addBooksToCollection() {
        when(collectionRepository.findById(any())).thenReturn(Optional.of(new CollectionEntity()));
        when(bookService.getAllBooksById(any())).thenReturn(List.of(new BookDto()));
        when(collectionRepository.save(any())).thenReturn(new CollectionEntity());
        when(userService.doesUserExist(any())).thenReturn(true);
        CollectionDto collectionDto = new CollectionDto(1L);
        collectionDto.setBooks(List.of(new BookDto(2L)));
        when(collectionMapper.entityToDto(any())).thenReturn(collectionDto);

        List<ErrorDto> errors = new ArrayList<>();
        ModifyCollectionDto modifyCollectionDto = new ModifyCollectionDto();
        modifyCollectionDto.setCollectionId(1L);
        modifyCollectionDto.setBookIds(List.of(1L));
        CollectionDto dto = collectionService.addBooksToCollection(modifyCollectionDto, errors);

        assertEquals(0, errors.size());
        assertNotNull(dto);
        assertEquals(collectionDto, dto);
    }

    @Test
    public void addBooksToCollection_collectionDoesNotExist_returnError() {
        when(collectionRepository.findById(any())).thenReturn(Optional.empty());

        ModifyCollectionDto dto = new ModifyCollectionDto();
        dto.setBookIds(List.of(1L));
        List<ErrorDto> errors = new ArrayList<>();
        CollectionDto collectionDto = collectionService.addBooksToCollection(dto, errors);

        assertNull(collectionDto);
        assertEquals(1, errors.size());
        assertEquals(Errors.COLLECTION_DOES_NOT_EXIST, errors.get(0).getMessage());
    }

    @Test
    public void addBooksToCollection_booksDoNotExist_returnError() {
        when(collectionRepository.findById(any())).thenReturn(Optional.of(new CollectionEntity()));
        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        when(bookService.getAllBooksById(any())).thenReturn(new ArrayList<>(List.of(bookDto)));
        CollectionDto collectionDto = new CollectionDto(1L);
        collectionDto.setBooks(List.of(new BookDto()));
        when(collectionMapper.entityToDto(any())).thenReturn(collectionDto);

        ModifyCollectionDto dto = new ModifyCollectionDto();
        dto.setBookIds(new ArrayList<>(List.of(1L, 2L)));
        List<ErrorDto> errors = new ArrayList<>();
        CollectionDto actual = collectionService.addBooksToCollection(dto, errors);

        assertNull(actual);
        assertEquals(1, errors.size());
        assertEquals(String.format(Errors.BOOKS_DO_NOT_EXIST, "[2]"), errors.getFirst().getMessage());
    }

    @Test
    public void removeBooksFromCollection_whenFound_removeBooksFromCollection() {
        BookDto book = new BookDto(1L);
        CollectionDto collection = new CollectionDto(1L);
        collection.setBooks(new ArrayList<>(List.of(new BookDto(1L))));
        when(collectionRepository.findById(any())).thenReturn(Optional.of(new CollectionEntity()));
        when(collectionMapper.entityToDto(any())).thenReturn(collection);
        when(bookService.getAllBooksById(any())).thenReturn(List.of(book));
        when(userService.doesUserExist(any())).thenReturn(true);
        when(collectionRepository.save(any())).thenReturn(new CollectionEntity());

        List<ErrorDto> errors = new ArrayList<>();
        CollectionDto actual = collectionService.deleteBooksFromCollection(new ModifyCollectionDto(1L, List.of(1L)), errors);

        assertTrue(errors.isEmpty());
        assertTrue(actual.getBooks().isEmpty());
    }

    @Test
    public void removeBooksFromCollection_whenNoBookIds_returnError() {
        List<ErrorDto> errors = new ArrayList<>();

        CollectionDto dto = collectionService.deleteBooksFromCollection(new ModifyCollectionDto(1L, new ArrayList<>()), errors);

        assertNull(dto);
        assertEquals(1, errors.size());
        assertEquals(Errors.MISSING_BOOK_IDS, errors.getFirst().getMessage());
    }

    @Test
    public void removeBooksFromCollection_collectionDoesNotExist_returnError() {
        when(collectionRepository.findById(any())).thenReturn(Optional.empty());

        List<ErrorDto> errors = new ArrayList<>();
        CollectionDto dto = collectionService.deleteBooksFromCollection(new ModifyCollectionDto(1L, new ArrayList<>(List.of(1L))), errors);

        assertNull(dto);
        assertEquals(1, errors.size());
        assertEquals(Errors.COLLECTION_DOES_NOT_EXIST, errors.getFirst().getMessage());
    }

    @Test
    public void deleteAllById_deletesAllCollections() {
        collectionService.deleteAllById(List.of(new CollectionDto(1L)));

        verify(collectionRepository, times(1)).deleteAllById(List.of(1L));
    }

    @Test
    public void deleteById_notDefaultCollection_deleteEntity() {
        when(collectionRepository.findById(any())).thenReturn(Optional.of(new CollectionEntity()));
        CollectionDto dto = new CollectionDto();
        dto.setIsDefaultCollection(false);
        when(collectionMapper.entityToDto(any())).thenReturn(dto);

        List<ErrorDto> errors = new ArrayList<>();
        collectionService.deleteById(1L, errors);

        assertThat(errors.isEmpty()).isTrue();
        verify(collectionRepository, times(1)).deleteById(1L);
    }

    @Test
    public void deleteById_defaultCollection_deleteEntity_returnError() {
        when(collectionRepository.findById(any())).thenReturn(Optional.of(new CollectionEntity()));
        CollectionDto dto = new CollectionDto();
        dto.setIsDefaultCollection(true);
        when(collectionMapper.entityToDto(any())).thenReturn(dto);

        List<ErrorDto> errors = new ArrayList<>();
        collectionService.deleteById(1L, errors);

        assertEquals(1, errors.size());
        assertEquals(Errors.DELETE_DEFAULT_COLLECTION, errors.getFirst().getMessage());
        verify(collectionRepository, times(0)).deleteById(1L);
    }

    @Test
    public void saveAll() {
        when(collectionRepository.saveAll(any())).thenReturn(List.of(new CollectionEntity()));
        when(collectionMapper.entityToDto(any())).thenReturn(new CollectionDto(1L));
        when(collectionMapper.createDtoToEntity(any(), any())).thenReturn(new CollectionEntity());

        CreateCollectionDto expected = new CreateCollectionDto(1L);
        expected.setId(1L);
        List<CollectionDto> actual = collectionService.saveAll(List.of(expected), new ArrayList<>());

        assertEquals(expected.getId(), actual.getFirst().getId());
    }

    @Test
    public void saveAll_whenMultipleUsers_throwException() {
        CreateCollectionDto dto1 = new CreateCollectionDto();
        dto1.setUserId(1L);
        CreateCollectionDto dto2 = new CreateCollectionDto();
        dto2.setUserId(2L);
        List<ErrorDto> errors = new ArrayList<>();
        List<CollectionDto> dtos = collectionService.saveAll(List.of(dto1, dto2), errors);
        assertNull(dtos);
        assertEquals(1, errors.size());
        assertEquals(Errors.SAVE_COLLECTION_MULTIPLE_USERS, errors.getFirst().getMessage());
    }
}
