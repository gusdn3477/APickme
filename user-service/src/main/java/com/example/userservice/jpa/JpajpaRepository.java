package com.example.userservice.jpa;

import com.example.userservice.entity.ApplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpajpaRepository extends JpaRepository<ApplyEntity, String>{

    @Query(value = "select apply_num,user_id, applys.jobs_no, apply_name, apply_email, apply_contact, portfolio, apply_date_time from applys ,jobs where applys.jobs_no = jobs.jobs_no and jobs.corp_no = :corpNo", nativeQuery = true)
    List<ApplyEntity> findAllByCorpNo(@Param("corpNo") String corpNo);
}
