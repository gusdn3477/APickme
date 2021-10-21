package com.example.job.jpa;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@DynamicInsert
@Table(name = "jobs")
public class JobEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private Long jobsNo;

    @Column(length = 10)
    private String corpNo2;

    @Column(length = 10)
    private String empNo;


    @Column(nullable= false)
    private String jobsTitle;

    @Column(nullable = false)
    private String jobsContext;

    @Column(nullable = false)
    private Integer recruitNum;

    @Column()
    private String favoriteLang;

    @Column(nullable = false)
    private String jobLocation;

    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Date createdAt;

    @Column(nullable = false)
    private String jobType;

    @Column()
    @ColumnDefault("'자격 무관'") //지원자격
    private String jobQualify;

    @Column()
    @ColumnDefault("'무관'")
    private String employType; //채용유형 무관/신입/

    @Column()
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Date applyStart;

    @Column()
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Date applyEnd;

    @Column()
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Date intvStart1;

    @Column()
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Date intvEnd1;

    @Column()
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Date intvStart2;

    @Column()
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Date intvEnd2;

    @Column()
    private String workDetail;





}
