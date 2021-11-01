package com.example.processservice.jpa;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name="interview")
@EntityListeners(AuditingEntityListener.class)
public class InterviewEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, length = 50, unique = true)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String applyNum;

//    @Id
    @Column(nullable = false, length = 50, unique = true)
    private String userId;

    @Column(length = 50)
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

    @Column(insertable = false, updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

}
