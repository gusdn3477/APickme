package com.example.processservice.jpa;

import java.util.List;

public interface ApplyInterviewRepository extends ApplyRepository<ApplyEntity,String> {


    List<ApplyEntity> findAllByJobsNo(String jobsNo);

}



