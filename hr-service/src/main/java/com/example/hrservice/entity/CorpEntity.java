package com.example.hrservice.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@DynamicInsert
@Table(name="company")
public class CorpEntity {
    @Id
    @Column(nullable = false, length = 30, unique = true)
    private String corpNo;

    @Column(nullable = false, length = 50)
    private String corpName;

}
