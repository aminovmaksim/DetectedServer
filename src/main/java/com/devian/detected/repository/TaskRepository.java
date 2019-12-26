package com.devian.detected.repository;

import com.devian.detected.domain.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
    List<Task> findAllByTypeAndCompleted(int type, int completed);

    Optional<Task> findByTagId(String tagId);
}
