package dev.ryanlioy.booklogger.mapper;

import dev.ryanlioy.booklogger.entity.EntryEntity;
import dev.ryanlioy.booklogger.dto.EntryDto;
import org.springframework.stereotype.Component;

@Component
public class EntryMapper {
    public EntryEntity dtoToEntity(EntryDto entryDto) {
        EntryEntity entryEntity = new EntryEntity();
        entryEntity.setId(entryDto.getId());
        entryEntity.setUserId(entryDto.getUserId());
        entryEntity.setDescription(entryDto.getDescription());
        entryEntity.setBookId(entryDto.getBookId());
        entryEntity.setCreationDate(entryDto.getCreationDate());

        return entryEntity;
    }

    public EntryDto entityToDto(EntryEntity entryEntity) {
        EntryDto entryDto = new EntryDto();
        entryDto.setId(entryEntity.getId());
        entryDto.setUserId(entryEntity.getUserId());
        entryDto.setBookId(entryEntity.getBookId());
        entryDto.setDescription(entryEntity.getDescription());
        entryDto.setCreationDate(entryEntity.getCreationDate());

        return entryDto;
    }
}
