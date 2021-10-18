package com.example.processservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@Slf4j
public class ProcessController {

    Environment env;

    @Autowired
    public ProcessController(Environment env){
        this.env = env;
    }

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in Catalog Service on PORT %s",
                env.getProperty("local.server.port"));
    }

    @PutMapping("/process/written-test/score")
    public String writtenTestScore(){
        return "작성중";
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
