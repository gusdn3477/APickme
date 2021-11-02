package com.example.processservice.service;

import com.example.processservice.dto.ApplyDto;
import com.example.processservice.dto.WrittenDto;
import com.example.processservice.jpa.ApplyEntity;
import com.example.processservice.jpa.ApplyRepository;
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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Data
@Slf4j
@Service
public class WrittenServiceImpl implements WrittenService{

    WrittenRepository writtenRepository;
    ApplyRepository applyRepository;

    @Autowired
    public WrittenServiceImpl(WrittenRepository writtenRepository, ApplyRepository applyRepository){
        this.writtenRepository = writtenRepository;
        this.applyRepository = applyRepository;
    }

    // 면접자 개인 생성 => 단체도 만들면 좋을듯
    @Override
    public WrittenEntity createWrittenPerson(List<WrittenDto> writtenDtos){

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        List<WrittenEntity> writtenList = writtenDtos.stream().map(p -> mapper.map(p, WrittenEntity.class)).collect(Collectors.toList());

        writtenRepository.saveAll(writtenList);
        return null;
    }

    @Override
    public Iterable<WrittenEntity> getWrittenListByJobsNoAndEmpNo(WrittenDto writtenDto){ // 공고번호에 해당하는 지원자 조회
        return writtenRepository.findByJobsNoAndEmpNo(writtenDto.getJobsNo(), writtenDto.getEmpNo());
    }

    //채점하기 서비스
    @Override
    public Iterable<WrittenEntity> writtenScore(WrittenDto writtenDto){

        Iterable<WrittenEntity> writtenEntity = writtenRepository.findByJobsNoAndEmpNo(writtenDto.getJobsNo(), writtenDto.getEmpNo());
        if (writtenEntity == null){
            return null;
        }
        writtenEntity.forEach(v -> {
            v.setWrittenScore((int)(Math.random()*101));
        });
        writtenRepository.saveAll(writtenEntity);
        return writtenEntity;
    }

    @Override
    public Iterable<WrittenEntity> getWrittenPassList(String writtenResult){

        Iterable<WrittenEntity> passList = writtenRepository.findByWrittenResult(writtenResult);
        return passList;
    }


    //지원자 리스트를 지원자 테이블에서 가져온다.
    @Override
    public List<WrittenDto> getApplicantList(String jobsNo){

        Iterable<ApplyEntity> applyEntities = applyRepository.findByJobsNo(jobsNo);

        List<WrittenDto> result = new ArrayList<>();
        applyEntities.forEach(v -> {
            result.add(new ModelMapper().map(v, WrittenDto.class));
        });

        return result;
    }


    // P, F 정하기
    @Override
    public Iterable<WrittenEntity> checkPassOrNot(WrittenDto writtenDto){

        AtomicInteger cnt = new AtomicInteger(writtenDto.getCount());
        Iterable<WrittenEntity> writtenEntity = writtenRepository.findByJobsNoAndEmpNoOrderByWrittenScoreDesc(writtenDto.getJobsNo(), writtenDto.getEmpNo());
        if(writtenEntity == null){
            return null;
        }
        writtenEntity.forEach(v -> {
            if(cnt.get() > 0) {
                v.setWrittenResult("P");
                cnt.addAndGet(-1);
//                v.setWrittenScore((int) (Math.random() * 101));
            }
            else{
                v.setWrittenResult(("F"));
            }
        });

        writtenRepository.saveAll(writtenEntity);
        return writtenEntity;
    }



}
