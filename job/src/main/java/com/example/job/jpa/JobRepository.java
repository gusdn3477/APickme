package com.example.job.jpa;

import org.springframework.data.repository.*;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;

public interface JobRepository extends CrudRepository<JobEntity, String>{
    Iterable<JobEntity> findAllByCorpNo(String corpNo);
    JobEntity findByJobsNo(String jobsNo);
    Iterable<JobEntity> findByApplyStartBeforeAndApplyEndAfter(Date curTime, Date endTime);
    JobEntity findByJobsNoAndEmpNo(String jobsNo, String empNo);
    void deleteByJobsNo(String jobsNo);
    JobEntity findByEmpNoAndCorpNo(String empNo, String corpNo);


}
//아직 모집중인 공고 보기
//전체 공고 보기
//createdBy
