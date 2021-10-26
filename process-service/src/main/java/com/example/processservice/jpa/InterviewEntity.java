package com.example.processservice.jpa;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name="interview")
public class InterviewEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, length = 20, unique = true)
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private String applyNum;

//    @Id
    @Column(nullable = false, length = 20, unique = true)
    private String userId;

    @Column(nullable = false, length = 20)
    private String empNo;
    @Column
    private Date firstInterviewDate;
    @Column
    private String firstInterviewer;
    @Column
    private Integer firstInterviewScore;
    @Column
    private String firstInterviewResult;

    @Column
    private Date secondInterviewDate;
    @Column
    private String secondInterviewer;
    @Column
    private Integer secondInterviewScore;
    @Column
    private String secondInterviewResult;
    @Column
    private String jobsNo;

    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Date createdAt;

}
