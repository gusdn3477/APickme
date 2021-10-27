package com.example.userservice.jpa;

import com.example.userservice.dto.ApplyDto;
import com.example.userservice.entity.ApplyEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ApplyRepository extends CrudRepository<ApplyEntity , Long> {

    @Transactional
    void deleteByJobsNo(String jobsNo);



    ApplyEntity findByJobsNo(String jobsNo);



    ApplyEntity findByUserId(String userId);

}
