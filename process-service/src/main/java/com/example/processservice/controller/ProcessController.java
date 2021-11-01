package com.example.processservice.controller;

import com.example.processservice.client.JobServiceClient;
import com.example.processservice.dto.ApplyDto;
import com.example.processservice.dto.InterviewDto;
import com.example.processservice.dto.WrittenDto;
import com.example.processservice.jpa.ApplyEntity;
import com.example.processservice.jpa.InterviewEntity;
import com.example.processservice.jpa.WrittenEntity;
import com.example.processservice.service.InterviewService;
import com.example.processservice.service.WrittenService;
import com.example.processservice.vo.RequestPutInterview;
import com.example.processservice.vo.RequestPutWritten;
import com.example.processservice.vo.ResponseJob;
import com.example.processservice.vo.ResponseWritten;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
@Slf4j
public class ProcessController {

    Environment env;
    InterviewService interviewService;
    WrittenService writtenService;
    CircuitBreakerFactory circuitBreakerFactory;
    JobServiceClient jobServiceClient;

    @Autowired
    public ProcessController(Environment env, InterviewService interviewService,
                             WrittenService writtenService,CircuitBreakerFactory circuitBreakerFactory,
                             JobServiceClient jobServiceClient){
        this.interviewService = interviewService;
        this.writtenService = writtenService;
        this.env = env;
        this.circuitBreakerFactory = circuitBreakerFactory;
        this.jobServiceClient = jobServiceClient;

    }

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in Catalog Service on PORT %s",
                env.getProperty("local.server.port"));
    }

    @PutMapping("/process/written-test/score")
    public ResponseEntity<List<ResponseWritten>> writtenTestScore(@RequestBody RequestPutWritten requestPutWritten){

        log.info("필기 자동 채점 서비스");
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//
        WrittenDto writtenDto = mapper.map(requestPutWritten, WrittenDto.class);

        writtenService.writtenScore(writtenDto);
        Iterable<WrittenEntity> writtenList = writtenService.getWrittenListByJobsNoAndEmpNo(writtenDto);
        List<ResponseWritten> result = new ArrayList<>();
        writtenList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseWritten.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PutMapping("/process/written-test/result")
    public ResponseEntity<List<ResponseWritten>> writtenTestResult(@RequestBody RequestPutWritten requestPutWritten){
        log.info("필기 자동 합/불 서비스");
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//
        WrittenDto writtenDto = mapper.map(requestPutWritten, WrittenDto.class);
        writtenService.checkPassOrNot(writtenDto);

        Iterable<WrittenEntity> writtenList = writtenService.getWrittenListByJobsNoAndEmpNo(writtenDto);
        List<ResponseWritten> result = new ArrayList<>();
        writtenList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseWritten.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/process/written-test")
    public String writtenTest(){
        //합격자 명단 받아서 interview 테이블에 데이터 넣기
        Iterable<WrittenEntity> writtenList = writtenService.getWrittenPassList("P");
        List<WrittenDto> result = new ArrayList<>();
        writtenList.forEach(v -> {
            result.add(new ModelMapper().map(v, WrittenDto.class));
        });

        interviewService.createInterviewPeople(result);
        return "OK";
    }

    //1차 면접관 할당
    @PutMapping("/process/first-interview/allocate")
    public String firstInterviewAllocate(@RequestBody RequestPutInterview requestPutInterview) {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//
        InterviewDto interviewDto = mapper.map(requestPutInterview, InterviewDto.class);
        interviewDto.setEmpNo(requestPutInterview.getEmpNo());
        interviewDto.setFirstInterviewer(requestPutInterview.getFirstInterviewer());
        interviewDto.setApplyNum(requestPutInterview.getApplyNum());

        InterviewEntity interviewEntity = interviewService.allocateFirstInterviewer(interviewDto);
        if(interviewEntity == null){
            return "1차 면접관 할당 실패!";
        }

        return "1차 면접관 할당 성공!";
    }

    //1차 면접관 채점
    @PutMapping("/process/first-interview/score")
    public String firstInterviewScore(@RequestBody RequestPutInterview requestPutInterview){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        InterviewDto interviewDto = mapper.map(requestPutInterview, InterviewDto.class);
        interviewDto.setFirstInterviewer(requestPutInterview.getFirstInterviewer());
        interviewDto.setApplyNum(requestPutInterview.getApplyNum());
        interviewDto.setFirstInterviewScore(requestPutInterview.getFirstInterviewScore());

        InterviewEntity interviewEntity = interviewService.scoreFirstInterviewer(interviewDto);
        if(interviewEntity == null){
            return "1차 면접 채점 실패!";
        }
        return "1차 면접 채점 성공!";
    }

    //1차 면접관 결과
    @PutMapping("/process/first-interview/result")
    public String firstInterviewResult(){
        return "작성중";
    }

    //여기부터 2차 면접 -> 2차 면접관 할당
    @PutMapping("/process/second-interview/allocate")
    public String secondInterviewAllocate(@RequestBody RequestPutInterview requestPutInterview) {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//
        InterviewDto interviewDto = mapper.map(requestPutInterview, InterviewDto.class);
        interviewDto.setEmpNo(requestPutInterview.getEmpNo());
        interviewDto.setFirstInterviewer(requestPutInterview.getSecondInterviewer());
        interviewDto.setApplyNum(requestPutInterview.getApplyNum());

        InterviewEntity interviewEntity = interviewService.allocateSecondInterviewer(interviewDto);
        if(interviewEntity == null){
            return "2차 면접관 할당 실패!";
        }
        return "2차 면접관 할당 성공";
    }

    // 2차 면접 채점
    @PutMapping("/process/second-interview/score")
    public String secondInterviewScore(@RequestBody RequestPutInterview requestPutInterview){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        InterviewDto interviewDto = mapper.map(requestPutInterview, InterviewDto.class);
        interviewDto.setSecondInterviewer(requestPutInterview.getSecondInterviewer());
        interviewDto.setApplyNum(requestPutInterview.getApplyNum());
        interviewDto.setSecondInterviewScore(requestPutInterview.getSecondInterviewScore());

        InterviewEntity interviewEntity = interviewService.scoreSecondInterviewer(interviewDto);
        if(interviewEntity == null){
            return "2차 면접 채점 실패";
        }
        return "2차 면접 채점 성공";
    }

    @PutMapping("/process/second-interview/result")
    public String secondInterviewResult(){
        return "작성중";
    }

//    // 자신이 담당인 공고 가져오기 -> 이제 필요 없다!!!!!
//    @GetMapping("/process/{empNo}")
//    public ResponseEntity getJobs(@PathVariable String empNo){
//        // Open feign
//        List<ResponseJob> jobList = null;
//        // Circuit Breaker
//        log.info("------------------------------------------------------Start Feign");
//        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("my-circuitbreaker");
//        jobList = circuitBreaker.run(() -> jobServiceClient.getJobs(empNo),
//                throwable -> new ArrayList<>());
//        log.info("------------------------------------------------------End Feign");
//
//        return ResponseEntity.status(HttpStatus.OK).body(jobList);
//    }

    // 자신이 담당하는 공고별 지원자 조회?  -> 이제 필요 없다!!!!!
    // 자신이 담당하는 공고별 지원자 점수 조회?   -> 이제 필요 없다!!!!!

    // 공고마감시 필기전형으로 지원자 데이터 넘기기
    @GetMapping("/process/{jobsNo}")
    public ResponseEntity setWritten(@PathVariable String jobsNo){

        List<WrittenDto> applyList= writtenService.getApplicantList(jobsNo);
        writtenService.createWrittenPerson(applyList);

        return ResponseEntity.status(HttpStatus.OK).body("데이터 옮기기 성공");
    }





}
