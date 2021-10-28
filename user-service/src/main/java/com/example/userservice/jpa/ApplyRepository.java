package com.example.userservice.jpa;

import com.example.userservice.entity.ApplyEntity;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface ApplyRepository extends CrudRepository<ApplyEntity , String> {
    Iterable<ApplyEntity> findAllByJobsNo(String jobsNo);

    @Transactional
    void deleteByJobsNo(String jobsNo);



    ApplyEntity findByJobsNo(String jobsNo);



    ApplyEntity findByUserId(String userId);

}
