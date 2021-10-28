package com.example.hrservice.jpa;

import com.example.hrservice.entity.HrEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HrRepository extends CrudRepository<HrEntity, String> {
    List<HrEntity> findByCorpNo(String corpNo);
    HrEntity findByEmail(String email);
    HrEntity findByEmpNo(String empNo);
    HrEntity deleteByEmpNo(String empNo);
    //List<HrEntity> findByEmpNo(String empNo);
}
