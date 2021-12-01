package com.example.processservice.jpa;

import org.springframework.data.repository.CrudRepository;

public interface JobRepository extends CrudRepository<JobEntity, String> {


    //Iterable<JobEntity> findAllByCorpNo(String corpNo);

    //Iterable<JobEntity> findByCorpNo(String corpNo);

}
