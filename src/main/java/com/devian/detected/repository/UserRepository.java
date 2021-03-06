package com.devian.detected.repository;

import com.devian.detected.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@SuppressWarnings("unused")
public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findByUid(String uid);
    Optional<User> findByEmail(String email);

    Optional<User> findByDisplayName(String displayName);

    List<User> findAll();
}
