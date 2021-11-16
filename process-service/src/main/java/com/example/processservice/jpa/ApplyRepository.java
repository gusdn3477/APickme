package com.example.processservice.jpa;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ApplyRepository<A, S> extends CrudRepository<ApplyEntity , String> {
    Iterable<ApplyEntity> findAllByJobsNo(String jobsNo);

    @Transactional
    //void deleteByJobsNo(String jobsNo);

    Iterable<ApplyEntity> findByJobsNo(String jobsNo);

    //List<ApplyEntity> getApply(String jobsNo);

    //ApplyEntity findByUserId(String userId);

}
