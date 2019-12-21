package com.devian.detected.repository;

import com.devian.detected.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findByUid(String uid);

    Optional<User> findByEmail(String email);

    List<User> findAll();
}
