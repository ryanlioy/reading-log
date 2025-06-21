package dev.ryanlioy.bookloger.mapper;

import dev.ryanlioy.bookloger.entity.CurrentlyReadingEntity;
import dev.ryanlioy.bookloger.dto.CurrentlyReadingDto;
import org.springframework.stereotype.Component;

@Component
public class CurrentlyReadingMapper {
    public CurrentlyReadingEntity resourceToEntity(CurrentlyReadingDto resource) {
        CurrentlyReadingEntity entity = new CurrentlyReadingEntity();
        entity.setId(resource.getId());
        entity.setUserId(resource.getUserId());
        entity.setBookId(resource.getBookId());

        return entity;
    }

    public CurrentlyReadingDto entityToResource(CurrentlyReadingEntity entity) {
        CurrentlyReadingDto resource = new CurrentlyReadingDto();
        resource.setId(entity.getId());
        resource.setUserId(entity.getUserId());
        resource.setBookId(entity.getBookId());

        return resource;
    }
}
