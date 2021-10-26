package com.example.processservice.service;

import com.example.processservice.dto.WrittenDto;
import com.example.processservice.jpa.WrittenEntity;

public interface WrittenService {

//    필기전형자 생성
    WrittenEntity createWrittenPerson(WrittenDto writtenDto);

    Iterable<WrittenEntity> getWrittenListByJobsNo(String jobsNo);
    Iterable<WrittenEntity> checkPassOrNot(WrittenDto writtenDto);
    Iterable<WrittenEntity> writtenScore(WrittenDto writtenDto);
    Iterable<WrittenEntity> getWrittenPassList(String writtenResult); // 합격자 리스트

}
