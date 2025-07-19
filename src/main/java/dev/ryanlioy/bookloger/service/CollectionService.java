package dev.ryanlioy.bookloger.service;

import dev.ryanlioy.bookloger.constants.Errors;
import dev.ryanlioy.bookloger.dto.BookDto;
import dev.ryanlioy.bookloger.dto.CollectionDto;
import dev.ryanlioy.bookloger.dto.CreateCollectionDto;
import dev.ryanlioy.bookloger.dto.ModifyCollectionDto;
import dev.ryanlioy.bookloger.dto.meta.ErrorDto;
import dev.ryanlioy.bookloger.entity.CollectionEntity;
import dev.ryanlioy.bookloger.mapper.CollectionMapper;
import dev.ryanlioy.bookloger.repository.CollectionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CollectionService {
    private static final Logger LOG = LoggerFactory.getLogger(CollectionService.class);
    private static final String CLASS_LOG = "CollectionService::";
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
            LOG.info("{}findById() No collection found with id {}", CLASS_LOG, id);
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
        LOG.info("{}findAllByUserId() Found {} collections for user with ID={}", CLASS_LOG, entities.size(), userId);
        List<CollectionDto> dtos = new ArrayList<>();
        entities.forEach(entity -> dtos.add(collectionMapper.entityToDto(entity)));
        return dtos;
    }

    /**
     * Create a collection
     * @param createCollectionDto the collection to create
     * @return the created collection
     */
    public CollectionDto save(CreateCollectionDto createCollectionDto, List<ErrorDto> errors) {
        Long userId = createCollectionDto.getUserId();
        boolean userExists = userService.doesUserExist(userId);
        if (!userExists) {
            LOG.error("{}save(CreateCollectionDto) Attempted to save collection for user with ID={}, but no such user exists", CLASS_LOG, userId);
            errors.add(new ErrorDto(Errors.USER_DOES_NOT_EXIST));
            return null;
        }
        List<BookDto> books = bookService.getAllBooksById(createCollectionDto.getBookIds());
        // if books returned is smaller than the requested books than some don't exist
        if (books.size() != createCollectionDto.getBookIds().size()) {
            List<Long> missingIds = createCollectionDto.getBookIds();
            // remove all ids that were returned by query
            missingIds.removeAll(books.stream().map(BookDto::getId).toList());
            LOG.error("{}save(CreateCollectionDto) Attempted to create collection but books with ID={} dp not exist", CLASS_LOG, missingIds);
            errors.add(new ErrorDto(String.format(Errors.BOOKS_DO_NOT_EXIST, missingIds)));
            return null;
        }
        CollectionEntity entity = collectionRepository.save(collectionMapper.createDtoToEntity(createCollectionDto, books));
        LOG.info("{}save() Saved collection with ID={}", CLASS_LOG, entity.getId());
        return collectionMapper.entityToDto(entity);
    }

    public List<CollectionDto> saveAll(List<CreateCollectionDto> createCollectionDtos, List<ErrorDto> errors) {
        Set<Long> userIds = createCollectionDtos.stream().map(CreateCollectionDto::getUserId).collect(Collectors.toSet());
        if (userIds.size() != 1) { // I think it makes sense to limit saving to only one user ID so we only need to check if one user exists
            LOG.error("{}saveAll() Attempted to save collections to multiple users with IDs={}", CLASS_LOG, userIds);
            errors.add(new ErrorDto(Errors.SAVE_COLLECTION_MULTIPLE_USERS));
            return null;
        }
        Iterable<CollectionEntity> entities = collectionRepository.saveAll(createCollectionDtos.stream().map(dto ->
                collectionMapper.createDtoToEntity(dto, bookService.getAllBooksById(dto.getBookIds()))).toList()
        );

        List<CollectionDto> dtos = new ArrayList<>();
        entities.forEach(entity -> dtos.add(collectionMapper.entityToDto(entity)));
        LOG.info("{}saveAll() Saved collections with IDs={}", CLASS_LOG, dtos.stream().map(CollectionDto::getId).toList());
        return dtos;
    }

    /**
     * Create a collection, differs from the other save as this one accepts a {@link CollectionDto}
     * @param dto the {@link CollectionDto} to create
     * @return the created collection
     */
    public CollectionDto save(CollectionDto dto, List<ErrorDto> errors) {
        Long userId = dto.getUserId();
        boolean userExists = userService.doesUserExist(userId);
        if (!userExists) {
            LOG.error("{}save(CollectionDto) Attempted to save collections non existent user with ID={}", CLASS_LOG, userId);
            errors.add(new ErrorDto(Errors.USER_DOES_NOT_EXIST));
            return null;
        }
        CollectionEntity entity = collectionRepository.save(collectionMapper.dtoToEntity(dto));
        LOG.info("{}save(CollectionDto) Saved collection with ID={}", CLASS_LOG, entity.getId());
        return collectionMapper.entityToDto(entity);
    }

    /**
     * Find all collections
     * @return {@link List} of all collections
     */
    public List<CollectionDto> findAll() {
        List<CollectionDto> foundCollections = new ArrayList<>();
        collectionRepository.findAll().forEach(
                entity -> foundCollections.add(collectionMapper.entityToDto(entity))
        );
        LOG.info("{}findAll() found {} collections", CLASS_LOG, foundCollections);
        return foundCollections;
    }

    /**
     * Add a book to a collection
     * @param dto contains the book ID and collection ID
     * @return the collection with the newly added book
     */
    public CollectionDto addBooksToCollection(ModifyCollectionDto dto, List<ErrorDto> errors) {
        CollectionDto targetDto = findById(dto.getCollectionId());
        if (targetDto == null) {
            LOG.error("{}addBooksToCollection() Attempted to add books to collection with ID={} but no books were provided", CLASS_LOG, dto.getBookIds());
            errors.add(new ErrorDto(Errors.COLLECTION_DOES_NOT_EXIST));
            return null;
        }
        if (dto.getBookIds() == null || dto.getBookIds().isEmpty()) {
            LOG.error("{}addBooksToCollection() Attempted to add books to collection with ID={} but no collection found", CLASS_LOG, dto.getBookIds());
            errors.add(new ErrorDto(Errors.ADD_BOOKS_COLLECTION_NO_BOOK_IDS));
            return null;
        }

        List<BookDto> books = bookService.getAllBooksById(dto.getBookIds());
        if (books.size() != dto.getBookIds().size()) {
            List<Long> missingIds = dto.getBookIds();
            // remove all ids that were returned by query
            missingIds.removeAll(books.stream().map(BookDto::getId).toList());
            LOG.error("{}addBooksToCollection() attempted to add books with IDs={} but books do not exist", CLASS_LOG, missingIds);
            errors.add(new ErrorDto(String.format(Errors.BOOKS_DO_NOT_EXIST, missingIds)));
            return null;
        }

        List<BookDto> newBooks = Stream.concat(books.stream(), targetDto.getBooks().stream()).toList(); // because the repository returns an immutable list!
        targetDto.setBooks(newBooks);
        CollectionDto newCollection = save(targetDto, new ArrayList<>()); // TODO handle errors from here
        LOG.info("{}addBooksToCollection() Saved books with IDs={} to collection with ID={}", CLASS_LOG, dto.getBookIds(),  newCollection.getId());
        return newCollection;
    }

    public CollectionDto deleteBooksFromCollection(ModifyCollectionDto dto) {
        if (dto.getBookIds() == null || dto.getBookIds().isEmpty()) {
            LOG.error("{}deleteBooksFromCollection() Attempted to delete books to collection with ID={} but request has no IDs", CLASS_LOG, dto.getBookIds());
            throw new RuntimeException("No books ids were given, nothing to delete");
        }
        CollectionDto targetDto = findById(dto.getCollectionId());
        if (targetDto == null) { // TODO more proper error handling
            LOG.error("{}deleteBooksFromCollection() Attempted to delete books from collection with ID={} but no collection found", CLASS_LOG, dto.getBookIds());
            throw new RuntimeException(String.format("Collection with id %s not found", dto.getCollectionId()));
        }
        List<BookDto> books = bookService.getAllBooksById(dto.getBookIds());
        List<BookDto> newBooks = new ArrayList<>(books); // because the repository returns an immutable list!
        newBooks.removeAll(books);
        targetDto.setBooks(newBooks);
        CollectionDto updatedCollection = save(targetDto, new  ArrayList<>()); // TODO handle errors from here
        LOG.info("{}deleteBooksFromCollection() Deleted books with IDs={} from collection with ID={}", CLASS_LOG, dto.getBookIds(),  updatedCollection.getId());
        return updatedCollection;
    }

    /**
     * Deletes collections from a list of IDs; mainly used when deleting a user.
     * @param collections {@link List} of collection IDs
     */
    public void deleteAllById(List<CollectionDto> collections) {
        collectionRepository.deleteAllById(collections.stream().map(CollectionDto::getId).toList());
        LOG.info("{}deleteAllById() Deleted collections with IDs={}", CLASS_LOG, collections.stream().map(CollectionDto::getId).toList());
    }

    /**
     * Delete a collection by ID
     * @param id the ID to delete
     */
    public void deleteById(Long id) {
        CollectionDto dto = findById(id);
        if (dto.getIsDefaultCollection()) { // TODO proper error handling
            LOG.info("{}deleteById() Cannot delete collection with ID={} because it is a default collection", CLASS_LOG, id);
            throw new RuntimeException(String.format("Collection with id %s is a default collection and cannot be deleted", dto.getId()));
        }
        collectionRepository.deleteById(id);
        LOG.info("{}deleteById() Deleted collections with ID={}", CLASS_LOG, id);
    }
}
