package com.example.job.service;

import com.example.job.dto.JobDto;
import com.example.job.dto.JobProcessDto;
import com.example.job.jpa.JobEntity;
import com.example.job.jpa.JobProcessEntity;
import com.example.job.vo.ResponseCalender;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface JobService {

    JobEntity createJob(String UUID, JobDto jobDto);
    JobProcessEntity createJobProcess(String UUID, JobProcessDto jobProcessDto);
    Iterable<JobEntity> getAllJobs();
    Iterable<JobEntity> getCorpAllJobs(String corpNo);
    JobEntity getJob(String jobsNo);
    Iterable<JobEntity> getApplyAvailable(Date curTime,Date endTime);
    JobEntity updateJob(JobDto jobDto, JobProcessDto jobProcessDto);
    Iterable<JobEntity> getCorpClosedJobs(String corpNo,String closed);

    @Transactional
    void deleteJob(String jobsNo);

    @Transactional
    void deleteJobProcess(String jobsNo);
    JobProcessEntity updateProcess(JobProcessDto jobProcessDto);

    JobProcessEntity getProcess(String jobsNo);

    /*달력 관련*/
    List<ResponseCalender> getCorpAllJob(String corpNo);
    /*달력 관련*/
    List<ResponseCalender> getAllJobss();
//    필요한 서비스 추가 예정
}
