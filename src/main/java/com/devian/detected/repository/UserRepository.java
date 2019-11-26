package com.devian.detected.repository;

import com.devian.detected.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUuid(UUID uuid);
    Optional<User> findByLogin(String login);
    Optional<User> findByEmail(String email);
}
