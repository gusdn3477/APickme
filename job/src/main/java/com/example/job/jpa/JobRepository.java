package com.example.job.jpa;

import org.springframework.data.repository.*;

import java.util.Date;
import java.util.List;

public interface JobRepository<R, S> extends CrudRepository<JobEntity, String>{
    List<JobEntity> findAllByCorpNo(String corpNo);
    JobEntity findByJobsNo(String jobsNo);
    Iterable<JobEntity> findByApplyStartBeforeAndApplyEndAfter(Date curTime, Date endTime);
    JobEntity findByJobsNoAndEmpNo(String jobsNo, String empNo);
    void deleteByJobsNo(String jobsNo);
    JobEntity findByEmpNoAndCorpNo(String empNo, String corpNo);
    Iterable<JobEntity> findByCorpNoAndClosed(String corpNo, String closed);


}
//아직 모집중인 공고 보기
//전체 공고 보기
//createdBy
