package dev.ryanlioy.bookloger.controller;

import dev.ryanlioy.bookloger.dto.CollectionItemDto;
import dev.ryanlioy.bookloger.service.CollectionItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/collection")
public class CollectionItemController {
    private final CollectionItemService collectionItemService;

    public CollectionItemController(CollectionItemService collectionItemService) {
        this.collectionItemService = collectionItemService;
    }

    @GetMapping("/{type}/user/{userId}")
    public ResponseEntity<List<CollectionItemDto>> getCollectionItemsByUserIdAndType(@PathVariable String type, @PathVariable Long userId) {
        List<CollectionItemDto> resources = collectionItemService.findAllByUserIdAndType(userId, type);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CollectionItemDto> create(@RequestBody CollectionItemDto resource) {
        return new ResponseEntity<>(collectionItemService.create(resource),  HttpStatus.CREATED) ;
    }

    @GetMapping
    public ResponseEntity<List<CollectionItemDto>> getAllCollectionItems() {
        return new ResponseEntity<>(collectionItemService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CollectionItemDto> deleteById(@PathVariable Long id){
        collectionItemService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
