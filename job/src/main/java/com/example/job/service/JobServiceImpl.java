package com.example.job.service;

import com.example.job.dto.JobDto;
import com.example.job.jpa.JobEntity;
import com.example.job.jpa.JobRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Data
@Slf4j
@Service
public class JobServiceImpl implements JobService{
    JobRepository jobRepository;

    @Autowired
    public JobServiceImpl(JobRepository jobRepository){
        this.jobRepository = jobRepository;
    }


    @Override
    public JobEntity createJob(JobDto jobDto) {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        JobEntity jobEntity = mapper.map(jobDto, JobEntity.class);
        jobRepository.save(jobEntity);

        return null;
    }

    @Override
    public Iterable<JobEntity> getAllJobs() {
        return jobRepository.findAll();
    }
    @Override
    public Iterable<JobEntity> getCorpAllJobs(String corpNo){
        return jobRepository.findAllByCorpNo(corpNo);
    }

    @Override
    public JobEntity getJob(String jobsNo){
        return jobRepository.findByJobsNo(jobsNo);
    }

    @Override
    public Iterable<JobEntity> getApplyAvailable(Date curTime, Date endTime){
        return jobRepository.findByApplyStartBeforeAndApplyEndAfter(curTime, endTime);

    }

    @Override
    public JobEntity updateJob(JobDto jobDto){
        JobEntity jobEntity = jobRepository.findByJobsNoAndEmpNo(jobDto.getJobsNo(), jobDto.getEmpNo());
        if(jobEntity == null){
            return null;
        }
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        jobEntity = mapper.map(jobDto, JobEntity.class);
        jobRepository.save(jobEntity);
        return jobEntity;
    }

    @Override
    @Transactional
    public void deleteJob(String jobsNo){
        jobRepository.deleteByJobsNo(jobsNo);
        }






}
