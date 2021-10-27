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
    @Column(length = 100)
    private String jobsNo;

    @Column
    private float writtenMultiple;
//jobEntity 의 recnum * writtenmultiple
//    int i = integernum.intValue()
//    Integer integernum = new Integer(i)

    @Column
    private Integer writtenPass;

    @Column
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Date writtenDate;

    @Column
    private float intv1Multiple;

    @Column
    private Integer intv1Pass;

    @Column
    private float intv2Multiple;

    @Column
    private Integer intv2Pass;

}
