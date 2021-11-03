package com.example.job.service;

import com.example.job.dto.JobDto;
import com.example.job.dto.JobProcessDto;
import com.example.job.jpa.JobEntity;
import com.example.job.jpa.JobProcessEntity;
import com.example.job.jpa.JobRepository;
import com.example.job.jpa.JobProcessRepository;
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
    JobProcessRepository jobProcessRepository;

    @Autowired
    public JobServiceImpl(JobRepository jobRepository, JobProcessRepository jobProcessRepository) {
        this.jobRepository = jobRepository;
        this.jobProcessRepository = jobProcessRepository;
    }


    @Override
    public JobEntity createJob(String UUID,JobDto jobDto) {
        jobDto.setJobsNo(UUID);
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        JobEntity jobEntity = mapper.map(jobDto, JobEntity.class);

        jobRepository.save(jobEntity);

        return null;
    }

    @Override
    public JobProcessEntity createJobProcess(String UUID, JobProcessDto jobProcessDto){
        jobProcessDto.setJobsNo(UUID);
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        JobProcessEntity jobProcessEntity = mapper.map(jobProcessDto, JobProcessEntity.class);

        jobProcessEntity.setIntv1Pass((int) (jobProcessEntity.getRecruitNum()*jobProcessEntity.getIntv1Multiple()));
        jobProcessEntity.setIntv2Pass((int) (jobProcessEntity.getRecruitNum()*jobProcessEntity.getIntv2Multiple()));
        jobProcessEntity.setWrittenPass((int) (jobProcessEntity.getRecruitNum()*jobProcessEntity.getWrittenMultiple()));

        jobProcessRepository.save(jobProcessEntity);

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
    public JobProcessEntity updateProcess(JobProcessDto jobProcessDto){
        JobProcessEntity jobProcessEntity = jobProcessRepository.findByJobsNo(jobProcessDto.getJobsNo());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        jobProcessEntity = mapper.map(jobProcessDto,JobProcessEntity.class);
        jobProcessEntity.setIntv1Pass((int) (jobProcessEntity.getRecruitNum()*jobProcessEntity.getIntv1Multiple()));
        jobProcessEntity.setIntv2Pass((int) (jobProcessEntity.getRecruitNum()*jobProcessEntity.getIntv2Multiple()));
        jobProcessEntity.setWrittenPass((int) (jobProcessEntity.getRecruitNum()*jobProcessEntity.getWrittenMultiple()));
        jobProcessRepository.save(jobProcessEntity);
        return jobProcessEntity;

    }
    @Override
    public JobEntity updateJob(JobDto jobDto, JobProcessDto jobProcessDto){
//        JobEntity jobEntity = jobRepository.findByJobsNoAndEmpNo(jobDto.getJobsNo(), jobDto.getEmpNo());
        JobEntity jobEntity = jobRepository.findByJobsNoAndEmpNo(jobDto.getJobsNo(), jobDto.getEmpNo());

        if(jobEntity == null){
            return null;
        }
        else {
//            updateProcess(jobProcessDto);
            ModelMapper mapper = new ModelMapper();
            mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            jobEntity = mapper.map(jobDto, JobEntity.class);

            jobRepository.save(jobEntity);

            return jobEntity; }
    }

    @Override
    @Transactional
    public void deleteJob(String jobsNo){
        jobRepository.deleteByJobsNo(jobsNo);
        jobProcessRepository.deleteByJobsNo(jobsNo);
    }

    @Override
    @Transactional
    public void deleteJobProcess(String jobsNo){
        jobRepository.deleteByJobsNo(jobsNo);
    }

    @Override
    public JobProcessEntity getProcess(String jobsNo){
        return jobProcessRepository.findByJobsNo(jobsNo);
    }
}
