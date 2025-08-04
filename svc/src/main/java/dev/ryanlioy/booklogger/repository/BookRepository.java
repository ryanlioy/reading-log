package dev.ryanlioy.booklogger.repository;

import dev.ryanlioy.booklogger.entity.BookEntity;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<BookEntity, Long> {
    boolean existsByTitle(String title);
}
