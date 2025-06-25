package dev.ryanlioy.bookloger.service;

import dev.ryanlioy.bookloger.dto.FinishedBookDto;
import dev.ryanlioy.bookloger.entity.FinishedBookEntity;
import dev.ryanlioy.bookloger.mapper.FinishedBookMapper;
import dev.ryanlioy.bookloger.repository.FinishedBookRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FinishedBookService {
    private FinishedBookRepository finishedBookRepository;
    private FinishedBookMapper finishedBookMapper;

    public FinishedBookService(FinishedBookRepository finishedBookRepository,  FinishedBookMapper finishedBookMapper) {
        this.finishedBookRepository = finishedBookRepository;
        this.finishedBookMapper = finishedBookMapper;
    }

    public FinishedBookDto create(FinishedBookDto dto) {
        return finishedBookMapper.entityToDto(finishedBookRepository.save(finishedBookMapper.dtoToEntity(dto)));
    }

    public FinishedBookDto getById(Long id) {
        Optional<FinishedBookEntity> optionalFinishedBookEntity = finishedBookRepository.findById(id);
        FinishedBookDto dto = null;
        if (optionalFinishedBookEntity.isPresent()) {
            dto = finishedBookMapper.entityToDto(optionalFinishedBookEntity.get());
        }
        return dto;
    }

    public void deleteById(Long id) {
        finishedBookRepository.deleteById(id);
    }

    public List<FinishedBookDto> getAllFinishedBooksByUserId(Long userId) {
        Iterable<FinishedBookEntity> entities = finishedBookRepository.findAll();
        List<FinishedBookDto> dtos = new ArrayList<>();
        entities.forEach(entity ->
            dtos.add(finishedBookMapper.entityToDto(entity))
        );
        return dtos;
    }
}
