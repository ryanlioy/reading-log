package dev.ryanlioy.bookloger.repository;

import dev.ryanlioy.bookloger.entity.BookEntity;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<BookEntity, Long> {
    boolean existByTitle(String title);
}
