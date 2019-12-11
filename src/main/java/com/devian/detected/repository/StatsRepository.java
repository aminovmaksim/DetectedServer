package com.devian.detected.repository;

import com.devian.detected.domain.User;
import com.devian.detected.domain.UserStats;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatsRepository extends CrudRepository<UserStats, String> {
    Optional<UserStats> findByUid(String uid);
}
