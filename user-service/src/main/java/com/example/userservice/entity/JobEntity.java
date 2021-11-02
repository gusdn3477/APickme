package com.example.userservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@DynamicInsert
@Table(name = "jobs")
public class JobEntity implements Serializable {
    @Id
    @Column(nullable = false, length = 50, unique = true)
    private String jobsNo;

    @Column(length = 10)
    private String corpNo;
    @Column(length = 10)
    private String empNo;
    @Column(nullable= false, length = 100)
    private String jobsTitle;

    @Column(nullable = false, length = 200)
    private String jobsContext;

    @Column(nullable = false, length = 10)
    private Integer recruitNum;

    @Column(length = 30)
    private String favoriteLang;

    @Column(nullable = false, length = 100)
    private String jobLocation;

    @Column(length = 100)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Date createdAt;

    @Column(nullable = false, length = 100)
    private String jobType;

    @Column(length = 100)
    @ColumnDefault("'자격 무관'") //지원자격
    private String jobQualify;

    @Column(length = 100)
    @ColumnDefault("'무관'")
    private String employType; //채용유형 무관/신입/

    @Column(length = 100)
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Date applyStart;

    @Column(length = 100)
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Date applyEnd;

    @Column(length = 100)
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Date intv1Start;

    @Column(length = 100)
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Date intv1End;

    @Column(length = 100)
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Date intv2Start;

    @Column(length = 100)
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Date intv2End;

    @Column(length = 100)
    private String workDetail;





}
