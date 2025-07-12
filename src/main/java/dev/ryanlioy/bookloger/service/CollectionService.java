package dev.ryanlioy.bookloger.service;

import dev.ryanlioy.bookloger.dto.BookDto;
import dev.ryanlioy.bookloger.dto.CollectionDto;
import dev.ryanlioy.bookloger.dto.CreateCollectionDto;
import dev.ryanlioy.bookloger.dto.ModifyCollectionDto;
import dev.ryanlioy.bookloger.entity.CollectionEntity;
import dev.ryanlioy.bookloger.mapper.CollectionMapper;
import dev.ryanlioy.bookloger.repository.CollectionRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class CollectionService {
    private final CollectionRepository collectionRepository;
    private final CollectionMapper collectionMapper;
    private final BookService bookService;
    private final UserService userService;

    public CollectionService(CollectionRepository collectionRepository, CollectionMapper collectionMapper, BookService bookService, @Lazy UserService userService) {
        this.collectionRepository = collectionRepository;
        this.collectionMapper = collectionMapper;
        this.bookService = bookService;
        this.userService = userService;
    }

    /**
     * Find a collection by ID
     * @param id ID of the collection to find
     * @return the matching collection, null if not found
     */
    public CollectionDto findById(Long id) {
        CollectionEntity entity = collectionRepository.findById(id).orElse(null);
        if (entity == null) {
            return null;
        }
        return collectionMapper.entityToDto(entity);
    }

    /**
     * Find all collections for a user
     * @param userId the user ID
     * @return {@link List} of collections for the user
     */
    public List<CollectionDto> findAllByUserId(Long userId) {
        List<CollectionEntity> entities = collectionRepository.findAllByUserId(userId);
        List<CollectionDto> dtos = new ArrayList<>();
        entities.forEach(entity -> dtos.add(collectionMapper.entityToDto(entity)));
        return dtos;
    }

    /**
     * Create a collection
     * @param createCollectionDto the collection to create
     * @return the created collection
     */
    public CollectionDto save(CreateCollectionDto createCollectionDto) {
        Long userId = createCollectionDto.getUserId();
        boolean userExists = userService.doesUserExist(userId);
        if (!userExists) {
            throw new RuntimeException(String.format("User with ID=%s does not exist",  userId));
        }
        List<BookDto> books = bookService.getAllBooksById(createCollectionDto.getBookIds());
        return collectionMapper.entityToDto(collectionRepository.save(collectionMapper.createDtoToEntity(createCollectionDto, books)));
    }

    /**
     * Create a collection, differs from the other save as this one accepts a {@link CollectionDto}
     * @param dto the {@link CollectionDto} to create
     * @return the created collection
     */
    public CollectionDto save(CollectionDto dto) {
        Long userId = dto.getUserId();
        boolean userExists = userService.doesUserExist(userId);
        if (!userExists) {
            throw new RuntimeException(String.format("User with ID=%s does not exist",  userId));
        }
        return collectionMapper.entityToDto(collectionRepository.save(collectionMapper.dtoToEntity(dto)));
    }

    /**
     * Find all collections
     * @return {@link List} of all collections
     */
    public List<CollectionDto> findAll() {
        List<CollectionDto> currentlyReadingDtos = new ArrayList<>();
        collectionRepository.findAll().forEach(
                entity -> currentlyReadingDtos.add(collectionMapper.entityToDto(entity))
        );
        return currentlyReadingDtos;
    }

    /**
     * Add a book to a collection
     * @param dto contains the book ID and collection ID
     * @return the collection with the newly added book
     */
    public CollectionDto addBooksToCollection(ModifyCollectionDto dto) {
        if (dto.getBookIds() == null || dto.getBookIds().isEmpty()) {
            throw new RuntimeException("No books ids were given, nothing to add");
        }
        CollectionDto targetDto = findById(dto.getCollectionId());
        if (targetDto == null) { // TODO more proper error handling
            throw new RuntimeException(String.format("Collection with id %s not found", dto.getCollectionId()));
        }

        List<BookDto> books = bookService.getAllBooksById(dto.getBookIds());
        List<BookDto> newBooks = Stream.concat(books.stream(), targetDto.getBooks().stream()).toList(); // because the repository returns an immutable list!
        targetDto.setBooks(newBooks);
        return save(targetDto);
    }

    public CollectionDto deleteBooksFromCollection(ModifyCollectionDto dto) {
        if (dto.getBookIds() == null || dto.getBookIds().isEmpty()) {
            throw new RuntimeException("No books ids were given, nothing to delete");
        }
        CollectionDto targetDto = findById(dto.getCollectionId());
        if (targetDto == null) { // TODO more proper error handling
            throw new RuntimeException(String.format("Collection with id %s not found", dto.getCollectionId()));
        }
        List<BookDto> books = bookService.getAllBooksById(dto.getBookIds());
        List<BookDto> newBooks = new ArrayList<>(books); // because the repository returns an immutable list!
        newBooks.removeAll(books);
        targetDto.setBooks(newBooks);
        return save(targetDto);
    }

    /**
     * Deletes collections from a list of IDs; mainly used when deleting a user.
     * @param collections {@link List} of collection IDs
     */
    public void deleteAllById(List<CollectionDto> collections) {
        collectionRepository.deleteAllById(collections.stream().map(CollectionDto::getId).toList());
    }

    /**
     * Delete a collection by ID
     * @param id the ID to delete
     */
    public void deleteById(Long id) {
        CollectionDto dto = findById(id);
        if (dto.getIsDefaultCollection()) { // TODO proper error handling
            throw new RuntimeException(String.format("Collection with id %s is a default collection and cannot be deleted", dto.getId()));
        }
        collectionRepository.deleteById(id);
    }
}
