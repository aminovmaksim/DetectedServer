package com.devian.detected.repository;

import com.devian.detected.domain.tasks.GeoTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@SuppressWarnings("unused")
public interface GeoTaskRepository extends CrudRepository<GeoTask, String> {
    List<GeoTask> findAllByCompleted(boolean completed);

    Optional<GeoTask> findByTagId(String tagId);
}
