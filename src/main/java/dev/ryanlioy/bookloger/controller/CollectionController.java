package dev.ryanlioy.bookloger.controller;

import dev.ryanlioy.bookloger.dto.CollectionDto;
import dev.ryanlioy.bookloger.dto.CreateCollectionDto;
import dev.ryanlioy.bookloger.dto.ModifyCollectionDto;
import dev.ryanlioy.bookloger.dto.meta.EnvelopeDto;
import dev.ryanlioy.bookloger.service.CollectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/collection")
public class CollectionController {
    private final CollectionService collectionService;

    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    /**
     * Gets all collections by user ID
     * @param userId user ID to find collection for
     * @return {@link List} of {@link CollectionDto}
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<EnvelopeDto<List<CollectionDto>>> getCollectionItemsByUserId(@PathVariable Long userId) {
        List<CollectionDto> dtos = collectionService.findAllByUserId(userId);
        return new ResponseEntity<>(new EnvelopeDto<>(dtos), HttpStatus.OK);
    }

    /**
     * Creates a {@link CollectionDto}
     * @param resource the request
     * @return the created {@link CollectionDto}
     */
    @PostMapping
    public ResponseEntity<EnvelopeDto<CollectionDto>> create(@RequestBody CreateCollectionDto resource) {
        return new ResponseEntity<>(new EnvelopeDto<>(collectionService.save(resource)),  HttpStatus.CREATED);
    }

    /**
     * Adds a book to a collection
     * @param dto request containing the book ID and collection ID
     * @return the collection with the added book
     */
    @PostMapping("/add")
    public ResponseEntity<EnvelopeDto<CollectionDto>> addBooksToCollection(@RequestBody ModifyCollectionDto dto) {
        return new ResponseEntity<>(new EnvelopeDto<>(collectionService.addBooksToCollection(dto)),  HttpStatus.OK);
    }

    @PostMapping("/remove")
    public ResponseEntity<EnvelopeDto<CollectionDto>> removeBooksFromCollection(@RequestBody ModifyCollectionDto dto) {
        return new ResponseEntity<>(new EnvelopeDto<>(collectionService.deleteBooksFromCollection(dto)), HttpStatus.OK);
    }

    /**
     * Gets all collections
     * @return a {@link List} of all collections
     */
    @GetMapping
    public ResponseEntity<EnvelopeDto<List<CollectionDto>>> getAllCollectionItems() {
        return new ResponseEntity<>(new EnvelopeDto<>(collectionService.findAll()), HttpStatus.OK);
    }

    /**
     * Delete a collection
     * @param id the collection ID
     * @return 204 with no body
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<EnvelopeDto<CollectionDto>> deleteById(@PathVariable Long id){
        collectionService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
