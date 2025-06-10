package dev.ryanlioy.bookloger.repository;

import dev.ryanlioy.bookloger.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

}
