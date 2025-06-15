package dev.ryanlioy.bookloger.service;

import dev.ryanlioy.bookloger.entity.EntryEntity;
import dev.ryanlioy.bookloger.mapper.EntryMapper;
import dev.ryanlioy.bookloger.repository.EntryRepository;
import dev.ryanlioy.bookloger.resource.EntryResource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EntryService {
    private final EntryRepository entryRepository;
    private final EntryMapper entryMapper;

    public EntryService(EntryRepository entryRepository, EntryMapper entryMapper) {
        this.entryRepository = entryRepository;
        this.entryMapper = entryMapper;
    }

    public EntryResource createEntry(EntryResource entryResource) {
        return entryMapper.entityToResource(entryRepository.save(entryMapper.resourceToEntity(entryResource)));
    }

    public List<EntryResource> getEntryByBookIdAndUserId(Long bookId, Long userId) {
        List<EntryEntity> entities = entryRepository.findAllByUserIdAndBookId(bookId, userId);

        List<EntryResource> resources = new ArrayList<>();
        for(EntryEntity entity : entities) {
            resources.add(entryMapper.entityToResource(entity));
        }

        return resources;
    }

    public Optional<EntryEntity> getEntryById(Long entryId) {
        return entryRepository.findById(entryId);
    }

    public void deleteEntryById(Long entryId) {
        entryRepository.deleteById(entryId);
    }
}
