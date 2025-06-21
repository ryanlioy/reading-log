package dev.ryanlioy.bookloger.test.controller;

import dev.ryanlioy.bookloger.controller.CurrentlyReadingController;
import dev.ryanlioy.bookloger.dto.CurrentlyReadingDto;
import dev.ryanlioy.bookloger.service.CurrentlyReadingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CurrentlyReadingControllerTest {
    @Mock
    private CurrentlyReadingService currentlyReadingService;

    private CurrentlyReadingController currentlyReadingController;

    @BeforeEach
    public void setUp() {
        currentlyReadingController = new CurrentlyReadingController(currentlyReadingService);
    }

    @Test
    public void getCurrentlyReadingById_whenEntityExists_returnResourceAnd200() {
        when(currentlyReadingService.findById(any())).thenReturn(new CurrentlyReadingDto(1L));
        ResponseEntity<CurrentlyReadingDto> response = currentlyReadingController.getCurrentlyReading(1L);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(1L, response.getBody().getId());
    }

    @Test
    public void getCurrentlyReadingById_whenEntityDoesNotExist_returnNoContent() {
        when(currentlyReadingService.findById(any())).thenReturn(null);
        ResponseEntity<CurrentlyReadingDto> response = currentlyReadingController.getCurrentlyReading(1L);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Assertions.assertNull(response.getBody());
    }

    @Test
    public void createCurrentlyReading_returnResourceAnd200() {
        when(currentlyReadingService.create(any())).thenReturn(new CurrentlyReadingDto(1L));
        CurrentlyReadingDto resource = new CurrentlyReadingDto();
        ResponseEntity<CurrentlyReadingDto> response = currentlyReadingController.create(resource);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(1L, response.getBody().getId());
    }

    @Test
    public void createCurrentlyReading_whenEntityExists_returnResourceAnd200() {
        currentlyReadingController.deleteById(1L);

        verify(currentlyReadingService, times(1)).deleteById(any());
    }
}
