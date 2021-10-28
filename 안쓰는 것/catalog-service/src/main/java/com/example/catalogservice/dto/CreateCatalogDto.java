package com.example.catalogservice.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class CreateCatalogDto implements Serializable{
    private String ISBN;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;

    private String orderId;
    private String userId;
}

