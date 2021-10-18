package com.example.hrservice.jpa;

import com.example.hrservice.entity.HrEntity;
import org.springframework.data.repository.CrudRepository;

public interface HrRepository extends CrudRepository<HrEntity, String> {
    //HrEntity;
}
