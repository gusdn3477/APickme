package com.example.processservice.jpa;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "process")
public class ProcessEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(nullable = false, length = 120, unique = true)
    private String productId;
    @Column(length = 30)
    private String productName;
    @Column(length = 10)
    private Integer stock;
    @Column(length = 10)
    private Integer unitPrice;
    @Column(length = 120)
    private String image;
    @Column(length = 50)
    private String writer;


    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Date createdAt;

}
