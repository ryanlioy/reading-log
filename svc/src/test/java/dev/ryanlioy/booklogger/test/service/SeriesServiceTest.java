package dev.ryanlioy.booklogger.test.service;

import dev.ryanlioy.booklogger.dto.AuthorDto;
import dev.ryanlioy.booklogger.dto.BookDto;
import dev.ryanlioy.booklogger.dto.CreateSeriesDto;
import dev.ryanlioy.booklogger.dto.SeriesDto;
import dev.ryanlioy.booklogger.meta.ErrorDto;
import dev.ryanlioy.booklogger.entity.SeriesEntity;
import dev.ryanlioy.booklogger.mapper.SeriesMapper;
import dev.ryanlioy.booklogger.repository.SeriesRepository;
import dev.ryanlioy.booklogger.service.AuthorService;
import dev.ryanlioy.booklogger.service.BookService;
import dev.ryanlioy.booklogger.service.SeriesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static dev.ryanlioy.booklogger.constants.Errors.ADD_SERIES_AUTHOR_DOES_NOT_EXIST;
import static dev.ryanlioy.booklogger.constants.Errors.ADD_SERIES_NO_AUTHOR;
import static dev.ryanlioy.booklogger.constants.Errors.ADD_SERIES_NO_TITLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SeriesServiceTest {
    @Mock
    private SeriesRepository seriesRepository;
    @Mock
    private SeriesMapper seriesMapper;
    @Mock
    private AuthorService authorService;
    @Mock
    private BookService bookService;

    private SeriesService seriesService;

    @BeforeEach
    public void setUp() {
        seriesService = new SeriesService(seriesRepository, seriesMapper, authorService, bookService);
    }

    @Test
    public void findById_entityFound() {
        when(seriesRepository.findById(any())).thenReturn(Optional.of(new SeriesEntity()));
        SeriesDto dto = new SeriesDto();
        when(seriesMapper.entityToDto(any())).thenReturn(dto);

        SeriesDto seriesDto = seriesService.findById(1L);
        assertEquals(dto, seriesDto);
    }

    @Test
    public void findById_entityNotFound() {
        when(seriesRepository.findById(any())).thenReturn(Optional.empty());

        SeriesDto seriesDto = seriesService.findById(1L);
        assertNull(seriesDto);
    }

    @Test
    public void create_noTitle() {
        when(authorService.doesAuthorExist(any())).thenReturn(true);
        List<ErrorDto> errors = new ArrayList<>();
        CreateSeriesDto createSeriesDto = new CreateSeriesDto();
        createSeriesDto.setAuthor("author");

        SeriesDto dto = seriesService.create(createSeriesDto, errors);
        assertNull(dto);
        assertEquals(1, errors.size());
        assertEquals(ADD_SERIES_NO_TITLE, errors.getFirst().getMessage());
    }

    @Test
    public void create_noAuthor() {
        when(authorService.doesAuthorExist(any())).thenReturn(true);
        List<ErrorDto> errors = new ArrayList<>();
        CreateSeriesDto createSeriesDto = new CreateSeriesDto();
        createSeriesDto.setTitle("title");

        SeriesDto dto = seriesService.create(createSeriesDto, errors);
        assertNull(dto);
        assertEquals(1, errors.size());
        assertEquals(ADD_SERIES_NO_AUTHOR, errors.getFirst().getMessage());
    }

    @Test
    public void create_authorDoesntExist() {
        when(authorService.doesAuthorExist(any())).thenReturn(false);
        List<ErrorDto> errors = new ArrayList<>();
        CreateSeriesDto createSeriesDto = new CreateSeriesDto();
        createSeriesDto.setTitle("title");
        createSeriesDto.setAuthor("author");
        SeriesDto dto = seriesService.create(createSeriesDto, errors);
        assertNull(dto);
        assertEquals(1, errors.size());
        assertEquals(ADD_SERIES_AUTHOR_DOES_NOT_EXIST, errors.getFirst().getMessage());
    }

    @Test
    public void create() {
        when(authorService.doesAuthorExist(any())).thenReturn(true);
        when(bookService.getAllBooksById(any())).thenReturn(List.of(new BookDto()));
        when(authorService.getAuthorByName(any())).thenReturn(new AuthorDto());
        when(seriesMapper.createDtoToEntity(any(), any(), any())).thenReturn(new SeriesEntity());
        SeriesDto dto = new SeriesDto();
        when(seriesMapper.entityToDto(any())).thenReturn(dto);
        when(seriesRepository.save(any())).thenReturn(new SeriesEntity());

        CreateSeriesDto createSeriesDto = new CreateSeriesDto();
        createSeriesDto.setTitle("title");
        createSeriesDto.setAuthor("author");
        ArrayList<ErrorDto> errors = new ArrayList<>();
        SeriesDto seriesDto = seriesService.create(createSeriesDto, errors);
        assertNotNull(seriesDto);
        assertEquals(dto, seriesDto);
    }

    @Test
    public void delete() {
        seriesService.delete(1L);
        verify(seriesRepository, times(1)).deleteById(1L);
    }
}
