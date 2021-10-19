package com.example.job.jpa;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "jobs")
public class JobEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private Long jobsNo;


    @Column(nullable= false)
    private String jobsTitle;
    @Column(nullable = false)
    private String jobsContext;
    @Column(nullable = false)
    private Integer recruitNum;
    @Column(nullable = false)
    private String favoriteLang;
    @Column(nullable = false)
    private String jobLocation;

    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Date createdAt;


}
