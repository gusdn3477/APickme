package com.example.userservice.entity;


import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "applys")
public class ApplyEntity {

    @Id
    private String applyNum; //uuid

    private String userId;
    private String jobsNo; //uuid로 공고쪽에서 이미 생성되었을것임
    private String applyName;
    private String applyEmail;
    private String applyContact;
    private String portfolio;

    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Date applyDateTime;
}

