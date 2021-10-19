package com.example.job.controller;

import com.example.job.dto.JobDto;
import com.example.job.jpa.JobEntity;
import com.example.job.service.JobService;
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

import java.util.*;

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
    @PostMapping("/jobs")
    public ResponseEntity<ResponseJob> createJob(@RequestBody RequestJobInfo job){
        log.info("Before add job data");

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        JobDto jobDto = mapper.map(job, JobDto.class);
        jobService.createJob(jobDto);

        // convert CatalogDto to ResponseCatalog
        ResponseJob responseJob = mapper.map(jobDto, ResponseJob.class);

        log.info("After added job data");
        return ResponseEntity.status(HttpStatus.CREATED).body(responseJob);


    }
}
