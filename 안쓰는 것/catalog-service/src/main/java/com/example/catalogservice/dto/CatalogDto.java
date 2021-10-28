package com.example.catalogservice.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CatalogDto implements Serializable {
    //private String ISBN;
    private String productName;
    private String productId;
    private Integer stock;
    private String writer;
    private Integer unitPrice;
    private String image;

    private String orderId;
    //private String userId;
}
