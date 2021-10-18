package com.example.processservice.service;

import com.example.processservice.jpa.InterviewEntity;
import com.example.processservice.jpa.InterviewRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Slf4j
@Service
public class InterviewServiceImpl implements InterviewService{
    InterviewRepository interviewRepository;

    @Autowired
    public InterviewServiceImpl(InterviewRepository interviewRepository){
        this.interviewRepository = interviewRepository;
    }

    @Override
    public Iterable<InterviewEntity> getInterviewListByJobsNo(Integer jobsNo){
        return interviewRepository.findByJobsNo(jobsNo);
    }
}
