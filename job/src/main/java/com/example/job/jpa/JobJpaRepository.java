package com.example.job.jpa;

import java.util.List;

public interface JobJpaRepository extends JobRepository<JobEntity, String> {

    List<JobEntity> findAllByCorpNo(String corpNo);

    List<JobEntity> findAll();
}
