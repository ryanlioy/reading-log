package dev.ryanlioy.bookloger.repository;

import dev.ryanlioy.bookloger.entity.CollectionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CollectionRepository extends CrudRepository<CollectionEntity, Long> {
    List<CollectionEntity> findAllByUserId(Long userId);
}
