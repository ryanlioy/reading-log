package dev.ryanlioy.bookloger.repository;


import dev.ryanlioy.bookloger.entity.AuthorEntity;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<AuthorEntity, Long> {
}
