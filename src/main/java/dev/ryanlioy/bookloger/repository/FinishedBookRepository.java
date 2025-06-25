package dev.ryanlioy.bookloger.repository;

import dev.ryanlioy.bookloger.dto.FinishedBookDto;
import dev.ryanlioy.bookloger.entity.FinishedBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FinishedBookRepository extends JpaRepository<FinishedBookEntity, Long> {
    @Query(value = "SELECT book.* FROM book INNER JOIN finished_book ON finished_book.id = finished_book.book_id WHERE book_id = :id", nativeQuery = true)
    List<FinishedBookDto> getFinishedBooksByUserId(@Param("id") Long id);
}
