package com.example.processservice.service;

import com.example.processservice.dto.WrittenDto;
import com.example.processservice.jpa.WrittenEntity;

public interface WrittenService {

    Iterable<WrittenEntity> getWrittenListByJobsNo(String jobsNo);
    Iterable<WrittenEntity> checkPassOrNot(String jobsNo, Integer count);
    Iterable<WrittenEntity> writtenScore(String jobsNo);
}
