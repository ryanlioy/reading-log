package dev.ryanlioy.bookloger.service;

import dev.ryanlioy.bookloger.entity.CurrentlyReadingEntity;
import dev.ryanlioy.bookloger.mapper.CurrentlyReadingMapper;
import dev.ryanlioy.bookloger.repository.CurrentlyReadingRepository;
import dev.ryanlioy.bookloger.resource.CurrentlyReadingResource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CurrentlyReadingService {
    private final CurrentlyReadingRepository currentlyReadingRepository;
    private final CurrentlyReadingMapper currentlyReadingMapper;

    public CurrentlyReadingService(CurrentlyReadingRepository currentlyReadingRepository,  CurrentlyReadingMapper currentlyReadingMapper) {
        this.currentlyReadingRepository = currentlyReadingRepository;
        this.currentlyReadingMapper = currentlyReadingMapper;
    }

    public CurrentlyReadingResource findById(Long id) {
        CurrentlyReadingEntity entity = currentlyReadingRepository.findById(id).orElse(null);
        if (entity == null) {
            return null;
        }
        return currentlyReadingMapper.entityToResource(entity);
    }

    public CurrentlyReadingResource create(CurrentlyReadingResource resource) {
        return currentlyReadingMapper.entityToResource(currentlyReadingRepository.save(currentlyReadingMapper.resourceToEntity(resource)));
    }

    public List<CurrentlyReadingResource> findAll() {
        List<CurrentlyReadingResource> currentlyReadingResources = new ArrayList<>();
        currentlyReadingRepository.findAll().forEach(entity ->
            currentlyReadingResources.add(currentlyReadingMapper.entityToResource(entity))
        );
        return currentlyReadingResources;
    }

    public void deleteById(Long id) {
        currentlyReadingRepository.deleteById(id);
    }
}
