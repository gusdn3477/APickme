package com.example.processservice.controller;

import com.example.processservice.client.JobServiceClient;
import com.example.processservice.dto.InterviewDto;
import com.example.processservice.dto.WrittenDto;
import com.example.processservice.jpa.InterviewEntity;
import com.example.processservice.jpa.WrittenEntity;
import com.example.processservice.service.InterviewService;
import com.example.processservice.service.WrittenService;
import com.example.processservice.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
    public ResponseEntity<List<ResponseWritten>> writtenTestScore(@RequestBody RequestPutWrittenShort requestPutWritten){

        log.info("?????? ?????? ?????? ?????????");
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//
        WrittenDto writtenDto = mapper.map(requestPutWritten, WrittenDto.class);
        writtenService.writtenScore(writtenDto);
        Iterable<WrittenEntity> writtenList = writtenService.getWrittenListByJobsNo(writtenDto.getJobsNo());
//        Iterable<WrittenEntity> writtenList = writtenService.getWrittenListByJobsNoAndEmpNo(writtenDto);
        List<ResponseWritten> result = new ArrayList<>();
        writtenList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseWritten.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PutMapping("/process/written-test/result")
    public ResponseEntity<List<ResponseWritten>> writtenTestResult(@RequestBody RequestPutWritten requestPutWritten){
        log.info("?????? ?????? ???/??? ?????????");
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

    @GetMapping("/process/written-test/{jobsNo}")
    public String writtenTest(@PathVariable String jobsNo){
        //????????? ?????? ????????? interview ???????????? ????????? ??????
        Iterable<WrittenEntity> writtenList = writtenService.getWrittenPassList("P", jobsNo);
        List<WrittenDto> result = new ArrayList<>();
        writtenList.forEach(v -> {
            result.add(new ModelMapper().map(v, WrittenDto.class));
        });

        interviewService.createInterviewPeople(result);
        return "OK";
    }

    //1??? ????????? ??????
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
            return "1??? ????????? ?????? ??????!";
        }

        return "1??? ????????? ?????? ??????!";
    }

    //1??? ????????? ??????
    @PutMapping("/process/first-interview/score")
    public String firstInterviewScore(@RequestBody RequestPutInterview requestPutInterview){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        InterviewDto interviewDto = mapper.map(requestPutInterview, InterviewDto.class);
        interviewDto.setFirstInterviewer(requestPutInterview.getFirstInterviewer());
        interviewDto.setApplyNum(requestPutInterview.getApplyNum());
        interviewDto.setFirstInterviewScore(requestPutInterview.getFirstInterviewScore());

//        InterviewEntity interviewEntity = interviewService.scoreFirstInterviewer(interviewDto);
        InterviewEntity interviewEntity = interviewService.scoreFirstInterview(interviewDto);
        if(interviewEntity == null){
            return "1??? ?????? ?????? ??????!";
        }
        return "1??? ?????? ?????? ??????!";
    }



    //???????????? 2??? ?????? -> 2??? ????????? ??????
    @PutMapping("/process/second-interview/allocate")
    public String secondInterviewAllocate(@RequestBody RequestPutInterview requestPutInterview) {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//
        InterviewDto interviewDto = mapper.map(requestPutInterview, InterviewDto.class);
        interviewDto.setEmpNo(requestPutInterview.getEmpNo());
        interviewDto.setFirstInterviewer(requestPutInterview.getSecondInterviewer());
        interviewDto.setApplyNum(requestPutInterview.getApplyNum());

//        InterviewEntity interviewEntity = interviewService.allocateSecondInterviewer(interviewDto);
        InterviewEntity interviewEntity = interviewService.scoreSecondInterview(interviewDto);
        if(interviewEntity == null){
            return "2??? ????????? ?????? ??????!";
        }
        return "2??? ????????? ?????? ??????";
    }

    // 2??? ?????? ??????
    @PutMapping("/process/second-interview/score")
    public String secondInterviewScore(@RequestBody RequestPutInterview requestPutInterview){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        InterviewDto interviewDto = mapper.map(requestPutInterview, InterviewDto.class);
        interviewDto.setSecondInterviewer(requestPutInterview.getSecondInterviewer());
        interviewDto.setApplyNum(requestPutInterview.getApplyNum());
        interviewDto.setSecondInterviewScore(requestPutInterview.getSecondInterviewScore());

        InterviewEntity interviewEntity = interviewService.scoreSecondInterview(interviewDto);
//        InterviewEntity interviewEntity = interviewService.scoreSecondInterviewer(interviewDto);
        if(interviewEntity == null){
            return "2??? ?????? ?????? ??????";
        }
        return "2??? ?????? ?????? ??????";
    }



    // ????????? ????????? ?????? ???????????? ->feign

//    // ????????? ????????? ?????? ???????????? -> ?????? ?????? ??????!!!!!

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


    // ????????? ???????????? ????????? ????????? ???????  -> ?????? ?????? ??????!!!!!
    // ????????? ???????????? ????????? ????????? ?????? ???????   -> ?????? ?????? ??????!!!!!

    // ??????????????? ?????????????????? ????????? ????????? ?????????(????????????) -- ??????
    @GetMapping("/process/{jobsNo}")
    public ResponseEntity setWritten(@PathVariable String jobsNo){

        //???????????? ????????????
        List<WrittenDto> applyList= writtenService.getApplicantList(jobsNo);
        writtenService.createWrittenPerson(applyList);
        //?????? T ????????????
        writtenService.setJobsClose(jobsNo);

        return ResponseEntity.status(HttpStatus.OK).body("????????? ????????? ??????");
    }

    // ?????? ???????????? -> ????????? id??? ????????????. -->  ?????? ????????? ???????????? ?????? ???????????? id???????????? ???????
    public ResponseEntity gradingId(){
        return null;
    }



    /* ??????????????? ???????????? ????????? ?????? ???????????? ?????? ????????? GET*/
//    @GetMapping("/process/{corpNo}")
//    public ResponseEntity<List<ResponeJobByCorpNo>> getJobsBycorp(@PathVariable String corpNo) throws Exception{
//        Iterable<JobEntity> jobList = interviewService.getJobsByCorpNo(corpNo);
//        List<ResponeJobByCorpNo> result = new ArrayList<>();
//
//        jobList.forEach(v->{
//            result.add(new ModelMapper().map(v, ResponeJobByCorpNo.class));
//        });
//        return ResponseEntity.status(HttpStatus.OK).body(result);
//    }


    /*1??? ?????? ?????? ?????? */
    @PutMapping("/process/first-interview/result")
    public ResponseEntity<List<ResponseInterview>> updateFirstInterviewResult(@RequestBody @Valid RequestFirstInterviewResult firstInterviewResult){
        log.info("?????? ?????? ???/??? ?????????");
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//
        InterviewDto interviewDto = mapper.map(firstInterviewResult, InterviewDto.class);
        interviewService.checkPassOrNotFirst(interviewDto);

        Iterable<InterviewEntity> interviewList = interviewService.getInterviewListByJobsNo(interviewDto.getJobsNo());
        List<ResponseInterview> result = new ArrayList<>();
        interviewList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseInterview.class));
        });
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /*2??? ?????? ?????? ?????? */
    @PutMapping("/process/second-interview/result")
    public ResponseEntity<List<ResponseInterview>> updateSecondInterviewResult(@RequestBody @Valid RequestSecondInterviewResult secondInterviewResult){
        log.info("2??? ?????? ?????? ???/??? ?????????");
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//
        InterviewDto interviewDto = mapper.map(secondInterviewResult, InterviewDto.class);
        interviewService.checkPassOrNotSecond(interviewDto);

        Iterable<InterviewEntity> interviewList = interviewService.getInterviewListByJobsNo(interviewDto.getJobsNo());
        List<ResponseInterview> result = new ArrayList<>();
        interviewList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseInterview.class));
        });
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /* ????????? ???????????? ????????????*/
//    @GetMapping("/process/{corpNo}/written-test")
//    public List<ResponseWrittenTest> getWrittenTest(@PathVariable("corpNo") String corpNo){
//        Iterable<WrittenEntity> writtenList = writtenService.getWrittenAllCorpNo(corpNo);
//
//        List<ResponseWrittenTest> result = new ArrayList<>();
//
//        writtenList.forEach(v ->{
//            result.add(new ModelMapper().map(v, ResponseWrittenTest.class));
//
//        });
//        return result;
//    }

    /* ????????? ?????? ????????? ????????????(?????? ?????? ????????? ??????) -??????  ?????? userId?????? empNo??? ?????????.. ?????? ????????? ????????????..*/
    @GetMapping("/process/written/{jobsNo}/{userId}")
    public ResponseEntity getWrittenList(@PathVariable("jobsNo") String jobsNo, @PathVariable("userId") String userId){

        WrittenDto writtenDto = new WrittenDto();
        writtenDto.setJobsNo(jobsNo);
        writtenDto.setUserId(userId);
        WrittenEntity writtenEntity = writtenService.getWrittenPersonByJobsNoAndUserId(writtenDto);

        ResponseWritten result = new ModelMapper().map(writtenEntity, ResponseWritten.class);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /* ????????? ?????? ????????? ???????????? */
    @GetMapping("/process/written/{jobsNo}")
    public List<ResponseWritten> getWrittenList(@PathVariable("jobsNo") String jobsNo){
        Iterable<WrittenEntity> writtenList = writtenService.getWrittenListByJobsNo(jobsNo);
        List<ResponseWritten> result = new ArrayList<>();
        writtenList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseWritten.class));
        });

        return result;
    }

    /* ????????? 1??? ?????? ????????? ???????????? */
    @GetMapping("/process/first-interview/{jobsNo}")
    public List<ResponseFirstInterviewList> getFirstInterviewList(@PathVariable("jobsNo") String jobsNo){
            Iterable<InterviewEntity> interviewList = interviewService.getInterviewListByJobsNo(jobsNo);
        List<ResponseFirstInterviewList> result = new ArrayList<>();

        interviewList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseFirstInterviewList.class));
        });

        return result;
    }

    /* ????????? 2??? ?????? ????????? ????????????*/
    @GetMapping("/process/second-interview/{jobsNo}")
    public List<ResponseSecondInterviewList> getSecondInterviewList(@PathVariable("jobsNo") String jobsNo){
        Iterable<InterviewEntity> interviewList = interviewService.getInterviewListByJobsNo(jobsNo);
        List<ResponseSecondInterviewList> result = new ArrayList<>();

        interviewList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseSecondInterviewList.class));
        });

        return result;

    }

    /* ????????? ?????? 1??? ????????? ????????????(?????? ?????? ????????? ??????) -???  */
    @GetMapping("/process/interview/{jobsNo}/{userId}")
    public ResponseEntity getInv1List(@PathVariable("jobsNo") String jobsNo, @PathVariable("userId") String userId){

        InterviewDto interviewDto = new InterviewDto();
        interviewDto.setJobsNo(jobsNo);
        interviewDto.setUserId(userId);
        InterviewEntity interviewEntity = interviewService.getInvPersonByJobsNoAndUserId(interviewDto);

        ResponseInterview result = new ModelMapper().map(interviewEntity, ResponseInterview.class);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    /* ????????? ????????? ?????? ???????????? ???????????? ?????? -??????*/
    @GetMapping("/process/final/{jobsNo}")
    public ResponseEntity getInterviewFinal(@PathVariable("jobsNo") String jobsNo){
        List<ResponseInterviewFinal> interviewList = interviewService.getInterviewFinal(jobsNo);
         return ResponseEntity.status(HttpStatus.OK).body(interviewList);
    }
    //    @GetMapping("/users/jobs/{userId}")
    //    public ResponseEntity getApplyJobs(@PathVariable("userId") String userId){
    //
    //        List<ResponseJobShort> applyJobShortList = userService.getJobsByUserId(userId);
    //        return ResponseEntity.status(HttpStatus.OK).body(applyJobShortList);
    //    }
}



