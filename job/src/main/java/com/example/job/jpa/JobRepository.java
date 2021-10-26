package com.example.job.jpa;

import org.springframework.data.repository.*;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;

public interface JobRepository extends CrudRepository<JobEntity, Long>{
    Iterable<JobEntity> findAllByCorpNo(String corpNo);
    JobEntity findByJobsNo(String jobsNo);
    Iterable<JobEntity> findByApplyStartBeforeAndApplyEndAfter(Date curTime, Date endTime);
    JobEntity findByJobsNoAndEmpNo(String jobsNo, String empNo);
    void deleteByJobsNo(String jobsNo);



}
//아직 모집중인 공고 보기
//전체 공고 보기
//createdBy
