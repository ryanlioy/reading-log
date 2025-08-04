package dev.ryanlioy.booklogger.repository;

import dev.ryanlioy.booklogger.entity.SeriesEntity;
import org.springframework.data.repository.CrudRepository;

public interface SeriesRepository extends CrudRepository<SeriesEntity, Long> {
}
