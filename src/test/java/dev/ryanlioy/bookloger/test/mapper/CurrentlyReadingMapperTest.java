package dev.ryanlioy.bookloger.test.mapper;

import dev.ryanlioy.bookloger.entity.CurrentlyReadingEntity;
import dev.ryanlioy.bookloger.mapper.CurrentlyReadingMapper;
import dev.ryanlioy.bookloger.resource.CurrentlyReadingResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CurrentlyReadingMapperTest {
    CurrentlyReadingMapper currentlyReadingMapper;

    @BeforeEach
    public void setUp() {
        currentlyReadingMapper = new CurrentlyReadingMapper();
    }

    @Test
    public void resourceToEntity() {
        CurrentlyReadingResource resource = new CurrentlyReadingResource();
        resource.setBookId(1L);
        resource.setUserId(2L);
        resource.setId(3L);

        CurrentlyReadingEntity entity = currentlyReadingMapper.resourceToEntity(resource);
        assertThat(entity.getId()).isEqualTo(resource.getId());
        assertThat(entity.getUserId()).isEqualTo(resource.getUserId());
        assertThat(entity.getBookId()).isEqualTo(resource.getBookId());
    }

    @Test
    public void entityToResource() {
        CurrentlyReadingEntity entity = new CurrentlyReadingEntity();
        entity.setBookId(1L);
        entity.setUserId(2L);
        entity.setId(3L);

        CurrentlyReadingResource resource = currentlyReadingMapper.entityToResource(entity);
        assertThat(resource.getId()).isEqualTo(entity.getId());
        assertThat(resource.getUserId()).isEqualTo(entity.getUserId());
        assertThat(resource.getBookId()).isEqualTo(entity.getBookId());
    }
}
