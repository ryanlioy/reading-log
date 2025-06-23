package dev.ryanlioy.bookloger.repository;

import dev.ryanlioy.bookloger.entity.GenreEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GenreRepository extends CrudRepository<GenreEntity, Long> {
    @Query(value = "SELECT genre_entity.* FROM book_entity INNER JOIN genre_entity ON genre_entity.book_id = book_entity.id WHERE book_id = :bookId", nativeQuery = true)
    List<GenreEntity> findAllByBookId(@Param("bookId") Long bookId);
}
