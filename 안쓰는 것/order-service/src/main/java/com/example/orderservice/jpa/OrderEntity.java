package com.example.orderservice.jpa;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@DynamicInsert
@Table(name="orders")
public class OrderEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String productId;
    @Column(nullable = false, length = 10)
    private Integer qty;
    @Column(nullable = false, length = 10)
    private Integer unitPrice;
    @Column(length = 10)
    private Integer totalPrice;

    @Column(nullable = false, length = 120)
    private String userId;
    @Column(nullable = false, unique = true, length = 120)
    private String orderId;

    @Column(length = 10)
    @ColumnDefault("0") //default 0 ,,배송시작 s , 배송중 i, 배송완료 c
    private Integer statusCode ;

    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Date createdAt;
}