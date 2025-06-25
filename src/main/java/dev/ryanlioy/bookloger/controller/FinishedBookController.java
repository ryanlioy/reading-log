package dev.ryanlioy.bookloger.controller;

import dev.ryanlioy.bookloger.dto.FinishedBookDto;
import dev.ryanlioy.bookloger.service.FinishedBookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/finishedBook")
public class FinishedBookController {
    private final FinishedBookService finishedBookService;

    public FinishedBookController(FinishedBookService finishedBookService) {
        this.finishedBookService = finishedBookService;
    }

    @PostMapping
    public ResponseEntity<FinishedBookDto> createFinishedReading(@RequestBody FinishedBookDto dto) {
        return new ResponseEntity<>(finishedBookService.create(dto), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FinishedBookDto> getFinishedReadingBiId(@PathVariable Long id) {
        FinishedBookDto dto = finishedBookService.getById(id);
        if (dto == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<FinishedBookDto>> getFinishedReadingsByUserId(@PathVariable Long id) {
        return new ResponseEntity<>(finishedBookService.getAllFinishedBooksByUserId(id),  HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FinishedBookDto> deleteFinishedReading(@PathVariable Long id) {
        finishedBookService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
