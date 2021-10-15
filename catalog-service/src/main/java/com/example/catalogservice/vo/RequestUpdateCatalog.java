package com.example.catalogservice.vo;

import lombok.Data;

@Data
public class RequestUpdateCatalog {
    private String productName;
    private String productId;
    private Integer stock;
    private String writer;
    private Integer unitPrice;
    private String image;
}
