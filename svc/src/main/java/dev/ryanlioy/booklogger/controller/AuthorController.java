package dev.ryanlioy.booklogger.controller;

import dev.ryanlioy.booklogger.dto.AuthorDto;
import dev.ryanlioy.booklogger.dto.CreateAuthorDto;
import dev.ryanlioy.booklogger.meta.EnvelopeDto;
import dev.ryanlioy.booklogger.meta.ErrorDto;
import dev.ryanlioy.booklogger.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/author")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    /**
     * Get an author by ID
     * @param id the author ID
     * @return the author DTO, no body if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<EnvelopeDto<AuthorDto>> getAuthor(@PathVariable Long id) {
        AuthorDto dto = authorService.findAuthorById(id);
        ResponseEntity<EnvelopeDto<AuthorDto>> response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        if (dto != null) {
            response = new ResponseEntity<>(new EnvelopeDto<>(dto), HttpStatus.OK);
        }
        return response;
    }

    /**
     * Get authors of a book by book ID
     * @param id the book ID
     * @return {@link List} of authors, empty list if none are found
     */
    @GetMapping("/book/{id}")
    public ResponseEntity<EnvelopeDto<List<AuthorDto>>> getAuthorsByBookId(@PathVariable Long id) {
        return new ResponseEntity<>(new EnvelopeDto<>(authorService.getAuthorsByBookId(id)), HttpStatus.OK);
    }

    /**
     * Create an author
     * @param body {@link CreateAuthorDto}request body
     * @return the created author
     */
    @PostMapping
    public ResponseEntity<EnvelopeDto<AuthorDto>> createAuthor(@RequestBody CreateAuthorDto body) {
        List<ErrorDto> errors = new ArrayList<>();
        AuthorDto dto = authorService.createAuthor(body, errors);
        ResponseEntity<EnvelopeDto<AuthorDto>> response = new ResponseEntity<>(new EnvelopeDto<>(dto), HttpStatus.CREATED);
        if (!errors.isEmpty()) {
            response = new ResponseEntity<>(new EnvelopeDto<>(errors), HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    /**
     * Deletes an author by ID
     * @param id the ID to delete
     * @return no body
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<EnvelopeDto<AuthorDto>> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthorById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
