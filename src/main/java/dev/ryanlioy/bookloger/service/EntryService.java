package dev.ryanlioy.bookloger.service;

import dev.ryanlioy.bookloger.entity.EntryEntity;
import dev.ryanlioy.bookloger.mapper.EntryMapper;
import dev.ryanlioy.bookloger.repository.EntryRepository;
import dev.ryanlioy.bookloger.dto.EntryDto;
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

    /**
     * Create an entry
     * @param entryDto the entry to create
     * @return the created enty
     */
    public EntryDto createEntry(EntryDto entryDto) {
        return entryMapper.entityToDto(entryRepository.save(entryMapper.dtoToEntity(entryDto)));
    }

    /**
     * Get entries by book ID and user ID
     * @param bookId the book ID
     * @param userId the user ID
     * @return {@link List} of entires
     */
    public List<EntryDto> getEntryByBookIdAndUserId(Long bookId, Long userId) {
        List<EntryEntity> entities = entryRepository.findAllByUserIdAndBookId(bookId, userId);

        List<EntryDto> dtos = new ArrayList<>();
        for(EntryEntity entity : entities) {
            dtos.add(entryMapper.entityToDto(entity));
        }

        return dtos;
    }

    /**
     * Get an entry by ID
     * @param entryId the ID of the entry
     * @return {@link Optional} that may contain the list
     */
    public EntryDto getEntryById(Long entryId) {
        Optional<EntryEntity> optional = entryRepository.findById(entryId);
        return optional.map(entryMapper::entityToDto).orElse(null);
    }

    /**
     * Delete an entry
     * @param entryId ID of entry to delete
     */
    public void deleteEntryById(Long entryId) {
        entryRepository.deleteById(entryId);
    }
}
