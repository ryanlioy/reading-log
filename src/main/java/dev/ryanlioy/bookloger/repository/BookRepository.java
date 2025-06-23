package dev.ryanlioy.bookloger.repository;

import dev.ryanlioy.bookloger.entity.BookEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends CrudRepository<BookEntity, Long> {
    @Query(value = "SELECT book.* FROM currently_reading INNER JOIN book ON currently_reading.book_id = book.id WHERE user_id = :userId", nativeQuery = true)
    List<BookEntity> getCurrentlyReadingBooksByUserId(@Param("userId") Long userId);
}
