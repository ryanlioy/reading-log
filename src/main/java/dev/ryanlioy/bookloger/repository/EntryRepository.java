package dev.ryanlioy.bookloger.repository;

import dev.ryanlioy.bookloger.entity.EntryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EntryRepository extends CrudRepository<EntryEntity, Long> {
    @Query(value = "SELECT * FROM entry_entity WHERE book_id = :bookId AND user_id = :userId", nativeQuery = true)
    List<EntryEntity> findAllByUserIdAndBookId(@Param("bookId") Long bookId, @Param("userId") Long userId);
}
