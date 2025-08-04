package dev.ryanlioy.booklogger.repository;

import dev.ryanlioy.booklogger.entity.AuthorEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends CrudRepository<AuthorEntity, Long> {
    /**
     * Find all authors for a book
     * @param bookId the book ID
     * @return {@link List} of authors
     */
    @Query(value = "SELECT author.* FROM author INNER JOIN book ON author.name = book.author WHERE book.id = :bookId", nativeQuery = true)
    List<AuthorEntity> findAllAuthorsByBookId(@Param("bookId") Long bookId);
    Optional<AuthorEntity> findByName(String authorName);
}
