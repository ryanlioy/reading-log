package dev.ryanlioy.bookloger.controller;

import dev.ryanlioy.bookloger.dto.GenreDto;
import dev.ryanlioy.bookloger.service.GenreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/genre")
public class GenreController {
    private GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public ResponseEntity<List<GenreDto>> getAllGenres() {
        return new ResponseEntity<>(genreService.getAllGenres(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreDto> getGenreById(@PathVariable Long id) {
        GenreDto dto = genreService.getGenre(id);
        if (dto == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GenreDto> createGenre(@RequestBody GenreDto genre) {
        return new ResponseEntity<>(genreService.createGenre(genre),  HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenreDto> deleteGenre(@PathVariable Long id) {
        genreService.deleteGenre(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<GenreDto>> getGenesByBookId(@PathVariable Long bookId) {
        return new ResponseEntity<>(genreService.getAllGenresByBookId(bookId), HttpStatus.OK) ;
    }
}
