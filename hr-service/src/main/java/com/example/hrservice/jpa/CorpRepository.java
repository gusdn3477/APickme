package com.example.hrservice.jpa;

import com.example.hrservice.entity.CorpEntity;
import org.springframework.data.repository.CrudRepository;

public interface CorpRepository extends CrudRepository<CorpEntity,String> {
}
