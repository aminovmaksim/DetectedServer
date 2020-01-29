package com.devian.detected.repository;

import com.devian.detected.domain.RankRow;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RankRepository extends CrudRepository<RankRow, String> {
    Optional<RankRow> findByUid(String uid);
}
