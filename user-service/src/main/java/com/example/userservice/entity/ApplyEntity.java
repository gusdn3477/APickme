package com.example.userservice.entity;


import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener; // 이게 있어야 날짜 있어도 자동 생성 -> 진희님 말씀

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
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

