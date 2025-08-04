package dev.ryanlioy.booklogger.repository;

import dev.ryanlioy.booklogger.entity.EntryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EntryRepository extends CrudRepository<EntryEntity, Long> {
    /**
     * Find all entries by user and book
     * @param bookId the book ID
     * @param userId the user ID
     * @return {@link List} of matching entries
     */
    @Query(value = "SELECT * FROM entry WHERE book_id = :bookId AND user_id = :userId ORDER BY creation_date DESC", nativeQuery = true)
    List<EntryEntity> findAllByUserIdAndBookId(@Param("bookId") Long bookId, @Param("userId") Long userId);
}
