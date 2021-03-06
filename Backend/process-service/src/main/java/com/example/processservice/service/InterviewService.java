package com.example.processservice.service;

import com.example.processservice.dto.InterviewDto;
import com.example.processservice.dto.WrittenDto;
import com.example.processservice.jpa.InterviewEntity;
import com.example.processservice.jpa.JobEntity;
import com.example.processservice.jpa.WrittenEntity;
import com.example.processservice.vo.ResponseInterviewFinal;

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

    //채점(2021-11-09 수정)
    Iterable<InterviewEntity> checkPassOrNotFirst(InterviewDto interviewDto);
    Iterable<InterviewEntity> checkPassOrNotSecond(InterviewDto interviewDto);
//    InterviewEntity getInterviewPersonByJobsNoAndUserId(InterviewDto interviewDto);

//    Iterable<JobEntity> getJobsByCorpNo(String corpNo);

    //합격자명단(2021-11-16 작성)
    List<ResponseInterviewFinal>  getInterviewFinal(String jobsNo);

}
