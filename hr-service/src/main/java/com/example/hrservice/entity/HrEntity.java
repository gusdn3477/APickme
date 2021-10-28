package com.example.hrservice.entity;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@DynamicInsert
@EntityListeners(AuditingEntityListener.class)
@Table(name="hr")
public class HrEntity {
    @Id
    @Column(nullable = false, length = 50, unique = true)
    private String empNo;

    @Column(nullable = false, length = 50)
    private String corpNo;

    @Column(nullable = false, length = 10)
    private String name;
    @Column(nullable = false, length = 30, unique = true)
    private String email;
    @Column(nullable = false, length = 30)
    private String parents;
    @Column(nullable = false, length = 60)
    private String encryptedPwd;

    @Column( length = 6)
    @ColumnDefault("false")
    private String auth;

//    @Column(nullable = false, updatable = false, insertable = false)
//    @ColumnDefault(value = "CURRENT_TIMESTAMP")
//    private Date registerDate;
}
