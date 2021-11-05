package com.example.processservice.service;

import com.example.processservice.dto.InterviewDto;
import com.example.processservice.dto.WrittenDto;
import com.example.processservice.jpa.InterviewEntity;
import com.example.processservice.jpa.JobEntity;

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

    /* 1차면접 합불 결정*/
    InterviewEntity firstInterviewResult(InterviewDto firstInterviewResultDto);
    /*2차 면접 합불 결정*/
    InterviewEntity secondInterviewResult(InterviewDto secondInterviewResultDto);

    // 면접결과 확인하기 위해 사용
    InterviewEntity getInvPersonByJobsNoAndUserId(InterviewDto interviewDto);

    //추가된 부분
    InterviewEntity scoreFirstInterview(InterviewDto interviewDto);
    InterviewEntity scoreSecondInterview(InterviewDto interviewDto);

//    Iterable<JobEntity> getJobsByCorpNo(String corpNo);
}
