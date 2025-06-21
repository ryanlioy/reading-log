package dev.ryanlioy.bookloger.repository;

import dev.ryanlioy.bookloger.entity.CurrentlyReadingEntity;
import org.springframework.data.repository.CrudRepository;

public interface CurrentlyReadingRepository extends CrudRepository<CurrentlyReadingEntity, Long> {
}
