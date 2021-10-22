package com.example.job.service;

import com.example.job.dto.JobDto;
import com.example.job.jpa.JobEntity;

public interface JobService {

    JobEntity createJob(JobDto jobDto);
    Iterable<JobEntity> getAllJobs();
    Iterable<JobEntity> getCorpAllJobs(String corpNo2);
    JobEntity getJob(String jobsNo);
//    필요한 서비스 추가 예정
}
