package com.example.processservice.service;

import com.example.processservice.jpa.InterviewEntity;

public interface InterviewService {

    Iterable<InterviewEntity> getInterviewListByJobsNo(String jobsNo);
}
