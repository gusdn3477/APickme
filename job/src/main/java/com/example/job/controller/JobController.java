package com.example.job.controller;

import com.example.job.dto.JobDto;
import com.example.job.dto.JobProcessDto;
import com.example.job.jpa.JobEntity;
import com.example.job.jpa.JobProcessEntity;
import com.example.job.service.JobService;
import com.example.job.vo.RequestDeleteJob;
import com.example.job.vo.RequestJobDetail;
import com.example.job.vo.RequestJobInfo;
import com.example.job.vo.ResponseJob;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import javax.validation.Valid;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.UUID;

@RestController
@RequestMapping("/")
@Slf4j
public class JobController {
    Environment env;
    JobService jobService;

    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome test";
    }

    @Autowired
    public JobController(Environment env, JobService jobService){
        this.env = env;
        this.jobService = jobService;
    }
    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in Catalog Service on PORT %s",
                env.getProperty("local.server.port"));
    }
    @GetMapping("/jobs")
    public ResponseEntity<List<ResponseJob>> getJob(){
        Iterable<JobEntity> jobList = jobService.getAllJobs();

        List<ResponseJob> result = new ArrayList<>();
        jobList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseJob.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/{corpNo}/jobs")
    public ResponseEntity<List<ResponseJob>> getCorpJob(@PathVariable("corpNo") String corpNo){
        Iterable<JobEntity> jobCorpList = jobService.getCorpAllJobs(corpNo);

        List<ResponseJob> result = new ArrayList<>();
        jobCorpList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseJob.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);

    }

    @GetMapping("/jobs/{jobsNo}")
    public ResponseEntity<JobEntity> getJobDetail(@PathVariable("jobsNo") String jobsNo){
        JobEntity jobDetailList = jobService.getJob(jobsNo);
//
//        ModelMapper mapper = new ModelMapper();
//        mapper.getConfiguration().getMatchingStrategy();
        return ResponseEntity.status(HttpStatus.OK).body(jobDetailList);
    }

    @GetMapping("/jobs/available")
    public ResponseEntity<Iterable<JobEntity>> getApplyAvailable(){
        Date now = new Date();
        Iterable<JobEntity> applyAvailable = jobService.getApplyAvailable(now,now);

        return ResponseEntity.status(HttpStatus.OK).body(applyAvailable);


    }




    @PostMapping("/jobs")
    public ResponseEntity<ResponseJob> createJob(@RequestBody RequestJobInfo job){
        log.info("Before add job data");
        String jobsUUID = UUID.randomUUID().toString();
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        JobDto jobDto = mapper.map(job, JobDto.class);
        JobProcessDto jobProcessDto = mapper.map(job, JobProcessDto.class);
//        jobDto.setJobsNo(jobsUUID);
        jobService.createJob(jobsUUID,jobDto);
        jobService.createJobProcess(jobsUUID,jobProcessDto);


        // convert JobDto to ResponseJob
        ResponseJob responseJob = mapper.map(jobDto, ResponseJob.class);

        log.info("After added job data");
        return ResponseEntity.status(HttpStatus.CREATED).body(responseJob);


    }

    @PutMapping("/jobs")
    public String upDateJob(@RequestBody RequestJobInfo requestJobInfo){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        JobDto jobDto = mapper.map(requestJobInfo, JobDto.class);
        JobProcessDto jobProcessDto = mapper.map(requestJobInfo, JobProcessDto.class);

        JobEntity jobEntity = jobService.updateJob(jobDto, jobProcessDto);

        if(jobEntity==null){
            return "수정 실패";
        }
        else{
            JobProcessEntity jobProcessEntity = jobService.updateProcess(jobProcessDto);
            return "수정에 성공하였습니다";
        }

    }

    @DeleteMapping("/jobs")
    public String deleteJob(@RequestBody RequestDeleteJob requestDeleteJob){
        String empNo = requestDeleteJob.getEmpNo();
        String jobsNo = requestDeleteJob.getJobsNo();
        JobEntity jobEntity = jobService.getJob(jobsNo);
        String result = "삭제 실패";
        if(jobEntity.getEmpNo().equals(empNo)){
            jobService.deleteJob(jobsNo);
            result = "삭제 성공";
        }
        return result;
    }

//    @DeleteMapping("/jobs/")
//    public
}
