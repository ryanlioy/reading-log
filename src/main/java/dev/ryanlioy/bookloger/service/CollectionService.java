package dev.ryanlioy.bookloger.service;

import dev.ryanlioy.bookloger.dto.BookDto;
import dev.ryanlioy.bookloger.dto.CollectionDto;
import dev.ryanlioy.bookloger.dto.CreateCollectionDto;
import dev.ryanlioy.bookloger.entity.CollectionEntity;
import dev.ryanlioy.bookloger.mapper.CollectionMapper;
import dev.ryanlioy.bookloger.repository.CollectionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public CollectionDto create(CreateCollectionDto resource) {
        List<BookDto> books = bookService.getAllBooksById(resource.getBookIds());
        return collectionMapper.entityToDto(collectionRepository.save(collectionMapper.createDtoToEntity(resource, books)));
    }

    public List<CollectionDto> findAll() {
        List<CollectionDto> currentlyReadingDtos = new ArrayList<>();
        collectionRepository.findAll().forEach(entity ->
                currentlyReadingDtos.add(collectionMapper.entityToDto(entity))
        );
        return currentlyReadingDtos;
    }

    public void deleteById(Long id) {
        collectionRepository.deleteById(id);
    }
}
