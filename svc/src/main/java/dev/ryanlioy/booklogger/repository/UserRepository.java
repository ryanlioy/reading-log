package dev.ryanlioy.booklogger.repository;

import dev.ryanlioy.booklogger.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
}
