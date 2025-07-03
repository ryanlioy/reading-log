package dev.ryanlioy.bookloger.controller;

import dev.ryanlioy.bookloger.dto.EntryDto;
import dev.ryanlioy.bookloger.dto.meta.EnvelopeDto;
import dev.ryanlioy.bookloger.entity.EntryEntity;
import dev.ryanlioy.bookloger.mapper.EntryMapper;
import dev.ryanlioy.bookloger.service.EntryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public ResponseEntity<EnvelopeDto<EntryDto>> createEntry(@RequestBody EntryDto entryDto) {
        return new ResponseEntity<>(new EnvelopeDto<>(entryService.createEntry(entryDto)), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<EnvelopeDto<List<EntryDto>>> getEntriesByUserIdAndBookId(@RequestParam Long userId, @RequestParam Long bookId) {
        return new ResponseEntity<>(new EnvelopeDto<>(entryService.getEntryByBookIdAndUserId(bookId, userId)),  HttpStatus.OK);
    }

    @GetMapping("/{entryId}")
    public ResponseEntity<EnvelopeDto<EntryDto>> getEntryById(@PathVariable Long entryId) {
        Optional<EntryEntity> entryEntity = entryService.getEntryById(entryId);
        EntryDto entryDto = null;
        HttpStatus status = HttpStatus.NO_CONTENT;
        if (entryEntity.isPresent()) {
            entryDto = entryMapper.entityToResource(entryEntity.get());
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(new EnvelopeDto<>(entryDto), status);
    }

    @DeleteMapping("/{entryId}")
    public ResponseEntity<EnvelopeDto<EntryDto>> deleteEntryById(@PathVariable Long entryId) {
        entryService.deleteEntryById(entryId);
        return new  ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
