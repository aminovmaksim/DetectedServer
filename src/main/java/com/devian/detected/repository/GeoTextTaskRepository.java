package com.devian.detected.repository;

import com.devian.detected.domain.tasks.GeoTextTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@SuppressWarnings("unused")
public interface GeoTextTaskRepository extends CrudRepository<GeoTextTask, String> {
    List<GeoTextTask> findAllByCompleted(boolean completed);

    Optional<GeoTextTask> findByTagId(String tagId);
}
