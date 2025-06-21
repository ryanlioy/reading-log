package dev.ryanlioy.bookloger.repository;

import dev.ryanlioy.bookloger.entity.BookEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends CrudRepository<BookEntity, Long> {
    @Query(value = "SELECT book_entity.* FROM currently_reading_entity INNER JOIN book_entity ON currently_reading_entity.book_id = book_entity.id WHERE user_id = :userId", nativeQuery = true)
    List<BookEntity> getCurrentlyReadingBooksByUserId(@Param("userId") Long userId);
}
