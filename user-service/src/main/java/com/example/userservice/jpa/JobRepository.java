package com.example.userservice.jpa;

import com.example.userservice.entity.JobEntity;
import com.example.userservice.vo.ResponseJobDetail;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface JobRepository extends CrudRepository<JobEntity, String>{


    Iterable<JobEntity> findAllByJobsNoIn(List<String> applyJobsList);
}
//아직 모집중인 공고 보기
//전체 공고 보기
//createdBy
