package com.example.cartservice.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CartDto implements Serializable {
    private long id;
    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;
    private String imageUrl;
    private String orderId;
    private String userId;
    private String productName;
//    private Integer stock;
}
