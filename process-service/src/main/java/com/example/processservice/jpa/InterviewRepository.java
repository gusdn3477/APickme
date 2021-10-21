package com.example.processservice.jpa;

import org.springframework.data.repository.CrudRepository;

public interface InterviewRepository extends CrudRepository<InterviewEntity, Long> {

    Iterable<InterviewEntity> findByJobsNo(String jobsNo);
}
