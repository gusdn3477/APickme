package com.example.userservice.jpa;

import com.example.userservice.entity.ApplyEntity;
import org.springframework.data.repository.CrudRepository;

public interface ApplyRepository extends CrudRepository<ApplyEntity , String> {
    Iterable<ApplyEntity> findAllByJobsNo(String jobsNo);


}
