package dev.ryanlioy.bookloger.controller;

import dev.ryanlioy.bookloger.resource.CurrentlyReadingResource;
import dev.ryanlioy.bookloger.service.CurrentlyReadingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/currentlyReading")
public class CurrentlyReadingController {
    private final CurrentlyReadingService currentlyReadingService;

    public CurrentlyReadingController(CurrentlyReadingService currentlyReadingService) {
        this.currentlyReadingService = currentlyReadingService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CurrentlyReadingResource> getCurrentlyReading(@PathVariable Long id) {
        CurrentlyReadingResource resource = currentlyReadingService.findById(id);
        ResponseEntity<CurrentlyReadingResource> response;
        if(resource == null) {
            response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            response = new ResponseEntity<>(resource, HttpStatus.OK);
        }
        return response;
    }

    @PostMapping
    public ResponseEntity<CurrentlyReadingResource> create(@RequestBody CurrentlyReadingResource resource) {
        return new ResponseEntity<>(currentlyReadingService.create(resource),  HttpStatus.CREATED) ;
    }

    @GetMapping
    public ResponseEntity<List<CurrentlyReadingResource>> getAllCurrentlyReadings() {
        return new ResponseEntity<>(currentlyReadingService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CurrentlyReadingResource> deleteById(@PathVariable Long id){
        currentlyReadingService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
