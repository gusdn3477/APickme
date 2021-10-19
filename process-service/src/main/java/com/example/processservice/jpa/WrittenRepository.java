package com.example.processservice.jpa;

import org.springframework.data.repository.CrudRepository;

public interface WrittenRepository extends CrudRepository<WrittenEntity, Long> {

    Iterable<WrittenEntity> findByJobsNo(String jobsNo);
    Iterable<WrittenEntity> findByJobsNoOrderByWrittenScoreDesc(String jobsNo);
}
