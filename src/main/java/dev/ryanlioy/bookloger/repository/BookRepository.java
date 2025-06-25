package dev.ryanlioy.bookloger.repository;

import dev.ryanlioy.bookloger.entity.BookEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends CrudRepository<BookEntity, Long> {
    @Query(value = "SELECT book.* FROM collection_item INNER JOIN book ON collection_item.book_id = book.id WHERE user_id = :userId AND collection_type = :type", nativeQuery = true)
    List<BookEntity> getCollectionByUserIdAndType(@Param("userId") Long userId, @Param("type") String type);
}
