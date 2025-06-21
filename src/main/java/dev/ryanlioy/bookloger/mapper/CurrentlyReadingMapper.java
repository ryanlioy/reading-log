package dev.ryanlioy.bookloger.mapper;

import dev.ryanlioy.bookloger.entity.CurrentlyReadingEntity;
import dev.ryanlioy.bookloger.resource.CurrentlyReadingResource;
import org.springframework.stereotype.Component;

@Component
public class CurrentlyReadingMapper {
    public CurrentlyReadingEntity resourceToEntity(CurrentlyReadingResource resource) {
        CurrentlyReadingEntity entity = new CurrentlyReadingEntity();
        entity.setId(resource.getId());
        entity.setUserId(resource.getUserId());
        entity.setBookId(resource.getBookId());

        return entity;
    }

    public CurrentlyReadingResource entityToResource(CurrentlyReadingEntity entity) {
        CurrentlyReadingResource resource = new CurrentlyReadingResource();
        resource.setId(entity.getId());
        resource.setUserId(entity.getUserId());
        resource.setBookId(entity.getBookId());

        return resource;
    }
}
