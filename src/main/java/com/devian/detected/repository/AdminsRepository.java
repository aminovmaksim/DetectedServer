package com.devian.detected.repository;

import com.devian.detected.domain.Admin;
import com.devian.detected.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminsRepository extends CrudRepository<Admin, String> {
    Optional<Admin> findByUid(String uid);
}
