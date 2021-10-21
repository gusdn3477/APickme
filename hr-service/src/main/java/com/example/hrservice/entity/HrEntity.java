package com.example.hrservice.entity;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Data
@Entity
@DynamicInsert
@Table(name="hr")
public class HrEntity {
    @Id
    @Column(nullable = false, length = 30, unique = true)
    private String empNo;

    @Column(nullable = false, length = 30)
    private String corpNo;

    @Column(nullable = false, length = 10)
    private String name;
    @Column(nullable = false, length = 30, unique = true)
    private String email;
    @Column(nullable = false, length = 30)
    private String parents;
    @Column(nullable = false, length = 60)
    private String encryptedPwd;
    @Column(nullable = false, length = 20)
    private String nickname;

    @Column(nullable = false, length = 2)
    @ColumnDefault("'F'")
    private String auth;

}
