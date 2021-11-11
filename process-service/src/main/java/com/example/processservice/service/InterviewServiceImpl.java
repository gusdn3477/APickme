package com.example.processservice.service;

import com.example.processservice.dto.InterviewDto;
import com.example.processservice.dto.WrittenDto;
import com.example.processservice.jpa.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@Slf4j
@Service
public class InterviewServiceImpl implements InterviewService {

    InterviewRepository interviewRepository;
    WrittenRepository writtenRepository;
    JobRepository jobRepository;

    @Autowired
    public InterviewServiceImpl(InterviewRepository interviewRepository) {
        this.interviewRepository = interviewRepository;
    }

    @Override
    public InterviewEntity createInterviewPerson(InterviewDto interviewDto) {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        InterviewEntity interviewEntity = mapper.map(interviewDto, InterviewEntity.class);

        interviewRepository.save(interviewEntity);
        return interviewEntity;
    }

    //넘겨받은 명단 한번에 생성하는 함수
    @Override
    public Iterable<InterviewEntity> createInterviewPeople(List<WrittenDto> writtenDto) {

        InterviewDto interviewDto = new InterviewDto();
        List<InterviewEntity> interviewEntities = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        //writtenDto를 interviewDto로 만들어야 함..
        writtenDto.forEach(v -> {
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
    public InterviewEntity getInterviewee(InterviewDto interviewDto) {
        return interviewRepository.findByApplyNum(interviewDto.getApplyNum());
    }

    // 1차 면접 부분
    @Override
    public InterviewEntity allocateFirstInterviewer(InterviewDto interviewDto) {

        InterviewEntity interviewEntity = interviewRepository.findByApplyNumAndEmpNo(interviewDto.getApplyNum(), interviewDto.getEmpNo());
        if (interviewEntity == null) {
            return null;
        }

        interviewEntity.setFirstInterviewer(interviewDto.getFirstInterviewer());
        interviewRepository.save(interviewEntity);

        return interviewEntity;
    }

    // 1차 면접 채점
    @Override
    public InterviewEntity scoreFirstInterviewer(InterviewDto interviewDto) {
        InterviewEntity interviewEntity = interviewRepository.findByFirstInterviewerAndApplyNum(interviewDto.getFirstInterviewer(), interviewDto.getApplyNum());
        if (interviewEntity == null) {
            return null;
        }

        interviewEntity.setFirstInterviewScore(interviewDto.getFirstInterviewScore());
        interviewRepository.save(interviewEntity);

        return interviewEntity;
    }


    // 2차 면접 부분 -> 2차 면접관 할당
    @Override
    public InterviewEntity allocateSecondInterviewer(InterviewDto interviewDto) {
        InterviewEntity interviewEntity = interviewRepository.findByApplyNumAndEmpNo(interviewDto.getApplyNum(), interviewDto.getEmpNo());
        if (interviewEntity == null) {
            return null;
        }

        interviewEntity.setSecondInterviewer(interviewDto.getSecondInterviewer());
        interviewRepository.save(interviewEntity);

        return interviewEntity;
    }

    // 2차 면접 채점
    @Override
    public InterviewEntity scoreSecondInterviewer(InterviewDto interviewDto) {
        InterviewEntity interviewEntity = interviewRepository.findBySecondInterviewerAndApplyNum(interviewDto.getSecondInterviewer(), interviewDto.getApplyNum());
        if (interviewEntity == null) {
            return null;
        }

        interviewEntity.setSecondInterviewScore(interviewDto.getSecondInterviewScore());
        interviewRepository.save(interviewEntity);

        return interviewEntity;
    }

    @Override
    public Iterable<InterviewEntity> getInterviewListByJobsNo(String jobsNo) { // 공고번호에 해당하는 지원자 조회
        return interviewRepository.findByJobsNo(jobsNo);
    }



    /*1차 면접 합불 결정*/
    @Override
    public InterviewEntity firstInterviewResult(InterviewDto firstInterviewResultDto) {
        InterviewEntity interviewEntity = interviewRepository.findByApplyNumAndUserId(firstInterviewResultDto.getApplyNum(), firstInterviewResultDto.getUserId());
        if (interviewEntity == null) {
            return null;
        }

        interviewEntity.setFirstInterviewResult(firstInterviewResultDto.getFirstInterviewResult());
        interviewRepository.save(interviewEntity);
        return interviewEntity;
    }

    /*1차 면접 합불 결정*/
    @Override
    public InterviewEntity secondInterviewResult(InterviewDto secondInterviewResultDto) {
        InterviewEntity interviewEntity = interviewRepository.findByApplyNumAndUserId(secondInterviewResultDto.getApplyNum(), secondInterviewResultDto.getUserId());
        if (interviewEntity == null) {
            return null;
        }

        interviewEntity.setSecondInterviewResult(secondInterviewResultDto.getSecondInterviewResult());
        interviewRepository.save(interviewEntity);
        return interviewEntity;
    }

    // 면접 전형자 조회(합/불 여부 볼 수 있게끔)
    @Override
    public InterviewEntity getInvPersonByJobsNoAndUserId(InterviewDto interviewDto) {
        return getInterviewRepository().findByJobsNoAndUserId(interviewDto.getJobsNo(),interviewDto.getUserId());
    }


    //추가된 부분
    @Override
    public InterviewEntity scoreFirstInterview(InterviewDto interviewDto){

        InterviewEntity interviewEntity = interviewRepository.findByApplyNum(interviewDto.getApplyNum());
        if(interviewEntity == null){
            return null;
        }
        interviewEntity.setFirstInterviewScore(interviewDto.getFirstInterviewScore());
        interviewEntity.setFirstInterviewer(interviewDto.getFirstInterviewer());
        interviewRepository.save(interviewEntity);

        return interviewEntity;
    }

    @Override
    public InterviewEntity scoreSecondInterview(InterviewDto interviewDto){

        InterviewEntity interviewEntity = interviewRepository.findByApplyNum(interviewDto.getApplyNum());
        if(interviewEntity == null){
            return null;
        }
        interviewEntity.setSecondInterviewScore(interviewDto.getSecondInterviewScore());
        interviewEntity.setSecondInterviewer(interviewDto.getSecondInterviewer());
        interviewRepository.save(interviewEntity);

        return interviewEntity;

    }

    //채점(2021-11-09 수정)
    @Override
    public Iterable<InterviewEntity> checkPassOrNotFirst(InterviewDto interviewDto){
        AtomicInteger cnt = new AtomicInteger(interviewDto.getCount());
        Iterable<InterviewEntity> interviewEntity = interviewRepository.findByJobsNoOrderByFirstInterviewScoreDesc(interviewDto.getJobsNo());
        if(interviewEntity == null){
            return null;
        }
        interviewEntity.forEach(v -> {
            if(cnt.get() > 0){
                v.setFirstInterviewResult("P");
                cnt.addAndGet(-1);
            }
            else{
                v.setFirstInterviewResult("F");
            }
            v.setFirstCheck(interviewDto.getFirstInterviewer());
//            v.setFirstInterviewer(interviewDto.getFirstInterviewer());
        });

        interviewRepository.saveAll(interviewEntity);
        return interviewEntity;
    }

    @Override
    public Iterable<InterviewEntity> checkPassOrNotSecond(InterviewDto interviewDto){
        AtomicInteger cnt = new AtomicInteger(interviewDto.getCount());
        Iterable<InterviewEntity> interviewEntity = interviewRepository.findByJobsNoOrderBySecondInterviewScoreDesc(interviewDto.getJobsNo());
        if(interviewEntity == null){
            return null;
        }
        interviewEntity.forEach(v -> {
            if(cnt.get() > 0){
                v.setSecondInterviewResult("P");
                cnt.addAndGet(-1);
            }
            else{
                v.setSecondInterviewResult("F");
            }
            v.setSecondCheck(interviewDto.getSecondInterviewer());
//            v.setSecondInterviewer(interviewDto.getSecondInterviewer());
        });

        interviewRepository.saveAll(interviewEntity);
        return interviewEntity;
    }
}
