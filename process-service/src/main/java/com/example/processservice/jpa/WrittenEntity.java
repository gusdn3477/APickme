package com.example.processservice.jpa;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "written")
@EntityListeners(AuditingEntityListener.class)
public class WrittenEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, length = 50, unique = true)
    private Long id;

//    @Id
    @Column(nullable = false, length = 50, unique = true)
    private String applyNum;

//    @Id
    @Column(nullable = false, length = 50)
    private String userId;

    @Column( length = 50)
    private String empNo;
    @Column
    private String jobsNo;
    @Column
    private Integer writtenScore;
    @Column
    private String writtenResult;
    //@Column( updatable = false, insertable = false)
    //@ColumnDefault(value = "CURRENT_TIMESTAMP")
    //@CreatedDate
    //@CreationTimestamp
    @Column(insertable = false, updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

}
