package dev.ryanlioy.bookloger.controller;

import dev.ryanlioy.bookloger.dto.CollectionDto;
import dev.ryanlioy.bookloger.dto.CreateCollectionDto;
import dev.ryanlioy.bookloger.service.CollectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/collection")
public class CollectionController {
    private final CollectionService collectionService;

    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CollectionDto>> getCollectionItemsByUserId(@PathVariable Long userId) {
        List<CollectionDto> dtos = collectionService.findAllByUserId(userId);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CollectionDto> create(@RequestBody CreateCollectionDto resource) {
        return new ResponseEntity<>(collectionService.create(resource),  HttpStatus.CREATED) ;
    }

    @GetMapping
    public ResponseEntity<List<CollectionDto>> getAllCollectionItems() {
        return new ResponseEntity<>(collectionService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CollectionDto> deleteById(@PathVariable Long id){
        collectionService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
