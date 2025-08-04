package dev.ryanlioy.booklogger.mapper;

import dev.ryanlioy.booklogger.dto.AuthorDto;
import dev.ryanlioy.booklogger.dto.BookDto;
import dev.ryanlioy.booklogger.dto.CreateSeriesDto;
import dev.ryanlioy.booklogger.dto.SeriesDto;
import dev.ryanlioy.booklogger.entity.SeriesEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SeriesMapper {
    private final AuthorMapper authorMapper;
    private final BookMapper bookMapper;

    public SeriesMapper(AuthorMapper authorMapper, BookMapper bookMapper) {
        this.authorMapper = authorMapper;
        this.bookMapper = bookMapper;
    }

    public SeriesEntity createDtoToEntity(CreateSeriesDto seriesDto, List<BookDto> books, AuthorDto author) {
        SeriesEntity entity = new SeriesEntity();
        entity.setTitle(seriesDto.getTitle());
        entity.setDescription(seriesDto.getDescription());
        entity.setAuthor(authorMapper.dtoToEntity(author));
        entity.setBooks(books.stream().map(bookMapper::dtoToEntity).toList());

        return entity;
    }

    public SeriesEntity dtoToEntity(SeriesDto seriesDto) {
        SeriesEntity seriesEntity = new SeriesEntity();
        seriesEntity.setId(seriesDto.getId());
        seriesEntity.setTitle(seriesDto.getTitle());
        seriesEntity.setDescription(seriesDto.getDescription());
        seriesEntity.setAuthor(authorMapper.dtoToEntity(seriesDto.getAuthor()));
        seriesEntity.setBooks(seriesDto.getBooks().stream().map(bookMapper::dtoToEntity).toList());

        return seriesEntity;
    }

    public SeriesDto entityToDto(SeriesEntity seriesEntity) {
        SeriesDto seriesDto = new SeriesDto();
        seriesDto.setId(seriesEntity.getId());
        seriesDto.setTitle(seriesEntity.getTitle());
        seriesDto.setDescription(seriesEntity.getDescription());
        seriesDto.setAuthor(authorMapper.entityToDto(seriesEntity.getAuthor()));
        seriesDto.setBooks(seriesEntity.getBooks().stream().map(bookMapper::entityToDto).toList());

        return seriesDto;
    }
}
