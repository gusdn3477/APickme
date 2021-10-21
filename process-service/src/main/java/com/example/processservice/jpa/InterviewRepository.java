package com.example.processservice.jpa;

import org.springframework.data.repository.CrudRepository;

public interface InterviewRepository extends CrudRepository<InterviewEntity, Long> {

    Iterable<InterviewEntity> findByJobsNo(String jobsNo);
    InterviewEntity findByApplyNum(String applyNum);
    InterviewEntity findByFirstInterviewer(String firstInterviewer); // 이 부분을 지원번호로 바꿔야 할듯?
    InterviewEntity findBySecondInterviewer(String secondInterviewer);
}
