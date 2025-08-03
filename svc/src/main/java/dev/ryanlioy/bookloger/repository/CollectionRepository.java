package dev.ryanlioy.bookloger.repository;

import dev.ryanlioy.bookloger.entity.CollectionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CollectionRepository extends CrudRepository<CollectionEntity, Long> {
    /**
     * Find all collections by user ID
     * @param userId the user ID to find collections of
     * @return a {@link List} of all the matchin collections
     */
    List<CollectionEntity> findAllByUserId(Long userId);
}
