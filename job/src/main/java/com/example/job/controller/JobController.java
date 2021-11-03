package com.example.job.controller;

import com.example.job.dto.JobDto;
import com.example.job.dto.JobProcessDto;
import com.example.job.jpa.JobEntity;
import com.example.job.jpa.JobProcessEntity;
import com.example.job.service.JobService;
import com.example.job.vo.*;
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
    
    //공고 조회
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

//    여기 안했습니다. 내일 할 예정
    @GetMapping("/jobprocess/{jobsNo}")
    public ResponseEntity<ResponseJobProcess> getJobProcess(@PathVariable("jobsNo") String jobsNo){

        JobProcessEntity jobProcessEntity = jobService.getProcess(jobsNo);
        ResponseJobProcess responseJobProcess = new ModelMapper().map(jobProcessEntity, ResponseJobProcess.class);
        return ResponseEntity.status(HttpStatus.OK).body(responseJobProcess);
    }

//    @DeleteMapping("/jobs/")
//    public

    /*달력 관련 추가 */
    @GetMapping("jobsall/{corpNo}")
    public List<ResponseCalender> getJobsByCorpNo(@PathVariable("corpNo") String corpNo){
        List<ResponseCalender> jobCorpList = jobService.getCorpAllJob(corpNo);

        List<ResponseCalender> result = new ArrayList<>();
        jobCorpList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseCalender.class));
        });

        return result;
    }

    @GetMapping("/{corpNo}/jobsall")
    public List<ResponseCalender> getCorpJobAll(@PathVariable("corpNo") String corpNo){
        Iterable<JobEntity> jobEntities = jobService.getCorpAllJobs(corpNo);

        List<ResponseCalender> jobList = new ArrayList<>();

        int i = 0;
        int j=3;
        //while(jobEntities.iterator().hasNext())
        while(j!=0){
            String title = jobEntities.iterator().next().getJobsTitle();
            Date start = jobEntities.iterator().next().getApplyStart();
            Date end = jobEntities.iterator().next().getApplyEnd();
            Date start2 = jobEntities.iterator().next().getIntv1Start();
            Date end2 = jobEntities.iterator().next().getIntv1End();
            Date start3 = jobEntities.iterator().next().getIntv2Start();
            Date end3 = jobEntities.iterator().next().getIntv2End();
            if(end3 == null){
                end3 =start3;
            }


            ResponseCalender rc = new ResponseCalender();
            rc.setTitle(title); rc.setStart(start); rc.setEnd(end);
            jobList.add(rc);
            i++;
            ResponseCalender rc2 = new ResponseCalender();
            rc2.setTitle(title); rc2.setStart(start2); rc2.setEnd(end2);
            jobList.add(rc2);
            i++;
            ResponseCalender rc3 = new ResponseCalender();
            rc3.setTitle(title); rc3.setStart(start3); rc3.setEnd(end3);
            jobList.add(rc3);
            i++;

            j--;
        }

        return jobList;
    }

}
