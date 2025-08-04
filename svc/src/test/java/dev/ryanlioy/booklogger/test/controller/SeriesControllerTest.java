package dev.ryanlioy.booklogger.test.controller;

import dev.ryanlioy.booklogger.controller.SeriesController;
import dev.ryanlioy.booklogger.dto.CreateSeriesDto;
import dev.ryanlioy.booklogger.dto.SeriesDto;
import dev.ryanlioy.booklogger.meta.EnvelopeDto;
import dev.ryanlioy.booklogger.service.SeriesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SeriesControllerTest {
    @Mock
    private SeriesService seriesService;

    private SeriesController seriesController;

    @BeforeEach
    public void setUp() {
        seriesController = new SeriesController(seriesService);
    }

    @Test
    public void getById() {
        SeriesDto dto = new SeriesDto();
        when(seriesService.findById(any())).thenReturn(dto);
        ResponseEntity<EnvelopeDto<SeriesDto>> response = seriesController.getById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody().getContent());
    }

    @Test
    public void getById_noEntity() {
        when(seriesService.findById(any())).thenReturn(null);
        ResponseEntity<EnvelopeDto<SeriesDto>> response = seriesController.getById(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void createSeries() {
        when(seriesService.create(any(), any())).thenReturn(new SeriesDto());
        ResponseEntity<EnvelopeDto<SeriesDto>> response = seriesController.createSeries(new CreateSeriesDto());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void deleteById() {
        ResponseEntity<EnvelopeDto<SeriesDto>> response = seriesController.deleteById(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
