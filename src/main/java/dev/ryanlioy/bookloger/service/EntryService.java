package dev.ryanlioy.bookloger.service;

import dev.ryanlioy.bookloger.dto.EntryDto;
import dev.ryanlioy.bookloger.entity.EntryEntity;
import dev.ryanlioy.bookloger.mapper.EntryMapper;
import dev.ryanlioy.bookloger.repository.EntryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EntryService {
    private static final Logger LOG = LoggerFactory.getLogger(EntryService.class);
    private static final String CLASS_LOG = "EntryService::";
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
        EntryEntity entity = entryRepository.save(entryMapper.dtoToEntity(entryDto));
        LOG.info("{}createEntry() created entry with ID={}", CLASS_LOG, entryDto.getId());
        return entryMapper.entityToDto(entity);
    }

    /**
     * Get entries by book ID and user ID
     * @param bookId the book ID
     * @param userId the user ID
     * @return {@link List} of entries
     */
    public List<EntryDto> getEntryByBookIdAndUserId(Long bookId, Long userId) {
        List<EntryEntity> entities = entryRepository.findAllByUserIdAndBookId(bookId, userId);

        List<EntryDto> dtos = new ArrayList<>();
        for(EntryEntity entity : entities) {
            dtos.add(entryMapper.entityToDto(entity));
        }
        LOG.info("{}getEntryByBookIdAndUserId() found {} entries for book ID={} and user ID={}", CLASS_LOG, dtos.size(),  bookId, userId);
        return dtos;
    }

    /**
     * Get an entry by ID
     * @param entryId the ID of the entry
     * @return {@link Optional} that may contain the list
     */
    public EntryDto getEntryById(Long entryId) {
        Optional<EntryEntity> optional = entryRepository.findById(entryId);
        LOG.info("{}getEntryById() entry with ID={} " + (optional.isPresent() ? "found" : "not found"), CLASS_LOG, entryId);
        return optional.map(entryMapper::entityToDto).orElse(null);
    }

    /**
     * Delete an entry
     * @param entryId ID of entry to delete
     */
    public void deleteEntryById(Long entryId) {
        entryRepository.deleteById(entryId);
        LOG.info("{}deleteEntryById() deleted entry with ID={}", CLASS_LOG, entryId);
    }
}
