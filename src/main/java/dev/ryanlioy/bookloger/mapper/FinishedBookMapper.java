package dev.ryanlioy.bookloger.mapper;

import dev.ryanlioy.bookloger.dto.FinishedBookDto;
import dev.ryanlioy.bookloger.entity.FinishedBookEntity;
import org.springframework.stereotype.Component;

@Component
public class FinishedBookMapper {
    public FinishedBookEntity dtoToEntity(FinishedBookDto dto) {
        FinishedBookEntity entity = new FinishedBookEntity();
        entity.setId(dto.getId());
        entity.setBookId(dto.getBookId());
        entity.setUserId(dto.getUserId());

        return entity;
    }

    public FinishedBookDto entityToDto(FinishedBookEntity entity) {
        FinishedBookDto dto = new FinishedBookDto();
        dto.setId(entity.getId());
        dto.setBookId(entity.getBookId());
        dto.setUserId(entity.getUserId());

        return dto;
    }
}
