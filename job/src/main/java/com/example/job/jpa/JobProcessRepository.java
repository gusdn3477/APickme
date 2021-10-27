package com.example.job.jpa;

import org.springframework.data.repository.CrudRepository;

public interface JobProcessRepository extends CrudRepository<JobProcessEntity, String> {
    void deleteByJobsNo(String jobsNo);
    JobProcessEntity findByJobsNo(String jobsNo);


}
