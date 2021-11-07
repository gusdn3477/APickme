package com.example.job.service;

import com.example.job.dto.JobDto;
import com.example.job.dto.JobProcessDto;
import com.example.job.jpa.*;
import com.example.job.vo.ResponseCalender;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;


@Data
@Slf4j
@Service
public class JobServiceImpl implements JobService{
    JobRepository jobRepository;
    JobProcessRepository jobProcessRepository;
    JobJpaRepository jobJpaRepository;

    @Autowired
    public JobServiceImpl(JobRepository jobRepository, JobProcessRepository jobProcessRepository,JobJpaRepository jobJpaRepository) {
        this.jobRepository = jobRepository;
        this.jobProcessRepository = jobProcessRepository;
        this.jobJpaRepository = jobJpaRepository;
    }


    @Override
    public JobEntity createJob(String UUID,JobDto jobDto) {
        jobDto.setJobsNo(UUID);
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        JobEntity jobEntity = mapper.map(jobDto, JobEntity.class);

        jobRepository.save(jobEntity);

        return null;
    }

    @Override
    public JobProcessEntity createJobProcess(String UUID, JobProcessDto jobProcessDto){
        jobProcessDto.setJobsNo(UUID);
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        JobProcessEntity jobProcessEntity = mapper.map(jobProcessDto, JobProcessEntity.class);

        jobProcessEntity.setIntv1Pass((int) (jobProcessEntity.getRecruitNum()*jobProcessEntity.getIntv1Multiple()));
        jobProcessEntity.setIntv2Pass((int) (jobProcessEntity.getRecruitNum()*jobProcessEntity.getIntv2Multiple()));
        jobProcessEntity.setWrittenPass((int) (jobProcessEntity.getRecruitNum()*jobProcessEntity.getWrittenMultiple()));

        jobProcessRepository.save(jobProcessEntity);

        return null;
    }


    @Override
    public Iterable<JobEntity> getAllJobs() {
        return jobRepository.findAll();
    }
    @Override
    public Iterable<JobEntity> getCorpAllJobs(String corpNo){
        return jobRepository.findAllByCorpNo(corpNo);
    }

    @Override
    public JobEntity getJob(String jobsNo){
        return jobRepository.findByJobsNo(jobsNo);
    }

    @Override
    public Iterable<JobEntity> getApplyAvailable(Date curTime, Date endTime){
        return jobRepository.findByApplyStartBeforeAndApplyEndAfter(curTime, endTime);

    }

    @Override
    public JobProcessEntity updateProcess(JobProcessDto jobProcessDto){
        JobProcessEntity jobProcessEntity = jobProcessRepository.findByJobsNo(jobProcessDto.getJobsNo());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        jobProcessEntity = mapper.map(jobProcessDto,JobProcessEntity.class);
        jobProcessEntity.setIntv1Pass((int) (jobProcessEntity.getRecruitNum()*jobProcessEntity.getIntv1Multiple()));
        jobProcessEntity.setIntv2Pass((int) (jobProcessEntity.getRecruitNum()*jobProcessEntity.getIntv2Multiple()));
        jobProcessEntity.setWrittenPass((int) (jobProcessEntity.getRecruitNum()*jobProcessEntity.getWrittenMultiple()));
        jobProcessRepository.save(jobProcessEntity);
        return jobProcessEntity;

    }
    @Override
    public JobEntity updateJob(JobDto jobDto, JobProcessDto jobProcessDto){
//        JobEntity jobEntity = jobRepository.findByJobsNoAndEmpNo(jobDto.getJobsNo(), jobDto.getEmpNo());
        JobEntity jobEntity = jobRepository.findByJobsNoAndEmpNo(jobDto.getJobsNo(), jobDto.getEmpNo());

        if(jobEntity == null){
            return null;
        }
        else {
//            updateProcess(jobProcessDto);
            ModelMapper mapper = new ModelMapper();
            mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            jobEntity = mapper.map(jobDto, JobEntity.class);

            jobRepository.save(jobEntity);

            return jobEntity; }
    }

    @Override
    @Transactional
    public void deleteJob(String jobsNo){
        jobRepository.deleteByJobsNo(jobsNo);
        jobProcessRepository.deleteByJobsNo(jobsNo);
    }

    @Override
    @Transactional
    public void deleteJobProcess(String jobsNo){
        jobRepository.deleteByJobsNo(jobsNo);
    }

    @Override
    public JobProcessEntity getProcess(String jobsNo){
        return jobProcessRepository.findByJobsNo(jobsNo);
    }


         @Override
         public List<ResponseCalender> getCorpAllJob(String corpNo) {
         List<JobEntity> jobEntitiess = jobJpaRepository.findAllByCorpNo(corpNo);
         List<ResponseCalender> jobList = new ArrayList<>();


            long count = StreamSupport.stream(jobEntitiess.spliterator(), false).count();
            System.out.println("count : "+count);

            int size = jobEntitiess.size();


            for(int i =0;i<jobEntitiess.size();i++){
                String title = jobEntitiess.get(i).getJobsTitle();
                 Date start = jobEntitiess.get(i).getApplyStart();
                 Date end = jobEntitiess.get(i).getApplyEnd();
                 Date start2 = jobEntitiess.get(i).getIntv1Start();
                 Date end2 = jobEntitiess.get(i).getIntv1End();
                 Date start3 = jobEntitiess.get(i).getIntv2Start();
                 Date end3 = jobEntitiess.get(i).getIntv2End();

              if(end3 == null){
                 end3 =start3;
              }

                 ResponseCalender rc = new ResponseCalender();
                 rc.setTitle(title+"서류 전형"); rc.setStart(start); rc.setEnd(end);
                 jobList.add(rc);

                 ResponseCalender rc2 = new ResponseCalender();
                 rc2.setTitle(title+"1차 면접 기간"); rc2.setStart(start2); rc2.setEnd(end2);
                 jobList.add(rc2);

                 ResponseCalender rc3 = new ResponseCalender();
                 rc3.setTitle(title+"2차 면접 기간"); rc3.setStart(start3); rc3.setEnd(end3);
                 jobList.add(rc3);

            }

        return jobList;
    }

    @Override
    public List<ResponseCalender> getAllJobss() {
        List<JobEntity> jobEntities = jobJpaRepository.findAll();
        List<ResponseCalender> jobList = new ArrayList<>();

        for(int i =0;i<jobEntities.size();i++){
            String title = jobEntities.get(i).getJobsTitle();
            Date start = jobEntities.get(i).getApplyStart();
            Date end = jobEntities.get(i).getApplyEnd();

            ResponseCalender rc = new ResponseCalender();
            rc.setTitle(title+" 지원기간"); rc.setStart(start); rc.setEnd(end);
            jobList.add(rc);
        }
        return jobList;
    }


}
