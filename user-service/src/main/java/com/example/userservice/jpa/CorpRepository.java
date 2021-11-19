package com.example.userservice.jpa;


import com.example.userservice.entity.CorpEntity;
import org.springframework.data.repository.CrudRepository;

public interface CorpRepository extends CrudRepository<CorpEntity,String> {

    CorpEntity findByCorpNo(String corpNo);
//    String findCorpNameByCorpNo(String corpNo);
}
