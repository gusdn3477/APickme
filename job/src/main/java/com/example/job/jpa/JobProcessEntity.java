package com.example.job.jpa;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;


import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@DynamicInsert
@Table(name = "jobprocess")
public class JobProcessEntity {
    @Id
    @Column(nullable = false, length = 50, unique = true)
    private String jobsNo;

    @Column(length = 100)
    private Integer recruitNum;

    @Column(length = 100)
    private float writtenMultiple;
//jobEntity 의 recnum * writtenmultiple
//    int i = integernum.intValue()
//    Integer integernum = new Integer(i)

    @Column(length = 100)
    private Integer writtenPass;

    @Column(length = 100)
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Date writtenDate;

    @Column(length = 100)
    private float intv1Multiple;

    @Column(length = 100)
    private Integer intv1Pass;

    @Column(length = 100)
    private float intv2Multiple;

    @Column(length = 100)
    private Integer intv2Pass;

}
