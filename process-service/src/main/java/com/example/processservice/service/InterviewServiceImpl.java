package com.example.processservice.service;

import com.example.processservice.dto.InterviewDto;
import com.example.processservice.dto.WrittenDto;
import com.example.processservice.jpa.InterviewEntity;
import com.example.processservice.jpa.InterviewRepository;
import com.example.processservice.jpa.WrittenRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public Iterable<InterviewEntity> createInterviewPeople(List<WrittenDto> writtenDto){

        InterviewDto interviewDto = new InterviewDto();
        List<InterviewEntity> interviewEntities = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        //writtenDto를 interviewDto로 만들어야 함..
        writtenDto.forEach(v->{
            interviewDto.setApplyNum(v.getApplyNum());
            interviewDto.setJobsNo(v.getJobsNo());
            interviewDto.setEmpNo(v.getEmpNo());
            interviewDto.setUserId(v.getUserId());
            interviewEntities.add(mapper.map(interviewDto, InterviewEntity.class));
        });
        interviewRepository.saveAll(interviewEntities);
        return interviewEntities;
    }

    @Override
    서울특별시 중랑구 용마산로 228    public InterviewEntity getInterviewee(InterviewDto interviewDto){
        return interviewRepository.findByApplyNum(interviewDto.getApplyNum());
    }

    // 1차 면접 부분
    @Override
    public InterviewEntity allocateFirstInterviewer(InterviewDto interviewDto){

        InterviewEntity interviewEntity = interviewRepository.findByApplyNumAndEmpNo(interviewDto.getApplyNum(), interviewDto.getEmpNo());
        if(interviewEntity == null){
            return null;
        }

        interviewEntity.setFirstInterviewer(interviewDto.getFirstInterviewer());
        interviewRepository.save(interviewEntity);

        return interviewEntity;
    }

    // 1차 면접 채점
    @Override
    public InterviewEntity scoreFirstInterviewer(InterviewDto interviewDto){
        InterviewEntity interviewEntity = interviewRepository.findByFirstInterviewerAndApplyNum(interviewDto.getFirstInterviewer(), interviewDto.getApplyNum());
        if(interviewEntity == null){
            return null;
        }

        interviewEntity.setFirstInterviewScore(interviewDto.getFirstInterviewScore());
        interviewRepository.save(interviewEntity);

        return interviewEntity;
    }


    // 2차 면접 부분 -> 2차 면접관 할당
    @Override
    public InterviewEntity allocateSecondInterviewer(InterviewDto interviewDto){
        InterviewEntity interviewEntity = interviewRepository.findByApplyNumAndEmpNo(interviewDto.getApplyNum(), interviewDto.getEmpNo());
        if(interviewEntity == null) {
            return null;
        }

        interviewEntity.setSecondInterviewer(interviewDto.getSecondInterviewer());
        interviewRepository.save(interviewEntity);

        return interviewEntity;
    }

    // 2차 면접 채점
    @Override
    public InterviewEntity scoreSecondInterviewer(InterviewDto interviewDto){
        InterviewEntity interviewEntity = interviewRepository.findBySecondInterviewerAndApplyNum(interviewDto.getSecondInterviewer(), interviewDto.getApplyNum());
        if(interviewEntity == null){
            return null;
        }

        interviewEntity.setSecondInterviewScore(interviewDto.getSecondInterviewScore());
        interviewRepository.save(interviewEntity);

        return interviewEntity;
    }

    @Override
    public Iterable<InterviewEntity> getInterviewListByJobsNo(String jobsNo){ // 공고번호에 해당하는 지원자 조회
        return interviewRepository.findByJobsNo(jobsNo);
    }
}
