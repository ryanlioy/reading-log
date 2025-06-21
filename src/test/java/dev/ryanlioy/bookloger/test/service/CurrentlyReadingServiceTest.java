package dev.ryanlioy.bookloger.test.service;

import dev.ryanlioy.bookloger.entity.CurrentlyReadingEntity;
import dev.ryanlioy.bookloger.mapper.CurrentlyReadingMapper;
import dev.ryanlioy.bookloger.repository.CurrentlyReadingRepository;
import dev.ryanlioy.bookloger.resource.CurrentlyReadingResource;
import dev.ryanlioy.bookloger.service.CurrentlyReadingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CurrentlyReadingServiceTest {
    @Mock
    private CurrentlyReadingRepository currentlyReadingRepository;

    @Mock
    private CurrentlyReadingMapper currentlyReadingMapper;

    private CurrentlyReadingService currentlyReadingService;

    @BeforeEach
    public void setUp() {
        currentlyReadingService = new CurrentlyReadingService(currentlyReadingRepository, currentlyReadingMapper);
    }

    @Test
    public void findById_whenFound_returnResource() {
        when(currentlyReadingRepository.findById(any())).thenReturn(Optional.of(new CurrentlyReadingEntity()));
        CurrentlyReadingResource resource = new CurrentlyReadingResource(1L);
        when(currentlyReadingMapper.entityToResource(any())).thenReturn(resource);
        CurrentlyReadingResource response = currentlyReadingService.findById(1L);

        Assertions.assertEquals(resource, response);
    }

    @Test
    public void findById_whenNotFound_returnNull() {
        when(currentlyReadingRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertNull(currentlyReadingService.findById(1L));
    }

    @Test
    public void create_returnResource() {
        when(currentlyReadingRepository.save(any())).thenReturn(new CurrentlyReadingEntity());
        when(currentlyReadingMapper.entityToResource(any())).thenReturn(new CurrentlyReadingResource(1L));

        CurrentlyReadingResource response = currentlyReadingService.create(new CurrentlyReadingResource());

        Assertions.assertNotNull(response.getId());
    }

    @Test
    public void findAll_whenFound_returnNonEmptyList() {
        when(currentlyReadingRepository.findAll()).thenReturn(List.of(new CurrentlyReadingEntity()));
        when(currentlyReadingMapper.entityToResource(any())).thenReturn(new CurrentlyReadingResource());

        List<CurrentlyReadingResource> response = currentlyReadingService.findAll();
        Assertions.assertFalse(response.isEmpty());
    }

    @Test
    public void findAll_whenNotFound_returnEmptyList() {
        when(currentlyReadingRepository.findAll()).thenReturn(new ArrayList<>());

        List<CurrentlyReadingResource> response = currentlyReadingService.findAll();
        Assertions.assertTrue(response.isEmpty());
    }

    @Test
    public void deleteById_deleteEntity() {
        currentlyReadingService.deleteById(1L);

        verify(currentlyReadingRepository, times(1)).deleteById(1L);
    }
}
