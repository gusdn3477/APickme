package com.example.processservice.jpa;

import com.example.processservice.vo.ResponseInterviewFinal;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Iterator;

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
    @Column(nullable = false, length = 50)
    private String userId;

    @Column(length = 50)
    private String empNo;

    @Column
    private Date firstInterviewDate; //ㅇ
    @Column
    private String firstInterviewer; //ㅇ
    @Column
    private Integer firstInterviewScore; //ㅇ
    @Column
    private String firstInterviewResult; //ㅇ

    @Column
    private Date secondInterviewDate; //ㅇ
    @Column
    private String secondInterviewer; //ㅇ
    @Column
    private Integer secondInterviewScore; //ㅇ
    @Column
    private String secondInterviewResult; //ㅇ
    @Column
    private String jobsNo; //ㅇ

    @Column(insertable = false, updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;


}
