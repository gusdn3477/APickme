package com.example.processservice.service;

import com.example.processservice.dto.ApplyDto;
import com.example.processservice.dto.WrittenDto;
import com.example.processservice.jpa.ApplyEntity;
import com.example.processservice.jpa.WrittenEntity;

import java.util.List;

public interface WrittenService {

//    필기전형자 생성
    WrittenEntity createWrittenPerson(List<WrittenDto> writtenEntity);
    WrittenEntity getWrittenPersonByJobsNoAndUserId(WrittenDto writtenDto);

    Iterable<WrittenEntity> getWrittenListByJobsNoAndEmpNo(WrittenDto writtenDto);
    Iterable<WrittenEntity> checkPassOrNot(WrittenDto writtenDto);
    Iterable<WrittenEntity> writtenScore(WrittenDto writtenDto);
    Iterable<WrittenEntity> getWrittenPassList(String writtenResult, String jobsNo); // 합격자 리스트

    Iterable<WrittenEntity> getWrittenListByJobsNo(String jobsNo);
    //Iterable<WrittenEntity> getWrittenAllCorpNo(String corpNo);

    List<WrittenDto> getApplicantList(String jobsNo);

    WrittenEntity setJobsClose(String jobsNo);
}
