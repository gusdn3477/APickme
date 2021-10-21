package com.example.processservice.service;

import com.example.processservice.dto.InterviewDto;
import com.example.processservice.dto.WrittenDto;
import com.example.processservice.jpa.InterviewEntity;

import java.util.List;

public interface InterviewService {

    InterviewEntity createInterviewPerson(InterviewDto interviewDto);
    InterviewEntity getInterviewee(InterviewDto interviewDto);

    //1차 면접
    InterviewEntity allocateFirstInterviewer(InterviewDto interviewDto);
    InterviewEntity scoreFirstInterviewer(InterviewDto interviewDto);

    //2차 면접
    InterviewEntity allocateSecondInterviewer(InterviewDto interviewDto);
    InterviewEntity scoreSecondInterviewer(InterviewDto interviewDto);

    Iterable<InterviewEntity> createInterviewPeople(List<WrittenDto> writtenDto);
    Iterable<InterviewEntity> getInterviewListByJobsNo(String jobsNo);
}
