package com.devian.detected.repository;

import com.devian.detected.domain.RankRow;
import com.devian.detected.domain.UserStats;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankRepository extends CrudRepository<RankRow, String> {

}
