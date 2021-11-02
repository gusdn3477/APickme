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
    private String applyNum; //ㅇ

//    @Id
    @Column(nullable = false, length = 20, unique = true)
    private String userId; //ㅇ

    @Column(nullable = false, length = 20)
    private String empNo; //ㅇ
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

    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Date createdAt;

}
