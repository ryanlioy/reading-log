package dev.ryanlioy.bookloger.mapper;

import dev.ryanlioy.bookloger.entity.EntryEntity;
import dev.ryanlioy.bookloger.resource.EntryResource;
import org.springframework.stereotype.Component;

@Component
public class EntryMapper {
    public EntryEntity resourceToEntity(EntryResource entryResource) {
        EntryEntity entryEntity = new EntryEntity();
        entryEntity.setId(entryResource.getId());
        entryEntity.setUserId(entryResource.getUserId());
        entryEntity.setDescription(entryResource.getDescription());
        entryEntity.setBookId(entryResource.getBookId());

        return entryEntity;
    }

    public EntryResource entityToResource(EntryEntity entryEntity) {
        EntryResource entryResource = new EntryResource();
        entryResource.setId(entryEntity.getId());
        entryResource.setUserId(entryEntity.getUserId());
        entryResource.setBookId(entryEntity.getBookId());
        entryResource.setDescription(entryEntity.getDescription());

        return entryResource;
    }
}
