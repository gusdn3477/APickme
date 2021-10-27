package com.example.job.service;

import com.example.job.dto.JobDto;
import com.example.job.dto.JobProcessDto;
import com.example.job.jpa.JobEntity;
import com.example.job.jpa.JobProcessEntity;

import java.util.Date;

public interface JobService {

    JobEntity createJob(String UUID, JobDto jobDto);
    JobProcessEntity createJobProcess(String UUID, JobProcessDto jobProcessDto);
    Iterable<JobEntity> getAllJobs();
    Iterable<JobEntity> getCorpAllJobs(String corpNo);
    JobEntity getJob(String jobsNo);
    Iterable<JobEntity> getApplyAvailable(Date curTime,Date endTime);
    JobEntity updateJob(JobDto jobDto, JobProcessDto jobProcessDto);
    void deleteJob(String jobsNo);
    void deleteJobProcess(String jobsNo);
    JobProcessEntity updateProcess(JobProcessDto jobProcessDto);
//    필요한 서비스 추가 예정
}
