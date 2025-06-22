package dev.ryanlioy.bookloger.repository;

import dev.ryanlioy.bookloger.entity.GenreEntity;
import org.springframework.data.repository.CrudRepository;

public interface GenreRepository extends CrudRepository<GenreEntity, Long> {
}
