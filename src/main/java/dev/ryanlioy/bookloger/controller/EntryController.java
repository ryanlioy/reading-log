package dev.ryanlioy.bookloger.controller;

import dev.ryanlioy.bookloger.dto.EntryDto;
import dev.ryanlioy.bookloger.dto.meta.EnvelopeDto;
import dev.ryanlioy.bookloger.dto.meta.ErrorDto;
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

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/entry")
public class EntryController {
    private final EntryService entryService;

    public EntryController(EntryService entryService) {
        this.entryService = entryService;
    }

    /**
     * Creates an entry
     * @param entryDto the request
     * @return the created entry
     */
    @PostMapping
    public ResponseEntity<EnvelopeDto<EntryDto>> createEntry(@RequestBody EntryDto entryDto) {
        List<ErrorDto> errors = new ArrayList<>();
        EntryDto dto = entryService.createEntry(entryDto, errors);
        ResponseEntity<EnvelopeDto<EntryDto>> response = new ResponseEntity<>(new EnvelopeDto<>(), HttpStatus.CREATED);
        if (!errors.isEmpty()) {
            response = new ResponseEntity<>(new EnvelopeDto<>(errors), HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    /**
     * Get an entry by user ID and book ID
     * @param userId the user ID
     * @param bookId the book ID
     * @return {@link List} of the entries
     */
    @GetMapping
    public ResponseEntity<EnvelopeDto<List<EntryDto>>> getEntriesByUserIdAndBookId(@RequestParam Long userId, @RequestParam Long bookId) {
        return new ResponseEntity<>(new EnvelopeDto<>(entryService.getEntryByBookIdAndUserId(bookId, userId)),  HttpStatus.OK);
    }

    /**
     * Get an entry by ID
     * @param entryId the entry ID
     * @return a body containing the specified ID, null if it does not exist
     */
    @GetMapping("/{entryId}")
    public ResponseEntity<EnvelopeDto<EntryDto>> getEntryById(@PathVariable Long entryId) {
        EntryDto entry = entryService.getEntryById(entryId);
        HttpStatus status = HttpStatus.NO_CONTENT;
        if (entry != null) {
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(new EnvelopeDto<>(entry), status);
    }

    /**
     * Delete an entry by ID
     * @param entryId he ID to delete
     * @return 204 with no body
     */
    @DeleteMapping("/{entryId}")
    public ResponseEntity<EnvelopeDto<EntryDto>> deleteEntryById(@PathVariable Long entryId) {
        entryService.deleteEntryById(entryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
