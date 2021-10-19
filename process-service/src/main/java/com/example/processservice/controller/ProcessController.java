package com.example.processservice.controller;

import com.example.processservice.jpa.InterviewEntity;
import com.example.processservice.jpa.WrittenEntity;
import com.example.processservice.service.InterviewService;
import com.example.processservice.service.WrittenService;
import com.example.processservice.vo.ResponseWritten;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@Slf4j
public class ProcessController {

    Environment env;
    InterviewService interviewService;
    WrittenService writtenService;

    @Autowired
    public ProcessController(Environment env, InterviewService interviewService, WrittenService writtenService){
        this.interviewService = interviewService;
        this.writtenService = writtenService;
        this.env = env;
    }

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in Catalog Service on PORT %s",
                env.getProperty("local.server.port"));
    }

    @PutMapping("/process/written-test/score")
    public ResponseEntity<List<ResponseWritten>> writtenTestScore(ResponseWritten responseWritten){

        log.info("필기 자동 채점 서비스");
        Iterable<WrittenEntity> writtenList =
    }

    @PutMapping("/process/written-test/result")
    public String writtenTestResult(){
        return "작성중";
    }

    @PostMapping("/process/written-test")
    public String writtenTest(){
        //합격자 명단 받아서 interview 테이블에 데이터 넣기
        return "작성중";
    }

    //여기부터 1차 면접
    @PutMapping("/process/first-interview/allocate")
    public String firstInterviewAllocate(){
        return "작성중";
    }

    @PutMapping("/process/first-interview/score")
    public String firstInterviewScore(){
        return "작성중";
    }

    @PutMapping("/process/first-interview/result")
    public String firstInterviewResult(){
        return "작성중";
    }

    //여기부터 2차 면접
    @PutMapping("/process/second-interview/allocate")
    public String secondInterviewAllocate(){
        return "작성중";
    }

    @PutMapping("/process/second-interview/score")
    public String secondInterviewScore(){
        return "작성중";
    }

    @PutMapping("/process/second-interview/result")
    public String secondInterviewResult(){
        return "작성중";
    }

}
