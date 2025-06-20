package dev.ryanlioy.bookloger.controller;

import dev.ryanlioy.bookloger.entity.EntryEntity;
import dev.ryanlioy.bookloger.mapper.EntryMapper;
import dev.ryanlioy.bookloger.resource.EntryResource;
import dev.ryanlioy.bookloger.service.EntryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/entry")
public class EntryController {
    private final EntryService entryService;
    private final EntryMapper entryMapper;

    public EntryController(EntryService entryService, EntryMapper entryMapper) {
        this.entryService = entryService;
        this.entryMapper = entryMapper;
    }

    @PostMapping
    public ResponseEntity<EntryResource> createEntry(@RequestBody EntryResource entryResource) {
        var a = entryService.createEntry(entryResource);
        return new  ResponseEntity<>(a, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EntryResource>> getEntriesByUserIdAndBookId(@RequestParam Long userId, @RequestParam Long bookId) {
        return new ResponseEntity<>(entryService.getEntryByBookIdAndUserId(bookId, userId),  HttpStatus.OK);
    }

    @GetMapping("/{entryId}")
    public ResponseEntity<EntryResource> getEntryById(@PathVariable Long entryId) {
        Optional<EntryEntity> entryEntity = entryService.getEntryById(entryId);
        EntryResource entryResource = null;
        HttpStatus status = HttpStatus.NO_CONTENT;
        if (entryEntity.isPresent()) {
            entryResource = entryMapper.entityToResource(entryEntity.get());
            status = HttpStatus.OK;
        }
        return new  ResponseEntity<>(entryResource, status);
    }

    @DeleteMapping("/{entryId}")
    public ResponseEntity<EntryResource> deleteEntryById(@PathVariable Long entryId) {
        entryService.deleteEntryById(entryId);
        return new  ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
