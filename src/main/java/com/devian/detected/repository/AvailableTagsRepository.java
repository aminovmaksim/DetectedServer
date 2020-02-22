package com.devian.detected.repository;

import com.devian.detected.domain.tasks.Tag;
import org.springframework.data.repository.CrudRepository;

public interface AvailableTagsRepository extends CrudRepository<Tag, String> {
}
