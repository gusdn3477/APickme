package com.example.processservice.jpa;

import org.springframework.data.repository.CrudRepository;

public interface WrittenRepository extends CrudRepository<WrittenEntity, Long> {

    WrittenEntity findByJobsNoAndUserId(String jobsNo, String userId);
    Iterable<WrittenEntity> findByJobsNo(String jobsNo);
    Iterable<WrittenEntity> findByJobsNoAndEmpNo(String jobsNo, String EmpNo); // 공고, 인사코드로 검색 => 내가 관리자로 있는 공고 확인
    Iterable<WrittenEntity> findByJobsNoAndEmpNoOrderByWrittenScoreDesc(String jobsNo, String empNo);
    Iterable<WrittenEntity> findByWrittenResultAndJobsNo(String writtenResult, String jobsNo);


    Iterable<WrittenEntity> findByJobsNoOrderByWrittenScoreDesc(String jobsNo);

    WrittenEntity findByApplyNum(String applyNum);
}
