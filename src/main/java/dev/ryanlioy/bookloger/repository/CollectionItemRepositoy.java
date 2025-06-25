package dev.ryanlioy.bookloger.repository;

import dev.ryanlioy.bookloger.entity.CollectionItemEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CollectionItemRepositoy extends CrudRepository<CollectionItemEntity, Long> {
    @Query(value = "SELECT * FROM collection_item WHERE user_id = :userId AND collection_type = :type", nativeQuery = true)
    List<CollectionItemEntity> findAllItemsByUserIdAndType(Long userId, String type);
}
