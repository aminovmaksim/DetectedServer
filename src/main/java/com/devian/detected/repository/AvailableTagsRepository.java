package com.devian.detected.repository;

import com.devian.detected.domain.tasks.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailableTagsRepository extends CrudRepository<Tag, String> {
}
