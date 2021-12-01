package com.example.processservice.jpa;

import org.springframework.data.repository.CrudRepository;

public interface InterviewRepository extends CrudRepository<InterviewEntity, Long> {

    Iterable<InterviewEntity> findByJobsNo(String jobsNo);
    Iterable<InterviewEntity> findByJobsNoAndEmpNo(String jobsNo, String EmpNo); // 공고, 인사코드로 검색 => 내가 관리자로 있는 공고 확인
    InterviewEntity findByApplyNum(String applyNum);
    InterviewEntity findByApplyNumAndEmpNo(String applyNum, String EmpNo);
    InterviewEntity findByFirstInterviewerAndApplyNum(String firstInterviewer, String applyNum); // 이 부분을 지원번호로 바꿔야 할듯?
    InterviewEntity findBySecondInterviewerAndApplyNum(String secondInterviewer, String applyNum);

    //바뀐 부분

    InterviewEntity findByApplyNumAndUserId(String applyNum, String userId);
    InterviewEntity findByJobsNoAndUserId(String jobsNo, String userId);

    //2021-11-09 추가
    Iterable<InterviewEntity> findByJobsNoOrderByFirstInterviewScoreDesc(String jobsNo);
    Iterable<InterviewEntity> findByJobsNoOrderBySecondInterviewScoreDesc(String jobsNo);
    //2021-11-09 추가
    InterviewEntity findByApplyNumAndJobsNo(String applyNum, String jobsNo);
}
