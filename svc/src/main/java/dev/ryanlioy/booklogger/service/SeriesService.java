package dev.ryanlioy.booklogger.service;

import dev.ryanlioy.booklogger.dto.AuthorDto;
import dev.ryanlioy.booklogger.dto.BookDto;
import dev.ryanlioy.booklogger.dto.CreateSeriesDto;
import dev.ryanlioy.booklogger.dto.SeriesDto;
import dev.ryanlioy.booklogger.meta.ErrorDto;
import dev.ryanlioy.booklogger.entity.SeriesEntity;
import dev.ryanlioy.booklogger.mapper.SeriesMapper;
import dev.ryanlioy.booklogger.repository.SeriesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static dev.ryanlioy.booklogger.constants.Errors.ADD_SERIES_AUTHOR_DOES_NOT_EXIST;
import static dev.ryanlioy.booklogger.constants.Errors.ADD_SERIES_NO_AUTHOR;
import static dev.ryanlioy.booklogger.constants.Errors.ADD_SERIES_NO_TITLE;

@Service
public class SeriesService {
    private static final Logger LOG = LoggerFactory.getLogger(SeriesService.class);
    private static final String CLASS_LOG = "SeriesService::";

    private final SeriesRepository seriesRepository;
    private final SeriesMapper seriesMapper;
    private final AuthorService authorService;
    private final BookService bookService;

    public SeriesService(SeriesRepository seriesRepository, SeriesMapper seriesMapper, AuthorService authorService, BookService bookService) {
        this.seriesRepository = seriesRepository;
        this.seriesMapper = seriesMapper;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    public SeriesDto findById(Long id) {
        SeriesEntity entity = seriesRepository.findById(id).orElse(null);
        if (entity == null) {
            LOG.info("{}findByID() series with ID={} not found", CLASS_LOG, id);
            return null;
        }
        LOG.info("{}findByID() series with ID={} found", CLASS_LOG, id);
        return seriesMapper.entityToDto(entity);
    }

    public SeriesDto create(CreateSeriesDto seriesDto, List<ErrorDto> errors) {
        if (seriesDto.getTitle() == null) {
            LOG.error("{}create() attempting to create series without title", CLASS_LOG);
            errors.add(new ErrorDto(ADD_SERIES_NO_TITLE));
        }
        if (seriesDto.getAuthor() == null) {
            LOG.error("{}create() attempting to create series without author", CLASS_LOG);
            errors.add(new ErrorDto(ADD_SERIES_NO_AUTHOR));
        }
        if (!authorService.doesAuthorExist(seriesDto.getAuthor())) {
            LOG.error("{}create() attempting to create series but author with name does not exist", CLASS_LOG);
            errors.add(new ErrorDto(ADD_SERIES_AUTHOR_DOES_NOT_EXIST));
        }

        if (!errors.isEmpty()) {
            return null;
        }

        List<BookDto> books = bookService.getAllBooksById(seriesDto.getBooks());
        AuthorDto author = authorService.getAuthorByName(seriesDto.getAuthor());
        SeriesEntity entity = seriesMapper.createDtoToEntity(seriesDto, books, author);
        LOG.info("{}create() created series with ID={} ", CLASS_LOG, entity.getId());
        return seriesMapper.entityToDto(seriesRepository.save(entity));
    }

    public void delete(Long id) {
        seriesRepository.deleteById(id);
        LOG.info("{}delete() deleted series with ID={}", CLASS_LOG, id);
    }
}
