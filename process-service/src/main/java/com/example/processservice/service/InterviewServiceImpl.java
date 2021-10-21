package com.example.processservice.service;

import com.example.processservice.dto.InterviewDto;
import com.example.processservice.dto.WrittenDto;
import com.example.processservice.jpa.InterviewEntity;
import com.example.processservice.jpa.InterviewRepository;
import com.example.processservice.jpa.WrittenEntity;
import com.example.processservice.jpa.WrittenRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Slf4j
@Service
public class InterviewServiceImpl implements InterviewService{

    InterviewRepository interviewRepository;
    WrittenRepository writtenRepository;

    @Autowired
    public InterviewServiceImpl(InterviewRepository interviewRepository){
        this.interviewRepository = interviewRepository;
    }

    @Override
    public InterviewEntity createInterviewPerson(InterviewDto interviewDto){

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        InterviewEntity interviewEntity = mapper.map(interviewDto, InterviewEntity.class);

        interviewRepository.save(interviewEntity);
        return interviewEntity;
    }

    //넘겨받은 명단 한번에 생성하는 함수
    @Override
    public Iterable<InterviewEntity> createInterviewPeople(List<WrittenDto> writtenDtos){

        List<InterviewEntity> interviewEntities = new ArrayList<>();
        InterviewDto interviewDto;
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        writtenDtos.forEach(v->{
//            interviewDto = mapper.map(v, InterviewDto.class);
            interviewEntities.add(mapper.map(mapper.map(v, InterviewDto.class), InterviewEntity.class));
        });
        interviewRepository.saveAll(interviewEntities);

        return interviewEntities;
    }

    @Override
    public InterviewEntity getInterviewee(InterviewDto interviewDto){
        return interviewRepository.findByApplyNum(interviewDto.getApplyNum());
    }

    // 1차 면접 부분
    @Override
    public InterviewEntity allocateFirstInterviewer(InterviewDto interviewDto){

        InterviewEntity interviewEntity = interviewRepository.findByApplyNum(interviewDto.getApplyNum());
        if((interviewDto.getEmpNo()).equals(interviewEntity.getEmpNo())){
            interviewEntity.setFirstInterviewer(interviewDto.getFirstInterviewer());
            interviewRepository.save(interviewEntity);
        }
        return interviewEntity;
    }

    // 1차 면접 채점
    @Override
    public InterviewEntity scoreFirstInterviewer(InterviewDto interviewDto){
        InterviewEntity interviewEntity = interviewRepository.findByFirstInterviewer(interviewDto.getFirstInterviewer());
        if((interviewDto.getFirstInterviewer()).equals(interviewEntity.getFirstInterviewer())){ // 같은 경우
            interviewEntity.setFirstInterviewScore(interviewDto.getFirstInterviewScore());
            interviewRepository.save(interviewEntity);
        }
        return interviewEntity;
    }


    // 2차 면접 부분
    @Override
    public InterviewEntity allocateSecondInterviewer(InterviewDto interviewDto){
        InterviewEntity interviewEntity = interviewRepository.findByApplyNum(interviewDto.getApplyNum());
        if((interviewDto.getEmpNo()).equals(interviewEntity.getEmpNo())){
            interviewEntity.setSecondInterviewer(interviewDto.getSecondInterviewer());
            interviewRepository.save(interviewEntity);
        }
        return interviewEntity;
    }

    @Override
    public InterviewEntity scoreSecondInterviewer(InterviewDto interviewDto){
        InterviewEntity interviewEntity = interviewRepository.findBySecondInterviewer(interviewDto.getFirstInterviewer());
        if((interviewDto.getSecondInterviewer()).equals(interviewEntity.getSecondInterviewer())){ // 같은 경우
            interviewEntity.setSecondInterviewScore(interviewDto.getSecondInterviewScore());
            interviewRepository.save(interviewEntity);
        }
        return interviewEntity;
    }

    @Override
    public Iterable<InterviewEntity> getInterviewListByJobsNo(String jobsNo){ // 공고번호에 해당하는 지원자 조회
        return interviewRepository.findByJobsNo(jobsNo);
    }
}
