package dev.ryanlioy.bookloger.service;

import dev.ryanlioy.bookloger.entity.CurrentlyReadingEntity;
import dev.ryanlioy.bookloger.mapper.CurrentlyReadingMapper;
import dev.ryanlioy.bookloger.repository.CurrentlyReadingRepository;
import dev.ryanlioy.bookloger.dto.CurrentlyReadingDto;
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

    public CurrentlyReadingDto findById(Long id) {
        CurrentlyReadingEntity entity = currentlyReadingRepository.findById(id).orElse(null);
        if (entity == null) {
            return null;
        }
        return currentlyReadingMapper.entityToResource(entity);
    }

    public CurrentlyReadingDto create(CurrentlyReadingDto resource) {
        return currentlyReadingMapper.entityToResource(currentlyReadingRepository.save(currentlyReadingMapper.resourceToEntity(resource)));
    }

    public List<CurrentlyReadingDto> findAll() {
        List<CurrentlyReadingDto> currentlyReadingDtos = new ArrayList<>();
        currentlyReadingRepository.findAll().forEach(entity ->
            currentlyReadingDtos.add(currentlyReadingMapper.entityToResource(entity))
        );
        return currentlyReadingDtos;
    }

    public void deleteById(Long id) {
        currentlyReadingRepository.deleteById(id);
    }
}
