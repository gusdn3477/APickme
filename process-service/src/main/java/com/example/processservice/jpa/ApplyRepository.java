package com.example.processservice.jpa;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface ApplyRepository extends CrudRepository<ApplyEntity , String> {
    Iterable<ApplyEntity> findAllByJobsNo(String jobsNo);

    @Transactional
    //void deleteByJobsNo(String jobsNo);

    Iterable<ApplyEntity> findByJobsNo(String jobsNo);

    //ApplyEntity findByUserId(String userId);

}
