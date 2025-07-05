package dev.ryanlioy.bookloger.service;

import dev.ryanlioy.bookloger.dto.BookDto;
import dev.ryanlioy.bookloger.dto.CollectionDto;
import dev.ryanlioy.bookloger.dto.CreateCollectionDto;
import dev.ryanlioy.bookloger.dto.ModifyCollectionDto;
import dev.ryanlioy.bookloger.entity.CollectionEntity;
import dev.ryanlioy.bookloger.mapper.CollectionMapper;
import dev.ryanlioy.bookloger.repository.CollectionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class CollectionService {
    private final CollectionRepository collectionRepository;
    private final CollectionMapper collectionMapper;
    private final BookService bookService;

    public CollectionService(CollectionRepository collectionRepository, CollectionMapper collectionMapper, BookService bookService) {
        this.collectionRepository = collectionRepository;
        this.collectionMapper = collectionMapper;
        this.bookService = bookService;
    }

    public CollectionDto findById(Long id) {
        CollectionEntity entity = collectionRepository.findById(id).orElse(null);
        if (entity == null) {
            return null;
        }
        return collectionMapper.entityToDto(entity);
    }

    public List<CollectionDto> findAllByUserId(Long userId) {
        List<CollectionEntity> entities = collectionRepository.findAllByUserId(userId);
        List<CollectionDto> dtos = new ArrayList<>();
        entities.forEach(entity -> dtos.add(collectionMapper.entityToDto(entity)));
        return dtos;
    }

    public CollectionDto save(CreateCollectionDto resource) {
        List<BookDto> books = bookService.getAllBooksById(resource.getBookIds());
        return collectionMapper.entityToDto(collectionRepository.save(collectionMapper.createDtoToEntity(resource, books)));
    }

    public CollectionDto save(CollectionDto dto) {
        return collectionMapper.entityToDto(collectionRepository.save(collectionMapper.dtoToEntity(dto)));
    }

    public List<CollectionDto> findAll() {
        List<CollectionDto> currentlyReadingDtos = new ArrayList<>();
        collectionRepository.findAll().forEach(
                entity -> currentlyReadingDtos.add(collectionMapper.entityToDto(entity))
        );
        return currentlyReadingDtos;
    }

    public CollectionDto addBooksToCollection(ModifyCollectionDto dto) {
        if (dto.getBookIds() == null || dto.getBookIds().isEmpty()) {
            throw new RuntimeException("No books ids were given, nothing to add");
        }
        CollectionDto targetDto = findById(dto.getCollectionId());
        if (targetDto == null) { // TODO more proper error handling
            throw new RuntimeException(String.format("Collection with id %s not found", dto.getCollectionId()));
        }

        List<BookDto> books = bookService.getAllBooksById(dto.getBookIds());
        List<BookDto> newBooks = Stream.concat(books.stream(), targetDto.getBooks().stream()).toList();
        targetDto.setBooks(newBooks);
        return save(targetDto);
    }

    public void deleteAllById(List<CollectionDto> collections) {
        collectionRepository.deleteAllById(collections.stream().map(CollectionDto::getId).toList());
    }

    public void deleteById(Long id) {
        CollectionDto dto = findById(id);
        if (dto.getIsDefaultCollection()) { // TODO proper error handling
            throw new RuntimeException(String.format("Collection with id %s is a default collection and cannot be deleted", dto.getId()));
        }
        collectionRepository.deleteById(id);
    }
}
