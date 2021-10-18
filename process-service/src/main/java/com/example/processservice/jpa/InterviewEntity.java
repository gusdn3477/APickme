package com.example.processservice.jpa;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name="interview")
public class InterviewEntity implements Serializable {

    @Id
    @Column(nullable = false, length = 20, unique = true)
    private String applyNum;

    @Id
    @Column(nullable = false, length = 20, unique = true)
    private String userId;

    @Column(nullable = false, length = 20)
    private String empNo;
    @Column
    private Date firstInterview;
    @Column
    private String firstInterviewer;
    @Column
    private Integer firstInterviewScore;
    @Column(length=20)
    private String firstInterviewResult;

    @Column
    private Date secondInterview;
    @Column
    private String secondInterviewer;
    @Column
    private Integer secondInterviewScore;
    @Column(length=20)
    private String secondInterviewResult;
    @Column
    private Integer jobsNo;

    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Date createdAt;

}
