package com.example.job.jpa;

import org.springframework.data.repository.*;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;

public interface JobRepository extends CrudRepository<JobEntity, Long>{
    Iterable<JobEntity> findAllByCorpNo2(String corpNo2);



}
//아직 모집중인 공고 보기
//전체 공고 보기
//createdBy
