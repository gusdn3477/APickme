package com.example.processservice.jpa;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "written")
public class WrittenEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, length = 20, unique = true)
    private Long id;

//    @Id
    @Column(nullable = false, length = 20, unique = true)
    private String applyNum;

//    @Id
    @Column(nullable = false, length = 20, unique = true)
    private String userId;

    @Column(nullable = false, length = 20)
    private String empNo;
    @Column
    private String jobsNo;
    @Column
    private Integer writtenScore;
    @Column(length=5)
    private String writtenResult;
    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Date createdAt;

}
