package dev.ryanlioy.bookloger.repository;

import dev.ryanlioy.bookloger.entity.SeriesEntity;
import org.springframework.data.repository.CrudRepository;

public interface SeriesRepository extends CrudRepository<SeriesEntity, Long> {
}
