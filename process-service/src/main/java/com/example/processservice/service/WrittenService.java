package com.example.processservice.service;

import com.example.processservice.jpa.WrittenEntity;

public interface WrittenService {

    Iterable<WrittenEntity> getWrittenListByJobsNo(Integer jobsNo);
}
