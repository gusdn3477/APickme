package com.example.processservice.service;

import com.example.processservice.dto.WrittenDto;
import com.example.processservice.jpa.WrittenEntity;

public interface WrittenService {

//    필기전형자 생성
    WrittenEntity createWrittenPerson(WrittenDto writtenDto);

    Iterable<WrittenEntity> getWrittenListByJobsNo(String jobsNo);
    Iterable<WrittenEntity> checkPassOrNot(String jobsNo, Integer count);
    Iterable<WrittenEntity> writtenScore(String jobsNo);
    Iterable<WrittenEntity> getWrittenPassList(String writtenResult);

}
