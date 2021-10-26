package com.example.job.service;

import com.example.job.dto.JobDto;
import com.example.job.jpa.JobEntity;

import java.util.Date;

public interface JobService {

    JobEntity createJob(JobDto jobDto);
    Iterable<JobEntity> getAllJobs();
    Iterable<JobEntity> getCorpAllJobs(String corpNo);
    JobEntity getJob(String jobsNo);
    Iterable<JobEntity> getApplyAvailable(Date curTime,Date endTime);
    JobEntity updateJob(JobDto jobDto);
    void deleteJob(String jobsNo);
//    필요한 서비스 추가 예정
}
