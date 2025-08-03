package dev.ryanlioy.bookloger.controller;

import dev.ryanlioy.bookloger.dto.CreateSeriesDto;
import dev.ryanlioy.bookloger.dto.SeriesDto;
import dev.ryanlioy.bookloger.dto.meta.EnvelopeDto;
import dev.ryanlioy.bookloger.dto.meta.ErrorDto;
import dev.ryanlioy.bookloger.service.SeriesService;
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
@RequestMapping("/series")
public class SeriesController {
    private final SeriesService seriesService;

    public SeriesController(SeriesService seriesService) {
        this.seriesService = seriesService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnvelopeDto<SeriesDto>> getById(@PathVariable Long id) {
        SeriesDto dto = seriesService.findById(id);
        ResponseEntity<EnvelopeDto<SeriesDto>> response = new ResponseEntity<>(new EnvelopeDto<>(dto), HttpStatus.OK);
        if (dto == null) {
            response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return response;
    }

    @PostMapping
    public ResponseEntity<EnvelopeDto<SeriesDto>> createSeries(@RequestBody CreateSeriesDto createSeriesDto) {
        List<ErrorDto> errors = new ArrayList<>();
        SeriesDto dto = seriesService.create(createSeriesDto, errors);
        ResponseEntity<EnvelopeDto<SeriesDto>> response = new ResponseEntity<>(new EnvelopeDto<>(dto), HttpStatus.CREATED);
        if (!errors.isEmpty()) {
            return new ResponseEntity<>(new EnvelopeDto<>(errors), HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EnvelopeDto<SeriesDto>> deleteById(@PathVariable Long id) {
        seriesService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
