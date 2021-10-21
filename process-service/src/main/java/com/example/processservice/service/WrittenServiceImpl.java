package com.example.processservice.service;

import com.example.processservice.jpa.WrittenEntity;
import com.example.processservice.jpa.WrittenRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Data
@Slf4j
@Service
public class WrittenServiceImpl implements WrittenService{

    WrittenRepository writtenRepository;

    @Autowired
    public WrittenServiceImpl(WrittenRepository writtenRepository){
        this.writtenRepository = writtenRepository;
    }

    @Override
    public Iterable<WrittenEntity> getWrittenListByJobsNo(String jobsNo){ // 공고번호에 해당하는 지원자 조회
        return writtenRepository.findByJobsNo(jobsNo);
    }

    @Override
    public Iterable<WrittenEntity> writtenScore(String jobsNo){

        Iterable<WrittenEntity> writtenEntity = writtenRepository.findByJobsNo(jobsNo);
        writtenEntity.forEach(v -> {
            v.setWrittenScore((int)(Math.random()*101));
//            ModelMapper mapper = new ModelMapper();
//            mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//            WrittenDto writtenDto = mapper.map(v, WrittenDto.class);
//            writtenDto.setWrittenScore((int)Math.random()*101);
//            writtenRepository.save(writtenDto);
        });
        writtenRepository.saveAll(writtenEntity);
        return writtenEntity;
    }

    @Override
    public Iterable<WrittenEntity> checkPassOrNot(String jobsNo, Integer count){

        AtomicInteger cnt = new AtomicInteger(count);
        Iterable<WrittenEntity> writtenEntity = writtenRepository.findByJobsNoOrderByWrittenScoreDesc(jobsNo);
        writtenEntity.forEach(v -> {
            if(cnt.get() > 0) {
                v.setWrittenResult("P");
                cnt.addAndGet(-1);
//                v.setWrittenScore((int) (Math.random() * 101));
            }
            else{
                v.setWrittenResult(("F"));
            }
//            ModelMapper mapper = new ModelMapper();
//            mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//            WrittenDto writtenDto = mapper.map(v, WrittenDto.class);
//            writtenDto.setWrittenScore((int)Math.random()*101);
//            writtenRepository.save(writtenDto);
        });

        writtenRepository.saveAll(writtenEntity);
        return writtenEntity;
    }
}