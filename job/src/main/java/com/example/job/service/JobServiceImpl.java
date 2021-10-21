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
    public Iterable<JobEntity> getCorpAllJobs(String corpNo2){
        return jobRepository.findAllByCorpNo2(corpNo2);
    }


}
