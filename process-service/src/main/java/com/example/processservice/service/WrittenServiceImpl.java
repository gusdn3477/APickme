package com.example.processservice.service;

import com.example.processservice.dto.WrittenDto;
import com.example.processservice.jpa.WrittenEntity;
import com.example.processservice.jpa.WrittenRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
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

    // 면접자 개인 생성 => 단체도 만들면 좋을듯
    @Override
    public WrittenEntity createWrittenPerson(WrittenDto writtenDto){

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        WrittenEntity writtenEntity = mapper.map(writtenDto, WrittenEntity.class);

        writtenRepository.save(writtenEntity);
        return writtenEntity;
    }

    @Override
    public Iterable<WrittenEntity> getWrittenListByJobsNo(String jobsNo){ // 공고번호에 해당하는 지원자 조회
        return writtenRepository.findByJobsNo(jobsNo);
    }

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

    @Override
    public Iterable<WrittenEntity> checkPassOrNot(WrittenDto writtenDto){

        AtomicInteger cnt = new AtomicInteger(writtenDto.getCount());
        Iterable<WrittenEntity> writtenEntity = writtenRepository.findByJobsNoOrderByWrittenScoreDesc(writtenDto.getJobsNo());
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
