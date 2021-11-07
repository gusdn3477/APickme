package com.example.userservice.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {

    @Id
    @Column(nullable = false, length = 50, unique = true)
    private String email;
    @Column(nullable = false, length = 50)
    private String applyName;
    @Column(nullable = false, unique = true)
    private String userId;
    @Column(unique = true)
    private String encryptedPwd;

    @Column()
    private String address;

    @Column()
    private String phoneNum;

    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Date registerDate;

    private String id;

    @Builder
    public UserEntity(String applyName, String email){
        this.applyName = applyName;
        this.email = email;
    }

    public UserEntity update(String applyName){
        this.applyName = applyName;

        return this;
    }
}